package org.framework.hsven.dispatch.tasks.core.distribute.impl;

import org.framework.hsven.dispatch.tasks.core.business.IDispatchTaskExecute;
import org.framework.hsven.dispatch.tasks.core.business.IDispatchTaskOperation;
import org.framework.hsven.dispatch.tasks.core.constans.TaskDispatchConstans;
import org.framework.hsven.dispatch.tasks.core.distribute.IDispatchTasksService;
import org.framework.hsven.dispatch.tasks.core.entity.DispatchProperties;
import org.framework.hsven.dispatch.tasks.core.entity.DispatchTasks;
import org.framework.hsven.dispatch.tasks.core.entity.DispatchTasksCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author sven
 * @date 2019/10/16 10:25
 */
public abstract class AbstractDispatchTasksService implements IDispatchTasksService, InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(AbstractDispatchTasksService.class);
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private ThreadPoolExecutor taskExecutorService;
	private boolean isFirstScan = true;
	private Date createDate = new Date();
	private String localHost = null;
	private boolean dispatchReady = false;
	//需要外部初始化的派发属性
	private DispatchProperties dispatchProperties;

	public void afterPropertiesSet() throws Exception {
		DispatchProperties tmpdispatchProperties = getDispatchProperties();
		Assert.isTrue(tmpdispatchProperties != null, "Dispatch Properties must init");
		dispatchProperties = tmpdispatchProperties;
		Assert.isTrue(dispatchProperties.getTaskMaxPoolSize() > 0, "task execute pool size > 0");
		IDispatchTaskExecute iDispatchTaskExecute = getIDispatchTaskExecute();
		IDispatchTaskOperation iDispatchTaskOperation = getIDispatchTaskOperation();
		Assert.isTrue(iDispatchTaskExecute != null, "iDispatchTaskExecute must init");
		Assert.isTrue(iDispatchTaskOperation != null, "iDispatchTaskOperation must init");
		taskExecutorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(dispatchProperties.getTaskMaxPoolSize());
		localHost = iDispatchTaskOperation.getLocalHost();
		//调用可能存在的自定义初始化方法
		customAfterPropertiesSet();
		//设置派发已经准备正常
		dispatchReady = true;
		logger.info("Dispatch {} tasks services ready={}.",
				dispatchProperties.getTaskType() == null ? "ALL" : dispatchProperties.getTaskType(), dispatchReady);
	}

	public void synchronousReset(String createClient, int taskStatusOld, int taskStatusNew) {
		if (!dispatchReady) {
			return;
		}
		try {
			readWriteLock.writeLock().lock();
			IDispatchTaskOperation iTaskOperation = getIDispatchTaskOperation();
			DispatchTasksCondition condition = new DispatchTasksCondition();
			condition.setTaskDispatchStatus(taskStatusOld);
			condition.setCreateClient(createClient);
			condition.setTaskType(dispatchProperties.getTaskType());
			iTaskOperation.synchronousReset(condition, taskStatusNew);
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	public void scanAndDispatchTasks() {
		if (!dispatchReady) {
			return;
		}
		try {
			readWriteLock.writeLock().lock();
			// 系统启动时重置异常结束的任务（自己的）
			IDispatchTaskOperation iTaskOperation = getIDispatchTaskOperation();
			if (isFirstScan) {
				isFirstScan = false;
				DispatchTasksCondition condition = new DispatchTasksCondition();
				condition.setTaskDispatchStatus(TaskDispatchConstans.STATUS_EXECUTING);
				condition.setCreateClient(localHost);
				condition.setTaskType(dispatchProperties.getTaskType());
				iTaskOperation.synchronousReset(condition, TaskDispatchConstans.STATUS_UNEXECUTED);

				condition = new DispatchTasksCondition();
				condition.setTaskDispatchStatus(TaskDispatchConstans.STATUS_NOT_READY);
				condition.setCreateTime(createDate);
				condition.setCreateClient(localHost);
				condition.setTaskType(dispatchProperties.getTaskType());
				iTaskOperation.synchronousReset(condition, TaskDispatchConstans.STATUS_UNEXECUTED);
			}

			if (taskExecutorService.getActiveCount() >= dispatchProperties.getTaskMaxPoolSize()) {
				logger.info("Dispatch tasks pool is full.");
				return;
			}

			logger.info("Scan and start dispatch tasks.");
			long startTime = System.currentTimeMillis();
			Collection<DispatchTasks> tasks = new ArrayList<DispatchTasks>();
			if (dispatchProperties.isDebug()) {
				// 只执行自己创建的任务
				tasks.addAll(iTaskOperation
						.getTasksForDispatch(TaskDispatchConstans.STATUS_UNEXECUTED, dispatchProperties.getTaskType(), localHost));
			} else {
				// 优先执行自己的任务
				tasks.addAll(iTaskOperation
						.getTasksForDispatch(TaskDispatchConstans.STATUS_UNEXECUTED, dispatchProperties.getTaskType(), localHost));
				tasks.addAll(
						iTaskOperation.getTasksForDispatch(TaskDispatchConstans.STATUS_UNEXECUTED, dispatchProperties.getTaskType(), null));
			}
			if (System.currentTimeMillis() - startTime > TimeUnit.SECONDS.toMillis(30)) {
				logger.error("Scan and start dispatch tasks time out (30s).");
				return;
			}

			for (DispatchTasks task : tasks) {
				if (taskExecutorService.getActiveCount() >= dispatchProperties.getTaskMaxPoolSize()) {
					logger.info("dispatch tasks pool is full.");
					break;
				}
				taskExecutorService.submit(new TaskExecutor(task.getTaskId(), task.getTaskType()));
			}
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	private class TaskExecutor implements Runnable {

		private final long taskId;
		private final String taskType;

		private TaskExecutor(long taskId, String taskType) {
			Assert.isTrue(taskId > 0);
			this.taskId = taskId;
			this.taskType = taskType;
		}

		public void run() {
			IDispatchTaskExecute iDispatchTaskExecute = getIDispatchTaskExecute();
			IDispatchTaskOperation iDispatchTaskOperation = getIDispatchTaskOperation();
			try {
				logger.info("Started to execute dispatch tasks: {}", taskId);
				//判断当前任务更新为正在执行,是否更新成功
				if (iDispatchTaskOperation
						.updateStatusExecuting(taskId, TaskDispatchConstans.STATUS_UNEXECUTED, TaskDispatchConstans.STATUS_EXECUTING,
								localHost, new Date())) {
					logger.info("dispatch tasks: {}, dispatch sucess", taskId);
					//派发任务,并调用具体三方实现的任务具体执行服务
					iDispatchTaskExecute.dispatch(taskId, taskType);
					//任务正常派发时,更新任务状态为正常完成
					boolean endNormal = iDispatchTaskOperation.updateStatusAndClientEndNormal(taskId, TaskDispatchConstans.STATUS_EXECUTING,
							TaskDispatchConstans.STATUS_SUCCESSED, localHost, new Date());
					if (!endNormal) {
						//正常执行完毕后,更新任务的状态为正常状态时异常

					}
				} else {
					logger.warn("dispatch tasks: {}, already started.", taskId);
				}
				logger.info("Finished to execute dispatch tasks: {}", taskId);
			} catch (Throwable t) {
				logger.error("Failed to execute dispatch tasks: " + String.valueOf(taskId), t);
				try {
					//任务异常派发时,更新任务状态为异常完成
					iDispatchTaskOperation.updateStatusAndClientEndException(taskId, TaskDispatchConstans.STATUS_EXECUTING,
							TaskDispatchConstans.STATUS_EXCEPTION, localHost, new Date(),
							String.format("Dispatch Exception:%s", t.getMessage()));
				} catch (Exception e) {
					logger.error("Failed to execute dispatch tasks then update failure info exception: " + String.valueOf(taskId), e);
				}
			}
		}
	}
}
