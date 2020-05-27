package org.framework.hsven.executor.model;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;

public class ExecutorServiceInfo extends CacheExpire implements Serializable {
    private static final long serialVersionUID = 2L;
    private String poolName;
    private ExecutorService executorService;
    private ThreadPoolConfig threadPoolConfig;

    public boolean isEnable() {
        return executorService != null;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ThreadPoolConfig getThreadPoolConfig() {
        return threadPoolConfig;
    }

    public void setThreadPoolConfig(ThreadPoolConfig threadPoolConfig) {
        this.threadPoolConfig = threadPoolConfig;
    }

    public ExecutorServiceInfo toSerializable() {
        ExecutorServiceInfo executorServiceInfo = new ExecutorServiceInfo();
        executorServiceInfo.setThreadPoolConfig(this.threadPoolConfig);
        executorServiceInfo.setPoolName(this.poolName);
        executorServiceInfo.setCreateDate(this.getCreateDate());
        executorServiceInfo.setExpiredDate(this.getExpiredDate());
        return executorServiceInfo;
    }
}
