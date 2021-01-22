package org.framework.hsven.cxfws.server.restful;

import java.util.ArrayList;
import java.util.List;

public class QueryPMResponse {
    private String eventId;
    private List<EventDetailPM> pmList = new ArrayList<>();

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<EventDetailPM> getPmList() {
        return pmList;
    }

    public void setPmList(List<EventDetailPM> pmList) {
        this.pmList = pmList;
    }
}
