package org.framework.hsven.dataloader.api.impl;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.api.IThreadPoolProvider;
import org.framework.hsven.executor.operation.sync.OperationDecorateSync;
import org.framework.hsven.executor.operation.sync.SyncTaskResult;
import org.framework.hsven.executor.support.CommonsExecutors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class DefaultThreadPoolProvider implements IThreadPoolProvider {
    public final static String Related_Loader_Thread_Pool_Name = "RelatedTableLoaderPool";
    private OperationDecorateSync operationDecorateSync = new OperationDecorateSync();

    @Override
    public String getDefaultPoolName() {
        return Related_Loader_Thread_Pool_Name;
    }

    @Override
    public ExecutorService provider(String poolName) {
        if (StringUtils.isEmpty(poolName)) {
            poolName = getDefaultPoolName();
        }
        return CommonsExecutors.newFixedThreadPoolForCalculationAndNotRejected(poolName).getExecutorService();
    }

    @Override
    public void shutdown(String poolName) {
//        if (StringUtils.isEmpty(poolName)) {
//            poolName = getDefaultPoolName();
//        }
//        ThreadPoolExecutorManager.getInstance().shutdown(poolName);
        //在默认的数据关联加载中,都使用一个线程池,因此不能关闭此线程池
    }

    @Override
    public <V> List<SyncTaskResult<V>> executBySync(String poolName, List<Callable<V>> tasks) {
        ExecutorService executorService = provider(poolName);
        List<SyncTaskResult<V>> results = operationDecorateSync.submit(executorService, tasks);
        return results;
    }

}
