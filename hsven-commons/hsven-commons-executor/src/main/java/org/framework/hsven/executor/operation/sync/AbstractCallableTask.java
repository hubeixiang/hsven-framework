package org.framework.hsven.executor.operation.sync;

import org.framework.hsven.executor.operation.TaskInfo;
import org.framework.hsven.executor.provider.ISyncTaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * 抽象的callable接口实现类
 *
 * @param <T>
 */
public abstract class AbstractCallableTask<T> extends TaskInfo implements Callable<ISyncTaskResult<T>> {
    private static Logger logger = LoggerFactory.getLogger(AbstractCallableTask.class);
    private ISyncTaskResult<T> syncTaskResult;

    public AbstractCallableTask(String taskGroup, String taskId) {
        super(taskGroup, taskId);
        syncTaskResult = new SyncTaskResult<T>(taskGroup, taskId);
    }

    public ISyncTaskResult<T> call() throws Exception {
        long taskExecuteBeginTimeMS = System.currentTimeMillis();
        try {
            syncTaskResult.setTaskExecuteBeginTimeMS(taskExecuteBeginTimeMS);
            T dealResult = deal();
            syncTaskResult.setResult(dealResult);
            syncTaskResult.setDealFlag(true);
            return syncTaskResult;
        } catch (Exception e) {
            syncTaskResult.setDealFlag(false);
            syncTaskResult.setDealException(e);
            logger.error("SyncCallableTask deal Exception.syncTaskResult:" + syncTaskResult, e);
        } catch (Throwable e) {
            syncTaskResult.setDealFlag(false);
            syncTaskResult.setDealException(e);
            logger.error("SyncCallableTask deal Throwable.syncTaskResult:" + syncTaskResult, e);
        } finally {
            long taskExecuteUsingTimeMS = System.currentTimeMillis() - taskExecuteBeginTimeMS;
            syncTaskResult.setTaskExecuteUsingTimeMS(taskExecuteUsingTimeMS);
        }
        return syncTaskResult;
    }

    public abstract T deal() throws Exception;

    @Override
    public String toString() {
        return super.toString();
    }

    public ISyncTaskResult<T> getSyncTaskResult() {
        return syncTaskResult;
    }
}
