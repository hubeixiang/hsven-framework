import org.framework.hsven.cxfws.server.restful.ChangeEvent;
import org.framework.hsven.cxfws.server.restful.EventDetail;
import org.framework.hsven.cxfws.server.restful.EventDetailAlarm;
import org.framework.hsven.cxfws.server.restful.EventDetailPM;
import org.framework.hsven.cxfws.server.restful.MonitorNE;
import org.framework.hsven.cxfws.server.restful.QueryAlarmRespsonse;
import org.framework.hsven.cxfws.server.restful.QueryPMResponse;
import org.framework.hsven.cxfws.server.ws.SyncEventDetail;
import org.framework.hsven.cxfws.server.ws.SyncSheetStateDetail;
import org.framework.hsven.cxfws.server.xml.OpDetail;
import org.framework.hsven.cxfws.server.xml.OpDetailUtil;
import org.framework.hsven.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class RestMainTest {
    public static void main(String[] args) {
        //event detail
        EventDetail eventDetail = createEventDetail();
        //event detail pm
        EventDetailPM eventDetailPM = createEventDetailPM();
        //event detail alarm
        EventDetailAlarm eventDetailAlarm = createEventDetailAlarm();
        //change event
        ChangeEvent changeEvent = createChangeEvent();
        System.out.println(JsonUtils.toJson(eventDetail));
        System.out.println(JsonUtils.toJson(eventDetailPM));
        System.out.println(JsonUtils.toJson(eventDetailAlarm));
        System.out.println(JsonUtils.toJson(changeEvent));

        QueryAlarmRespsonse queryAlarmRespsonse = createQueryAlarmRespsonse();
        QueryPMResponse queryPMResponse = createQueryPMResponse();
        String alarmJson = JsonUtils.toJson(queryAlarmRespsonse);
        String pmJson = JsonUtils.toJson(queryPMResponse);
        System.out.println(alarmJson);
        System.out.println(pmJson);
        alarmJson = "{\"eventId\":\"alarm event id\",\"alarmList\":[{\"networkType1\":0,\"networkType2\":0,\"vendor\":null,\"objType\":null,\"alarmTitle\":\"告警1\",\"eventTime\":\"2021-01-20 10:00:00\",\"cancelTime\":\"2021-01-20 10:00:00\",\"respDomain\":null},{\"networkType1\":0,\"networkType2\":0,\"vendor\":null,\"objType\":null,\"eventTime\":\"2021-01-20 10:00:00\",\"cancelTime\":\"2021-01-20 10:00:00\",\"respDomain\":null}]}";
        pmJson = "{\"eventId\":\"pm event id\",\"pmList\":[{\"objType\":null,\"objID\":\"网元id1\",\"objName\":null,\"pmType\":null,\"pmName\":null,\"sTime\":\"2021-01-20 00:00:00\",\"eTime\":\"2021-01-20 10:00:00\",\"period\":0,\"value\":0.0,\"threshold\":null,\"respDomain\":null},{\"objType\":null,\"objID\":\"网元id2\",\"objName\":null,\"pmType\":null,\"pmName\":null,\"sTime\":\"2021-01-20 00:00:00\",\"eTime\":\"2021-01-20 10:00:00\",\"period\":0,\"value\":0.0,\"threshold\":null,\"respDomain\":null,\"a\":\"b\"}]}";
        QueryAlarmRespsonse alarmRespsonse = JsonUtils.fromJson(alarmJson, QueryAlarmRespsonse.class);
        QueryPMResponse pmResponse = JsonUtils.fromJson(pmJson, QueryPMResponse.class);
        System.out.println("---------------");

        SyncEventDetail syncEventDetail = SyncEventDetail.of();
        SyncSheetStateDetail statusSheetDetail = SyncSheetStateDetail.of();
        OpDetail eventOpDetail = OpDetailUtil.formatEventDetail(eventDetail);
        OpDetail syncOpDetail = OpDetailUtil.formatEventDetail(syncEventDetail);
        OpDetail statusOpDetail = OpDetailUtil.formatEventDetail(statusSheetDetail);
        System.out.println(OpDetailUtil.toXmlString(eventOpDetail));
        System.out.println(OpDetailUtil.toXmlString(syncOpDetail));
        System.out.println(OpDetailUtil.toXmlString(statusOpDetail));
    }

    public static EventDetail createEventDetail() {
        EventDetail eventDetail = new EventDetail();
        List<MonitorNE> list = new ArrayList<>();
        eventDetail.setObjList(list);
        list.add(createMonitorNE("ne_objid_1"));
        list.add(createMonitorNE("ne_objid_2"));
        return eventDetail;
    }

    public static EventDetailPM createEventDetailPM() {
        return createEventDetailPM("ne_objid_1");
    }

    public static EventDetailAlarm createEventDetailAlarm() {
        return createEventDetailAlarm("alarm_title_告警");
    }

    public static ChangeEvent createChangeEvent() {
        ChangeEvent changeEvent = new ChangeEvent();
        changeEvent.setEventId("event-Id-value");
        changeEvent.setEventStatus(1);
        return changeEvent;
    }

    public static QueryAlarmRespsonse createQueryAlarmRespsonse() {
        QueryAlarmRespsonse queryAlarmRespsonse = new QueryAlarmRespsonse();
        queryAlarmRespsonse.setEventId("alarm event id");
        queryAlarmRespsonse.getAlarmList().add(createEventDetailAlarm("告警1"));
        queryAlarmRespsonse.getAlarmList().add(createEventDetailAlarm("告警2"));
        return queryAlarmRespsonse;
    }

    public static QueryPMResponse createQueryPMResponse() {
        QueryPMResponse queryPMResponse = new QueryPMResponse();
        queryPMResponse.setEventId("pm event id");
        queryPMResponse.getPmList().add(createEventDetailPM("网元id1"));
        queryPMResponse.getPmList().add(createEventDetailPM("网元id2"));
        return queryPMResponse;
    }

    private static EventDetailPM createEventDetailPM(String objID) {
        EventDetailPM eventDetailPM = new EventDetailPM();
        eventDetailPM.setObjID(objID);
        return eventDetailPM;
    }

    private static EventDetailAlarm createEventDetailAlarm(String alarmTitle) {
        EventDetailAlarm eventDetailAlarm = new EventDetailAlarm();
        eventDetailAlarm.setAlarmTitle(alarmTitle);
        return eventDetailAlarm;
    }

    private static MonitorNE createMonitorNE(String objID) {
        MonitorNE monitorNE = new MonitorNE();
        monitorNE.setObjID(objID);
        return monitorNE;
    }
}
