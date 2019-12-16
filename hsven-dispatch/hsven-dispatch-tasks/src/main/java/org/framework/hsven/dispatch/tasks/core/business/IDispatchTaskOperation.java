package org.framework.hsven.dispatch.tasks.core.business;

import org.framework.hsven.dispatch.tasks.core.entity.DispatchTasks;
import org.framework.hsven.dispatch.tasks.core.entity.DispatchTasksCondition;

import java.util.Date;
import java.util.List;

/**
 * 派发任务实体的操作接口,不同的实体存储方式,都各自实现该接口
 *
 * @author sven
 * @date 2019/10/16 10:22
 */
public interface IDispatchTaskOperation {
	/**
	 * 找到执行主机的主机id
	 *
	 * @return
	 */
	String getLocalHost();

	/**
	 * 查询需要派发的任务
	 *
	 * @param taskStatus
	 * @param taskType;
	 * @param createClient
	 * @return 待派发的任务列表
	 */
	List<DispatchTasks> getTasksForDispatch(int taskStatus, String taskType, String createClient);

	/**
	 * 同步复位指定的任务为新的状态
	 *
	 * @param condition     指定条件的任务
	 * @param taskStatusNew 新的任务状态
	 * @return 同步的数据量
	 */
	int synchronousReset(DispatchTasksCondition condition, int taskStatusNew);

	/**
	 * 更新指定的任务为正在执行状态,注意更新前任务状态为:TaskDispatchConstans.STATUS_UNEXECUTED(任务还未执行)
	 *
	 * @param taskId
	 * @param currentTaskStatus 更新前的任务状态
	 * @param taskStatus        更新后的任务状态
	 * @param executeClient     执行该任务的客户端
	 * @param beginTime         执行开始时间
	 * @return true:更新成功,false:更新失败
	 */
	boolean updateStatusExecuting(Long taskId, int currentTaskStatus, int taskStatus, String executeClient, Date beginTime);

	/**
	 * 更新指定的任务正常完成,注意更新前任务状态为:TaskDispatchConstans.STATUS_EXECUTING(任务正在执行)
	 *
	 * @param taskId
	 * @param currentTaskStatus 更新前的任务状态
	 * @param taskStatus        更新后的任务状态
	 * @param executeClient     执行改任务的客户端
	 * @param endTime           执行结束时间
	 * @return 更新的数据量
	 */
	boolean updateStatusAndClientEndNormal(Long taskId, int currentTaskStatus, int taskStatus, String executeClient, Date endTime);

	/**
	 * 更新指定的任务异常完成,注意更新前任务状态为:TaskDispatchConstans.STATUS_EXECUTING(任务正在执行)
	 *
	 * @param taskId
	 * @param currentTaskStatus 更新前的任务状态
	 * @param taskStatus        更新后的任务状态
	 * @param executeClient     执行该任务的客户端
	 * @param endTime           执行结束时间
	 * @param dispatchException 派发异常的原因
	 * @return 更新的数据量
	 */
	boolean updateStatusAndClientEndException(Long taskId, int currentTaskStatus, int taskStatus, String executeClient, Date endTime,
			String dispatchException);

}
