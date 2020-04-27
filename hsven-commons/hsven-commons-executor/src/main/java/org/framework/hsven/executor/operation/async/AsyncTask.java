package org.framework.hsven.executor.operation.async;


import org.framework.hsven.executor.operation.TaskInfo;

public class AsyncTask<T extends Runnable> extends TaskInfo {
    private T runnable;

    public AsyncTask(String taskGroup, String taskId) {
        super(taskGroup, taskId);
    }

    public T getRunnable() {
        return runnable;
    }

    public void setRunnable(T runnable) {
        this.runnable = runnable;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
