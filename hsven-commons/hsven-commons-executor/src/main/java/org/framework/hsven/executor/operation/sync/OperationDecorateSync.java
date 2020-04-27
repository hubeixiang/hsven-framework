package org.framework.hsven.executor.operation.sync;

import org.framework.hsven.executor.operation.TaskConstansts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 线程池的一些同步的通用操作封装
 */
public class OperationDecorateSync {
    private static Logger logger = LoggerFactory.getLogger(OperationDecorateSync.class);

    public <T> SyncTaskResult<T> submit(ExecutorService executorService, Callable<T> task) {
        try {
            if (task != null) {
                InternalSyncCallableTask<T> syncCallableTask =
                        new InternalSyncCallableTask(TaskConstansts.DEFAULT_TASK_GROUP, TaskConstansts.DEFAULT_TASK_ID, task);

                Future<SyncTaskResult<T>> future = executorService.submit(syncCallableTask);
                SyncTaskResult<T> result = getSyncFutureResult(future);
                return result;
            }
        } catch (Exception e) {
            logger.error("SyncOperationDecorate submit Exception.", e);
        } catch (Throwable e) {
            logger.error("SyncOperationDecorate submit Throwable.", e);
        } finally {
        }
        return null;
    }

    public <T> List<SyncTaskResult<T>> submit(ExecutorService executorService, List<Callable<T>> taskList) {
        try {
            if (taskList != null) {
                int taskListSize = taskList.size();
                List<Future<SyncTaskResult<T>>> futureList = new ArrayList<>();
                for (int i = 0; i < taskListSize; i++) {
                    Callable<T> task = taskList.get(i);
                    InternalSyncCallableTask<T> syncCallableTask =
                            new InternalSyncCallableTask(TaskConstansts.DEFAULT_TASK_GROUP, TaskConstansts.DEFAULT_TASK_ID + i, task);
                    Future<SyncTaskResult<T>> future = executorService.submit(syncCallableTask);
                    if (future != null) {
                        futureList.add(future);
                    }
                }

                List<SyncTaskResult<T>> return_list = getSyncFutureResult(futureList);
                return return_list;
            }
        } catch (Exception e) {
            logger.error("SyncOperationDecorate submit Exception.", e);
        } catch (Throwable e) {
            logger.error("SyncOperationDecorate submit Throwable.", e);
        } finally {
        }
        return null;
    }

    public <T> SyncTaskResult<T> submitSyncTask(ExecutorService executorService, AbstractCallableTask<T> task) {
        try {
            if (task != null) {
                Future<SyncTaskResult<T>> future = executorService.submit(task);
                SyncTaskResult<T> result = getSyncFutureResult(future);
                return result;
            }
        } catch (Exception e) {
            logger.error("SyncOperationDecorate submitSyncTask Exception.", e);
        } catch (Throwable e) {
            logger.error("SyncOperationDecorate submitSyncTask Throwable.", e);
        } finally {
        }
        return null;
    }

    public <T> List<SyncTaskResult<T>> submitSyncTasks(ExecutorService executorService, List<AbstractCallableTask<T>> taskList) {
        try {
            if (taskList != null) {
                int taskListSize = taskList.size();
                List<Future<SyncTaskResult<T>>> futureList = new ArrayList<>();
                for (int i = 0; i < taskListSize; i++) {
                    AbstractCallableTask<T> task = taskList.get(i);
                    Future<SyncTaskResult<T>> future = executorService.submit(task);
                    if (future != null) {
                        futureList.add(future);
                    }
                }

                List<SyncTaskResult<T>> return_list = getSyncFutureResult(futureList);
                return return_list;
            }
        } catch (Exception e) {
            logger.error("SyncOperationDecorate submitSyncTasks Exception.", e);
        } catch (Throwable e) {
            logger.error("SyncOperationDecorate submitSyncTasks Throwable.", e);
        } finally {
        }
        return null;
    }


    private <E> E getSyncFutureResult(Future<E> future) throws ExecutionException, InterruptedException {
        E result = future.get();
        return result;
    }

    private <E> List<E> getSyncFutureResult(List<Future<E>> futureList) {
        int futureListSize = futureList.size();
        List<E> return_list = new ArrayList<>(futureListSize);
        for (int i = 0; i < futureListSize; i++) {
            Future<E> future = futureList.get(i);
            try {
                E result = future.get();
                return_list.add(result);
            } catch (Exception e) {
                logger.error("future get Exception.", e);
            } catch (Throwable e) {
                logger.error("future get Throwable.", e);
            }
        }
        return return_list;
    }
}
