package org.framework.hsven.dataloader.beans.data;

import java.io.Serializable;

public class RowInfo implements Serializable {
    private static final long serialVersionUID = 2L;
    private boolean isException = false;
    private Throwable throwable;
    private String exceptionDesc;

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getExceptionDesc() {
        return exceptionDesc;
    }

    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
    }
}
