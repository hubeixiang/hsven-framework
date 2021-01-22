package org.framework.hsven.cxfws.server.ws;

public class SyncEventDetail {
    private String eventID;
    private int eventStatus = 1;
    private String recoveryTime = "2021-01-21 00:00:00";

    public static SyncEventDetail of() {
        return new SyncEventDetail();
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public int getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(int eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(String recoveryTime) {
        this.recoveryTime = recoveryTime;
    }
}
