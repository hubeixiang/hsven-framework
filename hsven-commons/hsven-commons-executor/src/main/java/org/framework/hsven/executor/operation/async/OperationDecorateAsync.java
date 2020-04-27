package org.framework.hsven.executor.operation.async;

import org.framework.hsven.executor.operation.TaskConstansts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 线程池的一些异步的通用操作封装
 */
public class OperationDecorateAsync {
    private static Logger logger = LoggerFactory.getLogger(OperationDecorateAsync.class);

    /**
     * 添加异步执行的任务
     *
     * @param executorService
     * @param runnable
     * @throws Exception
     */
    public void execute(ExecutorService executorService, Runnable runnable) {
        AsyncTask asyncTask = new AsyncTask(TaskConstansts.DEFAULT_TASK_GROUP, TaskConstansts.DEFAULT_TASK_ID);
        asyncTask.setRunnable(runnable);
        executeAsyncTask(executorService, asyncTask);
    }


    /**
     * 返回错误的未能添加执行队列的任务列表
     *
     * @param executorService
     * @param runnables
     * @return 返回的是未能添加执行的任务列表, 为null或者size=0表明全部执行成功
     */
    public List<AsyncTaskResult> execute(ExecutorService executorService, List<? extends Runnable> runnables) {
        int size = runnables == null ? 0 : runnables.size();
        List<AsyncTask> asyncTaskList = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            Runnable runnable = runnables.get(index);
            AsyncTask asyncTask = new AsyncTask(TaskConstansts.DEFAULT_TASK_GROUP, TaskConstansts.DEFAULT_TASK_ID + index);
            asyncTask.setRunnable(runnable);
            asyncTaskList.add(asyncTask);
        }
        return executeAsyncTasks(executorService, asyncTaskList);
    }

    /**
     * 添加异步执行的任务
     *
     * @param executorService
     * @param asyncTask
     * @throws Exception
     */
    public void executeAsyncTask(ExecutorService executorService, AsyncTask asyncTask) {
        executorService.execute(asyncTask.getRunnable());
    }


    /**
     * 返回错误的未能添加执行队列的任务列表
     *
     * @param executorService
     * @param runnables
     * @return 返回的是未能添加执行的任务列表, 为null或者size=0表明全部执行成功
     */
    public List<AsyncTaskResult> executeAsyncTasks(ExecutorService executorService, List<AsyncTask> runnables) {
        List<AsyncTaskResult> errList = new ArrayList<>();
        int size = runnables == null ? 0 : runnables.size();
        for (int index = 0; index < size; index++) {
            AsyncTask runnable = runnables.get(index);
            AsyncTaskResult asyncTaskResult = createAsyncTaskResult(runnable);
            try {
                executeAsyncTask(executorService, runnable);
                asyncTaskResult.setDealFlag(true);
                asyncTaskResult.setDealException(null);
            } catch (Throwable e) {
                logger.error(String.format("%s Throwable:%s", asyncTaskResult.toString(), e.getMessage()), e);
                asyncTaskResult.setDealFlag(false);
                asyncTaskResult.setDealException(e);
                errList.add(asyncTaskResult);
            }
        }
        if (errList.size() > 0) {
            return errList;
        } else {
            return null;
        }
    }


    private AsyncTaskResult createAsyncTaskResult(AsyncTask asyncTask) {
        AsyncTaskResult asyncTaskResult = new AsyncTaskResult(asyncTask.getTaskGroup(), asyncTask.getTaskId());
        asyncTaskResult.setRunnable(asyncTask.getRunnable());
        return asyncTaskResult;
    }

}
