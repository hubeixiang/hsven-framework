package org.framework.hsven.executor.provider;

/**
 * 异步执行的结果接口
 *
 * @param <E>
 */
public interface ISyncTaskResult<E> {

    /**
     * 任务创建时间点
     *
     * @return
     */
    public long getTaskCreateTimeMS();

    /**
     * 同步任务开始执行时间点
     *
     * @return
     */
    public long getTaskExecuteBeginTimeMS();

    public void setTaskExecuteBeginTimeMS(long taskExecuteBeginTimeMS);

    /**
     * 同步任务执行耗时(毫秒)
     *
     * @return
     */
    public long getTaskExecuteUsingTimeMS();

    public void setTaskExecuteUsingTimeMS(long taskExecuteUsingTimeMS) ;

    /**
     * 真正的程序执行结果
     * 当 isDealFlag() = true 时程序执行异常,所以结果为null,可以使用getDealException()查看异常信息
     *
     * @return
     */
    public E getResult();

    public void setResult(E result);

    /**
     * 获取执行标志
     *
     * @return true:程序正常执行完毕,false:程序执行异常,此时可以使用getDealException()具体查看异常信息
     */
    public boolean isDealFlag();

    public void setDealFlag(boolean dealFlag);

    /**
     * 执行异常时的异常
     *
     * @return
     */
    public Throwable getDealException();

    public void setDealException(Throwable dealException);
}
