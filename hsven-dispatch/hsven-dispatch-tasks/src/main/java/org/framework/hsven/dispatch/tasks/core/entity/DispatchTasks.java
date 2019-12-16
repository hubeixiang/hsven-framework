package org.framework.hsven.dispatch.tasks.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 派发任务的任务实体
 *
 * @author sven
 * @date 2019/10/16 10:25
 */
public class DispatchTasks implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long taskId;
	private String userId;
	private String taskType;
	private Integer taskDispatchStatus;
	private String taskParam;
	private String createClient;
	private String executeClient;
	//默认未数据库插入时间
	private Date createTime;
	private Date beginTime;
	private Date endTime;
	//当派发失败时,派发的异常原因
	private String dispatchException;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Integer getTaskDispatchStatus() {
		return taskDispatchStatus;
	}

	public void setTaskDispatchStatus(Integer taskDispatchStatus) {
		this.taskDispatchStatus = taskDispatchStatus;
	}

	public String getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(String taskParam) {
		this.taskParam = taskParam;
	}

	public String getCreateClient() {
		return createClient;
	}

	public void setCreateClient(String createClient) {
		this.createClient = createClient;
	}

	public String getExecuteClient() {
		return executeClient;
	}

	public void setExecuteClient(String executeClient) {
		this.executeClient = executeClient;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDispatchException() {
		return dispatchException;
	}

	public void setDispatchException(String dispatchException) {
		this.dispatchException = dispatchException;
	}
}
