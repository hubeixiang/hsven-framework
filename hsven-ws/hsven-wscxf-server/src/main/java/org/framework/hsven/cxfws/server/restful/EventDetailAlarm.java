package org.framework.hsven.cxfws.server.restful;

public class EventDetailAlarm {
    private int networkType1;
    private int networkType2;
    private String vendor;
    private String objType;
    private String alarmTitle;
    private String eventTime = "2021-01-20 10:00:00";
    private String cancelTime ="2021-01-20 10:00:00";
    private String respDomain;

    public int getNetworkType1() {
        return networkType1;
    }

    public void setNetworkType1(int networkType1) {
        this.networkType1 = networkType1;
    }

    public int getNetworkType2() {
        return networkType2;
    }

    public void setNetworkType2(int networkType2) {
        this.networkType2 = networkType2;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getRespDomain() {
        return respDomain;
    }

    public void setRespDomain(String respDomain) {
        this.respDomain = respDomain;
    }
}
