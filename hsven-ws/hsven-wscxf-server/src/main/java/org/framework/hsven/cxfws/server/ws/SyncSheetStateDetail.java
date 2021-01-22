package org.framework.hsven.cxfws.server.ws;

public class SyncSheetStateDetail {
    private String eventId;
    private String sheetNo;
    private String sheetId;
    private String sheetStatus;
    private String statusTime = "2021-01-21 10:00:00";
    private String sendTime = "2021-01-21 10:00:00";
    private String operateDept;
    private String operatorUserName;
    private String operatorUserID;
    private String operatorDeptTwo;
    private String operatorDeptThree;
    private String operatorRole;
    private String operatorPhone;
    private String faultCauseType1Pre;
    private String faultCauseType2;
    private String faultCauseType3;
    private String reservePlan;
    private String faultCauseType1Fact;
    private String faultCauseType2Fact;
    private String faultCauseType3Fact;
    private String treatmentResults;
    private String handleDetail;

    public static SyncSheetStateDetail of() {
        return new SyncSheetStateDetail();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getSheetStatus() {
        return sheetStatus;
    }

    public void setSheetStatus(String sheetStatus) {
        this.sheetStatus = sheetStatus;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getOperateDept() {
        return operateDept;
    }

    public void setOperateDept(String operateDept) {
        this.operateDept = operateDept;
    }

    public String getOperatorUserName() {
        return operatorUserName;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }

    public String getOperatorUserID() {
        return operatorUserID;
    }

    public void setOperatorUserID(String operatorUserID) {
        this.operatorUserID = operatorUserID;
    }

    public String getOperatorDeptTwo() {
        return operatorDeptTwo;
    }

    public void setOperatorDeptTwo(String operatorDeptTwo) {
        this.operatorDeptTwo = operatorDeptTwo;
    }

    public String getOperatorDeptThree() {
        return operatorDeptThree;
    }

    public void setOperatorDeptThree(String operatorDeptThree) {
        this.operatorDeptThree = operatorDeptThree;
    }

    public String getOperatorRole() {
        return operatorRole;
    }

    public void setOperatorRole(String operatorRole) {
        this.operatorRole = operatorRole;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getFaultCauseType1Pre() {
        return faultCauseType1Pre;
    }

    public void setFaultCauseType1Pre(String faultCauseType1Pre) {
        this.faultCauseType1Pre = faultCauseType1Pre;
    }

    public String getFaultCauseType2() {
        return faultCauseType2;
    }

    public void setFaultCauseType2(String faultCauseType2) {
        this.faultCauseType2 = faultCauseType2;
    }

    public String getFaultCauseType3() {
        return faultCauseType3;
    }

    public void setFaultCauseType3(String faultCauseType3) {
        this.faultCauseType3 = faultCauseType3;
    }

    public String getReservePlan() {
        return reservePlan;
    }

    public void setReservePlan(String reservePlan) {
        this.reservePlan = reservePlan;
    }

    public String getFaultCauseType1Fact() {
        return faultCauseType1Fact;
    }

    public void setFaultCauseType1Fact(String faultCauseType1Fact) {
        this.faultCauseType1Fact = faultCauseType1Fact;
    }

    public String getFaultCauseType2Fact() {
        return faultCauseType2Fact;
    }

    public void setFaultCauseType2Fact(String faultCauseType2Fact) {
        this.faultCauseType2Fact = faultCauseType2Fact;
    }

    public String getFaultCauseType3Fact() {
        return faultCauseType3Fact;
    }

    public void setFaultCauseType3Fact(String faultCauseType3Fact) {
        this.faultCauseType3Fact = faultCauseType3Fact;
    }

    public String getTreatmentResults() {
        return treatmentResults;
    }

    public void setTreatmentResults(String treatmentResults) {
        this.treatmentResults = treatmentResults;
    }

    public String getHandleDetail() {
        return handleDetail;
    }

    public void setHandleDetail(String handleDetail) {
        this.handleDetail = handleDetail;
    }
}
