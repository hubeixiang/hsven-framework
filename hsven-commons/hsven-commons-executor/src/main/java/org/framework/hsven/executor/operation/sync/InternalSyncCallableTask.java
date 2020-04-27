package org.framework.hsven.executor.operation.sync;

import java.util.concurrent.Callable;

/**
 * 用于内部封装的Callable任务类
 *
 * @param <T>
 */
public class InternalSyncCallableTask<T> extends AbstractCallableTask {
    private Callable<T> syncTask;

    public InternalSyncCallableTask(String taskGroup, String taskId, Callable<T> syncTask) {
        super(taskGroup, taskId);
        this.syncTask = syncTask;
    }

    public T deal() throws Exception {
        return this.syncTask.call();
    }

}
