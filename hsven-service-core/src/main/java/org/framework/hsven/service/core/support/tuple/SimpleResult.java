package org.framework.hsven.service.core.support.tuple;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @description: 二元组
 * @author: sven
 * @create: 2021-09-25
 **/

public class SimpleResult implements Serializable {
    private boolean ok;
    private String msg;

    public static void appendMsg(SimpleResult simpleResult, String message) {
        appendMsg(simpleResult, message, ";");
    }

    public static void appendMsg(SimpleResult simpleResult, String message, String split) {
        if (StringUtils.isNotBlank(message)) {
            if (StringUtils.isNotBlank(simpleResult.getMsg())) {
                simpleResult.setMsg(simpleResult.getMsg() + split + message);
            } else {
                simpleResult.setMsg(message);
            }
        }
    }

    public static SimpleResult of() {
        return new SimpleResult();
    }

    public static SimpleResult ok() {
        SimpleResult simpleResult = of();
        simpleResult.setOk(true);
        return simpleResult;
    }

    public static SimpleResult failure(String msg) {
        SimpleResult simpleResult = of();
        simpleResult.setOk(false);
        simpleResult.setMsg(msg);
        return simpleResult;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
