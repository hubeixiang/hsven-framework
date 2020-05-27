package org.framework.hsven.executor.model;

public class ThreadPoolConfig {
    private String poolName;
    //线程池维护线程的最小数量,默认值为1.
    private int corePoolSize = 1;
    //线程池维护线程的最大数量,默认值为2的32次方-1,即2147483647.
    private int maxPoolSize = 10;

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }
}
