package org.framework.hsven.dispatch.tasks.core.constans;

/**
 * @author sven
 * @date 2019/11/20 15:31
 */
public class TaskDispatchConstans {
	//下面的这些状态都是任务调度状态,与任务的具体业务逻辑没有关系
	//任务还未准备完毕,不用扫描执行
	public static final int STATUS_NOT_READY = -1;
	//任务还未执行
	public static final int STATUS_UNEXECUTED = 0;
	//任务正在执行
	public static final int STATUS_EXECUTING = 1;
	//任务执行正常完毕,与业务执行是否正常没有关系
	public static final int STATUS_SUCCESSED = 2;
	//任务执行异常完毕,与业务执行是否正常没有关系
	public static final int STATUS_EXCEPTION = 3;
}
