package org.framework.hsven.cxfws.server.ws;

public class NmsResult {
    private String eventID;
    private String errList;

    public static NmsResult of(String eventID, String errList) {
        NmsResult nmsResult = new NmsResult();
        nmsResult.eventID = eventID;
        nmsResult.errList = errList;
        return nmsResult;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getErrList() {
        return errList;
    }

    public void setErrList(String errList) {
        this.errList = errList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("eventID=").append(eventID == null ? "" : eventID).append(";")
                .append("errList=").append(errList == null ? "" : errList);
        return sb.toString();
    }
}
