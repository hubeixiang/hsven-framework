package org.framework.hsven.executor.operation.async;


public class AsyncTaskResult<T extends Runnable> extends AsyncTask {
    //执行是否正常
    private boolean dealFlag;
    //执行异常时的异常保存
    private Throwable dealException;

    public AsyncTaskResult(String taskGroup, String taskId) {
        super(taskGroup, taskId);
    }

    public boolean isDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(boolean dealFlag) {
        this.dealFlag = dealFlag;
    }

    public Throwable getDealException() {
        return dealException;
    }

    public void setDealException(Throwable dealException) {
        this.dealException = dealException;
    }

    @Override
    public String toString() {
        return String.format("AsyncTaskResult[%s,dealFlag=%s,dealException=%s]", super.toString(), dealFlag, dealException);
    }
}
