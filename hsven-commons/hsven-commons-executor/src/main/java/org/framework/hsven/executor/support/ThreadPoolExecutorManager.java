package org.framework.hsven.executor.support;

import org.framework.hsven.executor.model.ExecutorServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadPoolExecutorManager {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorManager.class);
    private static final ThreadPoolMaintainThread maintainThread = new ThreadPoolMaintainThread();
    private static final Map<String, ExecutorServiceInfo> managerExecutorServiceMaps = new HashMap<>();
    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static ThreadPoolExecutorManager instance;

    private ThreadPoolExecutorManager() {
        //设置维护线程名
        maintainThread.setName("ThreadPoolExecutorManager-MaintainThread");
        //启动维护线程
        maintainThread.start();
    }

    public static ThreadPoolExecutorManager getInstance() {

        if (instance != null) {
            return instance;
        }

        return createInstance();
    }

    private static synchronized ThreadPoolExecutorManager createInstance() {
        if (instance != null) {
            return instance;
        }

        instance = new ThreadPoolExecutorManager();
        return instance;
    }

    public void shutdown(String poolName) {
        ExecutorService executorService = checkExecutorService(poolName);
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public List<Runnable> shutdownNow(String poolName) {
        ExecutorService executorService = checkExecutorService(poolName);
        if (executorService != null) {
            return executorService.shutdownNow();
        }
        return null;
    }

    boolean isShutdown(String poolName) {
        ExecutorService executorService = checkExecutorService(poolName);
        if (executorService != null) {
            return executorService.isShutdown();
        }
        return true;
    }

    public boolean isTerminated(String poolName) {
        ExecutorService executorService = checkExecutorService(poolName);
        if (executorService != null) {
            return executorService.isTerminated();
        }
        return true;
    }

    public boolean awaitTermination(String poolName, long timeout, TimeUnit unit)
            throws InterruptedException {
        ExecutorService executorService = checkExecutorService(poolName);
        if (executorService != null) {
            return executorService.awaitTermination(timeout, unit);
        }
        return true;
    }

    public void clearExpire() {
        try {
            readWriteLock.writeLock().lock();
            Set<String> clearPoolName = new HashSet<String>();
            Date clearTime = new Date();
            for (Map.Entry<String, ExecutorServiceInfo> entry : managerExecutorServiceMaps.entrySet()) {
                String poolName = entry.getKey();
                ExecutorServiceInfo serviceInfo = entry.getValue();
                if (serviceInfo == null || serviceInfo.getExecutorService() == null) {
                    //已经没有了线程池的需要清除
                    clearPoolName.add(poolName);
                } else if (serviceInfo != null && serviceInfo.getExecutorService() != null &&
                        (serviceInfo.getExecutorService().isShutdown() || serviceInfo.getExecutorService().isTerminated())) {
                    //线程池已经被关闭了的
                    clearPoolName.add(poolName);
                } else if (serviceInfo != null && serviceInfo.getExecutorService() != null &&
                        (serviceInfo.getExpiredDate() != null && serviceInfo.getExpiredDate().getTime() < clearTime.getTime())) {
                    clearPoolName.add(poolName);
                }
            }
            if (clearPoolName.size() > 0) {
                logger.info(String.format("ThreadPoolExecutorManager clearExpire=%s", clearPoolName));
                for (String poolName : clearPoolName) {
                    ExecutorServiceInfo serviceInfo = managerExecutorServiceMaps.get(poolName);
                    if (serviceInfo == null || serviceInfo.getExecutorService() == null) {
                        //直接清除
                        managerExecutorServiceMaps.remove(poolName);
                    } else if (serviceInfo != null && serviceInfo.getExecutorService() != null &&
                            (serviceInfo.getExecutorService().isShutdown() || serviceInfo.getExecutorService().isTerminated())) {
                        //直接清除
                        managerExecutorServiceMaps.remove(poolName);
                    } else if (serviceInfo != null && serviceInfo.getExecutorService() != null &&
                            (serviceInfo.getExpiredDate() != null && serviceInfo.getExpiredDate().getTime() < clearTime.getTime())) {
                        //需要先关闭线程池,然后在清除
                        managerExecutorServiceMaps.remove(poolName);
                        serviceInfo.getExecutorService().shutdown();
                    }
                }
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private ExecutorService checkExecutorService(String poolName) {
        try {
            readWriteLock.readLock().lock();
            ExecutorServiceInfo executorServiceInfo = managerExecutorServiceMaps.get(poolName);
            if (executorServiceInfo != null && executorServiceInfo.getExecutorService() != null) {
                return executorServiceInfo.getExecutorService();
            }
            return null;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public Map info() {
        return managerExecutorServiceMaps;
    }

    //可序列号的信息
    public Map infoSerializable() {
        Map<String, ExecutorServiceInfo> map = new HashMap();
        map.putAll(managerExecutorServiceMaps);
        Set<String> keys = new HashSet<>();
        keys.addAll(map.keySet());
        for (String key : keys) {
            ExecutorServiceInfo info = map.get(key);
            map.put(key, info.toSerializable());
        }
        return map;
    }

    public void addExecutorServiceInfo(ExecutorServiceInfo executorServiceInfo) {
        String poolName = executorServiceInfo.getPoolName();
        try {
            readWriteLock.writeLock().lock();
            boolean isExists = false;
            if (managerExecutorServiceMaps.containsKey(poolName)) {
                //需要先将存在的删除,删除时也将其停止
                ExecutorServiceInfo alreadExists = managerExecutorServiceMaps.get(poolName);
                managerExecutorServiceMaps.remove(poolName);
                if (alreadExists != null && alreadExists.getExecutorService() != null) {
                    //不再接受新任务了，但是队列里的任务得执行完毕。
                    isExists = true;
                    logger.warn(String.format("add thread pool = %s,but this pool alread exists.so must delete", poolName));
                    alreadExists.getExecutorService().shutdown();
                }
            }
            logger.warn(String.format("add thread pool = %s,is exists = %s", poolName, isExists));
            managerExecutorServiceMaps.put(poolName, executorServiceInfo);
        } catch (Throwable t) {
            logger.error(String.format("ThreadPoolExecutorManager.addExecutorServiceInfo(%s) Exception:%s", poolName, t.getMessage()), t);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public ExecutorServiceInfo getExecutorService(String poolName) {
        ExecutorServiceInfo executorService = null;
        try {
            readWriteLock.readLock().lock();
            executorService = managerExecutorServiceMaps.get(poolName);
        } catch (Throwable t) {
            logger.error(String.format("ThreadPoolExecutorManager.getExecutorService(%s) Exception:%s", poolName, t.getMessage()), t);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return executorService;
    }


    public ExecutorServiceInfo removeExecutorService(String poolName) {
        ExecutorServiceInfo executorService = null;
        try {
            readWriteLock.writeLock().lock();
            if (managerExecutorServiceMaps.containsKey(poolName)) {
                executorService = managerExecutorServiceMaps.get(poolName);
                managerExecutorServiceMaps.remove(poolName);
            }
        } catch (Throwable t) {
            logger.error(String.format("ThreadPoolExecutorManager.removeExecutorService(%s) Exception:%s", poolName, t.getMessage()), t);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return executorService;
    }
}
