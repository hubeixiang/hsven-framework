package org.framework.hsven.executor.operation;

import java.io.Serializable;

/**
 * 要执行的任务的信息描述,方便在线程池调度时,方便跟踪任务
 */

public class TaskInfo implements Serializable {
    private static final long serialVersionUID = 10L;
    private final String taskGroup;
    private final String taskId;

    public TaskInfo(String taskGroup, String taskId) {
        this.taskGroup = taskGroup;
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public String toString() {
        return "TaskInfo [taskGroup=" + getTaskGroup() + ", taskId=" + getTaskId() + "]";
    }
}
