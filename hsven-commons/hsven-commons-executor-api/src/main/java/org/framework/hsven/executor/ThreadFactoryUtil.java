package org.framework.hsven.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadFactoryUtil {

    /**
     * 系统默认的线程产生工程
     *
     * @return
     */
    public static ThreadFactory createDefaultThreadFactory() {
        return Executors.defaultThreadFactory();
    }

    /**
     * 自定义的线程产生工程
     *
     * @param poolName
     * @return
     */
    public static ThreadFactory createCustomThreadFactory(String poolName) {
        return new ThreadFactoryImpl(poolName);
    }
}
