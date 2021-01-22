package org.framework.hsven.cxfws.server.xml;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.framework.hsven.cxfws.server.restful.EventDetail;
import org.framework.hsven.cxfws.server.restful.MonitorNE;
import org.framework.hsven.cxfws.server.ws.SyncEventDetail;
import org.framework.hsven.cxfws.server.ws.SyncSheetStateDetail;
import org.framework.hsven.utils.JsonUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class OpDetailUtil {
    public static OpDetail formatEventDetail(EventDetail eventDetail) {
        OpDetail opDetail = OpDetail.of();
        if (eventDetail == null) {
            return opDetail;
        }

        RecordInfo recordInfo = RecordInfo.of();
        opDetail.getOpDetail().add(recordInfo);
        //主告警自身的属性
        addMainAlarm(recordInfo, eventDetail);
        //objList的属性
        List<MonitorNE> monitorNEList = eventDetail.getObjList();
        List<RecordInfo> merges = megreMonitorNE(monitorNEList);
        createFieldInfo(recordInfo, "网元监控描述", "objList", JsonUtils.toJson(merges));
        return opDetail;
    }

    public static OpDetail formatEventDetail(SyncEventDetail syncEventDetail) {
        OpDetail opDetail = OpDetail.of();
        if (syncEventDetail == null) {
            return opDetail;
        }

        RecordInfo recordInfo = RecordInfo.of();
        opDetail.getOpDetail().add(recordInfo);
        addSyncEventDetail(recordInfo, syncEventDetail);
        return opDetail;
    }

    public static OpDetail formatEventDetail(SyncSheetStateDetail syncSheetStateDetail) {
        OpDetail opDetail = OpDetail.of();
        if (syncSheetStateDetail == null) {
            return opDetail;
        }

        RecordInfo recordInfo = RecordInfo.of();
        opDetail.getOpDetail().add(recordInfo);
        addSyncSheetStateDetail(recordInfo, syncSheetStateDetail);
        return opDetail;
    }

    private static void addMainAlarm(RecordInfo recordInfo, EventDetail eventDetail) {
        createFieldInfo(recordInfo, "事件id", "eventID", eventDetail.getEventID());
        createFieldInfo(recordInfo, "事件名称", "eventName", eventDetail.getEventName());
        createFieldInfo(recordInfo, "事件专业", "eventNetworkType", eventDetail.getEventNetworkType());
        createFieldInfo(recordInfo, "区域划分", "areaLevel", eventDetail.getAreaLevel());
        createFieldInfo(recordInfo, "影响范围", "effArea", eventDetail.getEffArea());
        createFieldInfo(recordInfo, "保障级别", "areaEnsureLevel", eventDetail.getAreaEnsureLevel());
        createFieldInfo(recordInfo, "异常事件类别", "eventType", eventDetail.getEventType());
        createFieldInfo(recordInfo, "事件发生时间", "eventTime", eventDetail.getEventTime());
        createFieldInfo(recordInfo, "大场景名称", "areatype", eventDetail.getAreatype());
        createFieldInfo(recordInfo, "优先级", "areaLevel", eventDetail.getAreaLevel());
        createFieldInfo(recordInfo, "小场景名称", "areaName", eventDetail.getAreaName());
        createFieldInfo(recordInfo, "场景细分", "areaSub", eventDetail.getAreaSub());
        createFieldInfo(recordInfo, "影响范围", "effInfo", eventDetail.getEffInfo());
        createFieldInfo(recordInfo, "客户级别", "custLevel", eventDetail.getCustLevel());
        createFieldInfo(recordInfo, "保障级别", "areaEnsureLevel", eventDetail.getAreaEnsureLevel());
        createFieldInfo(recordInfo, "事件描述", "eventDetail", eventDetail.getEventDetail());
        createFieldInfo(recordInfo, "事件级别", "eventLevel", eventDetail.getEventLevel());
        createFieldInfo(recordInfo, "是否影响业务", "effService", eventDetail.getEffService());
        createFieldInfo(recordInfo, "预处理信息", "preprocInfo", eventDetail.getPreprocInfo());
        createFieldInfo(recordInfo, "定界结果", "faultReason", eventDetail.getFaultReason());
    }

    private static void addSubAlarm(RecordInfo recordInfo, MonitorNE monitorNE) {
        createFieldInfo(recordInfo, "网元类型", "objType", monitorNE.getObjType());
        createFieldInfo(recordInfo, "网元ID", "objID", monitorNE.getObjID());
        createFieldInfo(recordInfo, "网元名称", "objName", monitorNE.getObjName());
        createFieldInfo(recordInfo, "网元层级", "objLevel", monitorNE.getObjLevel());
        createFieldInfo(recordInfo, "机房名称", "roomName", monitorNE.getRoomName());
        createFieldInfo(recordInfo, "经度", "lon", monitorNE.getLon());
        createFieldInfo(recordInfo, "纬度", "lat", monitorNE.getLat());
    }

    private static void addSyncEventDetail(RecordInfo recordInfo, SyncEventDetail syncEventDetail) {
        createFieldInfo(recordInfo, "事件唯一标识", "eventID", syncEventDetail.getEventID());
        createFieldInfo(recordInfo, "恢复状态", "eventStatus", syncEventDetail.getEventStatus());
        createFieldInfo(recordInfo, "恢复时间", "recoveryTime", syncEventDetail.getRecoveryTime());
    }

    private static void addSyncSheetStateDetail(RecordInfo recordInfo, SyncSheetStateDetail syncSheetStateDetail) {
        createFieldInfo(recordInfo, "事件ID", "eventId", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "EOMS工单流水号", "sheetNo", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "EOMS工单ID", "sheetId", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "工单状态", "sheetStatus", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "状态时间", "statusTime", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "建单时间", "sendTime", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "操作人姓名", "operatorUserName", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "操作人账号", "operatorUserID", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "操作人三级部门", "operatorDeptTwo", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "操作人二级部门", "operatorDeptThree", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "操作人对应角色", "operatorRole", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "操作人联系方式", "operatorPhone", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "故障原因一级分类", "faultCauseType1", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "故障原因二级分类", "faultCauseType2", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "故障原因三级分类", "faultCauseType3", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "预案模板名称", "reservePlan", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "处理结果", "treatmentResults", syncSheetStateDetail.getEventId());
        createFieldInfo(recordInfo, "处理措施说明", "handleDetail", syncSheetStateDetail.getEventId());
    }

    private static List<RecordInfo> megreMonitorNE(List<MonitorNE> monitorNEList) {
        if (CollectionUtils.isEmpty(monitorNEList)) {
            return new ArrayList();
        }
        List<RecordInfo> merges = new ArrayList<>();
        for (MonitorNE monitorNE : monitorNEList) {
            RecordInfo recordInfo = RecordInfo.of();
            addSubAlarm(recordInfo, monitorNE);
            merges.add(recordInfo);
        }
        return merges;
    }

    private static void createFieldInfo(RecordInfo recordInfo, String fieldChName, String fieldEnName, Object fieldContent) {
        recordInfo.getRecord().add(FieldInfo.of(fieldChName, fieldEnName, fieldContent == null ? "" : String.valueOf(fieldContent)));
    }

    public static String toXmlString(OpDetail opDetail) {
        if (opDetail == null) {
            opDetail = OpDetail.of();
        }
        return internalToXmlString(opDetail);
    }

    private static String internalToXmlString(OpDetail opDetail) {
        Document document = internalToXml(opDetail);
        return document.asXML();
    }

    private static Document internalToXml(OpDetail opDetail) {
        Document document = DocumentHelper.createDocument();
        Element element = document.addElement("opDetail");
        if (opDetail == null || CollectionUtils.isEmpty(opDetail.getOpDetail())) {
        } else {
            for (RecordInfo recordInfo : opDetail.getOpDetail()) {
                internalToXml(document, element, recordInfo);
            }
        }
        return document;
    }


    private static void internalToXml(Document doc, Element elementParent, RecordInfo recordInfo) {
        if (recordInfo == null || CollectionUtils.isEmpty(recordInfo.getRecord())) {
            return;
        }
        Element element = elementParent.addElement("recordInfo");
        for (FieldInfo fieldInfo : recordInfo.getRecord()) {
            internalToXml(doc, element, fieldInfo);
        }
    }

    private static void internalToXml(Document doc, Element element, FieldInfo fieldInfo) {
        attachElement(element, "fieldChName", fieldInfo.getFieldChName());
        attachElement(element, "fieldEnName", fieldInfo.getFieldEnName());
        attachElement(element, "fieldContent", fieldInfo.getFieldContent());
    }

    private static void attachElement(Element element, String elementName, String elementValue) {
        if (StringUtils.isEmpty(elementValue)) {
            element.addElement(elementName).setText("");
        } else if (elementValue.indexOf('<') != -1 || elementValue.indexOf('>') != -1) {
            element.addElement(elementName).addCDATA(elementValue);
        } else {
            element.addElement(elementName).setText(elementValue);
        }
    }
}
