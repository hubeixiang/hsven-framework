package org.framework.hsven.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryImpl implements ThreadFactory {
    private final ThreadGroup group;
    private final String poolName;
    private final String namePrefix;
    private final AtomicInteger threadInitNumber = new AtomicInteger(1);

    ThreadFactoryImpl(String poolName) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        this.poolName = poolName;
        this.namePrefix = "pool-" + poolName + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        int threadNum = threadInitNumber.getAndIncrement();
        Thread t = new Thread(group, r,
                namePrefix + threadNum,
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
