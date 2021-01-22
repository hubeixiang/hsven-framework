package org.framework.hsven.cxfws.server.restful;

import java.util.ArrayList;
import java.util.List;

public class QueryAlarmRespsonse {
    private String eventId;
    private List<EventDetailAlarm> alarmList = new ArrayList<>();

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<EventDetailAlarm> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<EventDetailAlarm> alarmList) {
        this.alarmList = alarmList;
    }
}
