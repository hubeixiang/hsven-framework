package org.framework.hsven.dispatch.tasks.core.business;

/**
 * 任务派发的接口,各个不同的业务实现自己的派发接口
 *
 * @author sven
 * @date 2019/10/16 10:59
 */
public interface IDispatchTaskExecute {
	/**
	 * 派发指定任务id的任务.不同的业务可以在自己的任务执行中更新记录中间结果,但是不得更新任务调度状态
	 *
	 * @param taskId
	 */
	public void dispatch(Long taskId);
}
