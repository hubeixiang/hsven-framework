package org.framework.hsven.cxfws.server.restful;

public class MonitorNEmerge {
    private String objType;
    private String objID;
    private String objName;
    private String objLevel;
    private String roomName;
    private String lon;
    private String lat;

    public static MonitorNEmerge of() {
        return new MonitorNEmerge();
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getObjID() {
        return objID;
    }

    public void setObjID(String objID) {
        this.objID = objID;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String getObjLevel() {
        return objLevel;
    }

    public void setObjLevel(String objLevel) {
        this.objLevel = objLevel;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
