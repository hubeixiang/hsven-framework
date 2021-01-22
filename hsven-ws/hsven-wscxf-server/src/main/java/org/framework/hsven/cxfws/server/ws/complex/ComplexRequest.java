package org.framework.hsven.cxfws.server.ws.complex;

import java.util.Date;

public class ComplexRequest {
    private String id;
    private Date time;
    private InterEntity interEntity;
    private boolean isRequest;

    public static ComplexRequest rand() {
        InterEntity interEntity = InterEntity.of("req_str", 2, new Date());
        return of("req_id", new Date(), interEntity, true);
    }

    public static ComplexRequest of(String id, Date time, InterEntity interEntity, boolean isRequest) {
        ComplexRequest request = new ComplexRequest();
        request.id = id;
        request.time = time;
        request.interEntity = interEntity;
        request.isRequest = isRequest;
        return request;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public InterEntity getInterEntity() {
        return interEntity;
    }

    public void setInterEntity(InterEntity interEntity) {
        this.interEntity = interEntity;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }
}
