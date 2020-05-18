package org.framework.hsven.executor.support;

import org.framework.hsven.executor.ThreadFactoryUtil;
import org.framework.hsven.executor.model.ExecutorServiceInfo;
import org.framework.hsven.executor.model.ThreadPoolConfig;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @see java.util.concurrent.Executors
 */
public class CommonsExecutors {
    /**
     * 线程池数量应用于io
     *
     * @return
     */
    public static int getMaximumPoolSizeForIO() {
        int cpuNums = Runtime.getRuntime().availableProcessors();
        return cpuNums * 2;
    }

    /**
     * 线程池数量应用于计算
     *
     * @return
     */
    public static int getMaximumPoolSizeForCalculation() {
        int cpuNums = Runtime.getRuntime().availableProcessors();
        return cpuNums;
    }

    public static ExecutorServiceInfo newFixedThreadPool(String poolName, int nThreads) {
        return CommonsExecutors.newFixedThreadPool(poolName, nThreads, null);
    }

    public static ExecutorServiceInfo newFixedThreadPoolForIO(String poolName) {
        return CommonsExecutors.newFixedThreadPool(poolName, CommonsExecutors.getMaximumPoolSizeForIO(), null);
    }

    public static ExecutorServiceInfo newFixedThreadPoolForCalculation(String poolName) {
        return CommonsExecutors.newFixedThreadPool(poolName, CommonsExecutors.getMaximumPoolSizeForCalculation(), null);
    }

    public static ExecutorServiceInfo newFixedThreadPoolNotRejected(String poolName, int nThreads) {
        return CommonsExecutors.newFixedThreadPool(poolName, nThreads, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ExecutorServiceInfo newFixedThreadPoolForIOAndNotRejected(String poolName) {
        return CommonsExecutors.newFixedThreadPool(poolName, CommonsExecutors.getMaximumPoolSizeForIO(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ExecutorServiceInfo newFixedThreadPoolForCalculationAndNotRejected(String poolName) {
        return CommonsExecutors.newFixedThreadPool(poolName, CommonsExecutors.getMaximumPoolSizeForCalculation(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ExecutorServiceInfo newFixedThreadPool(String poolName, int nThreads, RejectedExecutionHandler rejectedExecutionHandler) {
        ExecutorServiceInfo executorServiceInfo = ThreadPoolExecutorManager.getInstance().getExecutorService(poolName);
        if (executorServiceInfo == null) {
            ThreadPoolExecutor threadPoolExecutor = null;
            if (rejectedExecutionHandler == null) {
                threadPoolExecutor = new ThreadPoolExecutor(nThreads, nThreads,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(), ThreadFactoryUtil.createCustomThreadFactory(poolName));
            } else {
                threadPoolExecutor = new ThreadPoolExecutor(nThreads, nThreads,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(), ThreadFactoryUtil.createCustomThreadFactory(poolName), rejectedExecutionHandler);
            }
            executorServiceInfo = new ExecutorServiceInfo();

            //初始化完特殊参数后在调用此方法,初始化其它特殊共有的参数
            configExecutorServiceInfo(poolName, executorServiceInfo, threadPoolExecutor);
        }

        return executorServiceInfo;
    }

    private static void configExecutorServiceInfo(String poolName, ExecutorServiceInfo executorServiceInfo, ThreadPoolExecutor threadPoolExecutor) {
        executorServiceInfo.setPoolName(poolName);
        executorServiceInfo.setCreateDate(new Date());
        executorServiceInfo.setExecutorService(threadPoolExecutor);

        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
        executorServiceInfo.setThreadPoolConfig(threadPoolConfig);
        threadPoolConfig.setPoolName(executorServiceInfo.getPoolName());

        configThreadPoolConfig(threadPoolConfig, threadPoolExecutor);

        ThreadPoolExecutorManager.getInstance().addExecutorServiceInfo(executorServiceInfo);
    }

    private static void configThreadPoolConfig(ThreadPoolConfig threadPoolConfig, ThreadPoolExecutor threadPoolExecutor) {
        threadPoolConfig.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        threadPoolConfig.setMaxPoolSize(threadPoolExecutor.getMaximumPoolSize());
    }
}
