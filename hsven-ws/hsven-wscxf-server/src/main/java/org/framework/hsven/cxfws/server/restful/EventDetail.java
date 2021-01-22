package org.framework.hsven.cxfws.server.restful;

import java.util.ArrayList;
import java.util.List;

public class EventDetail {
    private String eventID;
    private String eventName;
    private String eventNetworkType;
    private String areaLevel;
    private String effArea;
    private String areaEnsureLevel;
    private String eventType;
    private String eventTime = "2021-01-20 10:00:00";
    private String areatype;
    //    private String areaLevel;
    private String areaName;
    private String areaSub;
    private String effInfo;
    private String custLevel;
    //    private String areaEnsureLevel;
    private String eventDetail;
    private Integer eventLevel;
    private String effService;
    private String preprocInfo;
    private String faultReason;
    private List<MonitorNE> objList = new ArrayList<>();

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventNetworkType() {
        return eventNetworkType;
    }

    public void setEventNetworkType(String eventNetworkType) {
        this.eventNetworkType = eventNetworkType;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getEffArea() {
        return effArea;
    }

    public void setEffArea(String effArea) {
        this.effArea = effArea;
    }

    public String getAreaEnsureLevel() {
        return areaEnsureLevel;
    }

    public void setAreaEnsureLevel(String areaEnsureLevel) {
        this.areaEnsureLevel = areaEnsureLevel;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getAreatype() {
        return areatype;
    }

    public void setAreatype(String areatype) {
        this.areatype = areatype;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaSub() {
        return areaSub;
    }

    public void setAreaSub(String areaSub) {
        this.areaSub = areaSub;
    }

    public String getEffInfo() {
        return effInfo;
    }

    public void setEffInfo(String effInfo) {
        this.effInfo = effInfo;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public Integer getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(Integer eventLevel) {
        this.eventLevel = eventLevel;
    }

    public String getEffService() {
        return effService;
    }

    public void setEffService(String effService) {
        this.effService = effService;
    }

    public String getPreprocInfo() {
        return preprocInfo;
    }

    public void setPreprocInfo(String preprocInfo) {
        this.preprocInfo = preprocInfo;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }

    public List<MonitorNE> getObjList() {
        return objList;
    }

    public void setObjList(List<MonitorNE> objList) {
        this.objList = objList;
    }
}
