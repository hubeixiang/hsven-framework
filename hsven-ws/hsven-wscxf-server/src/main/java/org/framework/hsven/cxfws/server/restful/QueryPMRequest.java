package org.framework.hsven.cxfws.server.restful;

public class QueryPMRequest {
    private String eventId;
    private String eventTime = "2021-01-20 10:00:00";

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
