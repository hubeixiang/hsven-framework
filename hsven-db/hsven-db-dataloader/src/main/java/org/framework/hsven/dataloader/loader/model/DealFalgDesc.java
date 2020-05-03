package org.framework.hsven.dataloader.loader.model;

import java.io.Serializable;

public class DealFalgDesc implements Serializable {
    private static final long serialVersionUID = 5;
    //处理开始时间
    private String beginTime;
    //处理耗时
    private long usingTimeMS;
    //是否正常执行完毕
    private boolean dealFlag;
    //执行异常时的异常保存
    private Throwable dealException;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public long getUsingTimeMS() {
        return usingTimeMS;
    }

    public void setUsingTimeMS(long usingTimeMS) {
        this.usingTimeMS = usingTimeMS;
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
        return String.format("DealFalgDesc[dealFlag=%s,dealException=%s,beginTime=%s,usingTimeMS=%s]", dealFlag, dealException == null ? null : dealException.getMessage(), beginTime, usingTimeMS);
    }
}
