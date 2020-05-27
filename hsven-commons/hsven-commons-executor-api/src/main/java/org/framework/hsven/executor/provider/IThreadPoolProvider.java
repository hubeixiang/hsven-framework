package org.framework.hsven.executor.provider;

import org.framework.hsven.executor.exception.PoolExcuteException;
import org.framework.hsven.executor.model.ExecutorServiceInfo;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface IThreadPoolProvider {
    /**
     * 获取当前线程池默认名称
     *
     * @return
     */
    public String getDefaultPoolName();

    /**
     * 获取指定线程名的线程池
     *
     * @param poolName 指定的线程池名称
     * @return
     */
    public ExecutorServiceInfo provider(String poolName);

    /**
     * 关闭指定名称的线程池
     *
     * @param poolName
     */
    public void shutdown(String poolName);

    /**
     * 判断指定名称的线程池是否关闭
     *
     * @param poolName
     * @return
     */
    public boolean isShutdown(String poolName);

    /**
     * 添加可执行对象到指定名称的线程池中
     * 异步执行
     *
     * @param poolName
     * @param runnable
     * @throws PoolExcuteException
     */
    public void execute(String poolName, Runnable runnable) throws PoolExcuteException;

    /**
     * 添加可回调的对象到指定名称的线程池中,具体执行时需要再次将改方法的返回对象再次调用get方法,获取执行结果
     *
     * @param poolName
     * @param task
     * @param <T>
     * @return
     * @throws PoolExcuteException
     */
    public <T> Future<T> submit(String poolName, Callable<T> task) throws PoolExcuteException;


    /**
     * 添加可回调的对象列表到指定名称的线程池中
     *
     * @param poolName
     * @param tasks
     * @param <T>
     * @return
     * @throws PoolExcuteException
     */
    public <T> List<ISyncTaskResult<T>> executBySync(String poolName, List<Callable<T>> tasks) throws PoolExcuteException;
}
