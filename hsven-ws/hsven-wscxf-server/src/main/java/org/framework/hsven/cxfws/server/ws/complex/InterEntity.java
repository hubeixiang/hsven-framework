package org.framework.hsven.cxfws.server.ws.complex;

import java.util.Date;

public class InterEntity {
    private String str;
    private Integer number;
    private Date time;

    public static InterEntity of(String str, Integer number, Date time) {
        InterEntity entity = new InterEntity();
        entity.str = str;
        entity.number = number;
        entity.time = time;
        return entity;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
