package org.framework.hsven.executor.operation.sync;

import org.framework.hsven.executor.operation.TaskInfo;

public class SyncTaskResult<T> extends TaskInfo {
    //任务创建时间
    private final long taskCreateTimeMS;
    //任务在线程池中开始执行时间
    private long taskExecuteBeginTimeMS;
    //任务在线程池中执行一共用时
    private long taskExecuteUsingTimeMS;
    //执行是否正常
    private boolean dealFlag;
    //执行异常时的异常保存
    private Throwable dealException;
    private T result;

    public SyncTaskResult(String taskGroup, String taskId) {
        super(taskGroup, taskId);
        taskCreateTimeMS = System.currentTimeMillis();
    }

    public long getTaskCreateTimeMS() {
        return taskCreateTimeMS;
    }

    public long getTaskExecuteBeginTimeMS() {
        return taskExecuteBeginTimeMS;
    }

    public void setTaskExecuteBeginTimeMS(long taskExecuteBeginTimeMS) {
        this.taskExecuteBeginTimeMS = taskExecuteBeginTimeMS;
    }

    public long getTaskExecuteUsingTimeMS() {
        return taskExecuteUsingTimeMS;
    }

    public void setTaskExecuteUsingTimeMS(long taskExecuteUsingTimeMS) {
        this.taskExecuteUsingTimeMS = taskExecuteUsingTimeMS;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SyncTaskResult [" + super.toString() + ", taskCreateTimeMS=" + taskCreateTimeMS + ", taskExecuteBeginTimeMS=" + taskExecuteBeginTimeMS + ", taskExecuteUsingTimeMS="
                + taskExecuteUsingTimeMS + ", result=" + result + "]";
    }

    public boolean isDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(boolean dealFlag) {
        this.dealFlag = dealFlag;
    }

    public Throwable getDealException() {
        return dealException;
    }

    public void setDealException(Throwable dealException) {
        this.dealException = dealException;
    }
}
