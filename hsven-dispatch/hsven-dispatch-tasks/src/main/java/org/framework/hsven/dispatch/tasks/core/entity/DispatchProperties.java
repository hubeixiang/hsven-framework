package org.framework.hsven.dispatch.tasks.core.entity;

/**
 * @author sven
 * @date 2019/12/16 16:22
 */
public class DispatchProperties {
	//服务是否是调试状态,如果是调试状态,任务调度只会执行自己的任务,依据任务中的executeClient限制
	private boolean debug = false;
	//获取任务派发的线程池大小,默认为2,也是每次派发的任务个数,每次派发时派发线程池中空闲线程数对应的任务数量
	private int taskMaxPoolSize = 2;
	//获取当前任务派发功能,可以派发的任务类型,配置后就只会派发这一类任务.未配置就全部任务都派发
	private String taskType;

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public int getTaskMaxPoolSize() {
		return taskMaxPoolSize;
	}

	public void setTaskMaxPoolSize(int taskMaxPoolSize) {
		this.taskMaxPoolSize = taskMaxPoolSize;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
}
