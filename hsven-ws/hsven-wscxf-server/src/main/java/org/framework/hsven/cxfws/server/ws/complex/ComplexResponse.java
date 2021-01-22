package org.framework.hsven.cxfws.server.ws.complex;

import java.util.Date;

public class ComplexResponse {
    private String id;
    private Date time;
    private InterEntity interEntity;
    private boolean isResponse;

    public static ComplexResponse rand() {
        InterEntity interEntity = InterEntity.of("response_str", 1, new Date());
        return of("res_id", new Date(), interEntity, true);
    }

    public static ComplexResponse of(String id, Date time, InterEntity interEntity, boolean isResponse) {
        ComplexResponse response = new ComplexResponse();
        response.id = id;
        response.time = time;
        response.interEntity = interEntity;
        response.isResponse = isResponse;
        return response;
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

    public boolean isResponse() {
        return isResponse;
    }

    public void setResponse(boolean response) {
        isResponse = response;
    }
}
