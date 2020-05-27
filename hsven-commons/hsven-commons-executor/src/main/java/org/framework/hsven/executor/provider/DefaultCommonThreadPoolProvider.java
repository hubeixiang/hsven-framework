package org.framework.hsven.executor.provider;

import org.framework.hsven.executor.exception.PoolExcuteException;
import org.framework.hsven.executor.model.ExecutorServiceInfo;
import org.framework.hsven.executor.operation.async.OperationDecorateAsync;
import org.framework.hsven.executor.operation.sync.OperationDecorateSync;
import org.framework.hsven.executor.support.CommonsExecutors;
import org.framework.hsven.executor.support.ThreadPoolExecutorManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;


@Service("defaultCommonThreadPoolProvider")
public class DefaultCommonThreadPoolProvider implements IThreadPoolProvider {
    public final static String Related_Loader_Thread_Pool_Name = "defaultCommonThreadPoolProvider";
    private OperationDecorateSync operationDecorateSync = new OperationDecorateSync();
    private OperationDecorateAsync operationDecorateAsync = new OperationDecorateAsync();

    @Override
    public String getDefaultPoolName() {
        return Related_Loader_Thread_Pool_Name;
    }

    @Override
    public ExecutorServiceInfo provider(String poolName) {
        if (StringUtils.isEmpty(poolName)) {
            poolName = getDefaultPoolName();
        }
        return CommonsExecutors.newFixedThreadPoolForCalculationAndNotRejected(poolName);
    }

    @Override
    public void shutdown(String poolName) {
        if (StringUtils.isEmpty(poolName)) {
            poolName = getDefaultPoolName();
        }
        ExecutorServiceInfo executorServiceInfo = ThreadPoolExecutorManager.getInstance().getExecutorService(poolName);
        if (executorServiceInfo != null && executorServiceInfo.isEnable()) {
            executorServiceInfo.getExecutorService().shutdown();
        }
    }

    @Override
    public boolean isShutdown(String poolName) {
        if (StringUtils.isEmpty(poolName)) {
            poolName = getDefaultPoolName();
        }
        ExecutorServiceInfo executorServiceInfo = ThreadPoolExecutorManager.getInstance().getExecutorService(poolName);
        if (executorServiceInfo != null && executorServiceInfo.isEnable()) {
            return executorServiceInfo.getExecutorService().isShutdown();
        }
        return true;
    }

    @Override
    public void execute(String poolName, Runnable runnable) {
        ExecutorServiceInfo executorServiceInfo = provider(poolName);
        if (executorServiceInfo != null && executorServiceInfo.isEnable()) {
            operationDecorateAsync.execute(executorServiceInfo.getExecutorService(), runnable);
            return;
        }
        throw new RuntimeException(String.format("poolName=%s thread pool not exists!", poolName));
    }

    @Override
    public <T> Future<T> submit(String poolName, Callable<T> task) throws PoolExcuteException {
        ExecutorServiceInfo executorServiceInfo = provider(poolName);
        if (executorServiceInfo != null && executorServiceInfo.isEnable()) {
            Future<T> result = operationDecorateSync.submitCallable(executorServiceInfo.getExecutorService(), task);
            return result;
        }
        throw new PoolExcuteException(String.format("poolName=%s thread pool not exists!", poolName));
    }

    @Override
    public <T> List<ISyncTaskResult<T>> executBySync(String poolName, List<Callable<T>> tasks) throws PoolExcuteException {
        ExecutorServiceInfo executorServiceInfo = provider(poolName);
        if (executorServiceInfo != null && executorServiceInfo.isEnable()) {
            List<ISyncTaskResult<T>> results = operationDecorateSync.submit(executorServiceInfo.getExecutorService(), tasks);
        }
        throw new PoolExcuteException(String.format("poolName=%s thread pool not exists!", poolName));
    }
}
