package org.framework.hsven.dispatch.tasks.simple.impl;

import org.framework.hsven.dispatch.tasks.core.entity.DispatchTasksCondition;
import org.framework.hsven.dispatch.tasks.core.business.IDispatchTaskOperation;
import org.framework.hsven.dispatch.tasks.core.entity.DispatchTasks;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author sven
 * @date 2019/10/17 15:09
 * 实现数据库中查询任务信息的service
 */
@Service("simpleDispatchTaskOperation")
public class SimpleDispatchTaskOperation implements IDispatchTaskOperation {
	public String getLocalHost() {
		return null;
	}

	public List<DispatchTasks> getTasksForDispatch(int taskStatus, String taskType, String createClient) {
		return null;
	}

	public int synchronousReset(DispatchTasksCondition condition, int taskStatusNew) {
		return 0;
	}

	@Override
	public boolean updateStatusExecuting(Long taskId, int currentTaskStatus, int taskStatus, String executeClient, Date beginTime) {
		return false;
	}

	@Override
	public boolean updateStatusAndClientEndNormal(Long taskId, int currentTaskStatus, int taskStatus, String executeClient, Date endTime) {
		return false;
	}

	@Override
	public boolean updateStatusAndClientEndException(Long taskId, int currentTaskStatus, int taskStatus, String executeClient, Date endTime,
			String dispatchException) {
		return false;
	}

}
