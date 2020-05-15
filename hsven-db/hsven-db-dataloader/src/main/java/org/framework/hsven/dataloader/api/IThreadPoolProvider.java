package org.framework.hsven.dataloader.api;

import org.framework.hsven.executor.operation.sync.SyncTaskResult;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public interface IThreadPoolProvider {
    public String getDefaultPoolName();

    public ExecutorService provider(String poolName);

    public void shutdown(String poolName);

    public <V> List<SyncTaskResult<V>> executBySync(String poolName, List<Callable<V>> tasks);
}
