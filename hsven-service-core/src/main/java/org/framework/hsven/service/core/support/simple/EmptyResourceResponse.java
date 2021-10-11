package org.framework.hsven.service.core.support.simple;

public final class EmptyResourceResponse implements ResourceResponse {
    public static final EmptyResourceResponse INSTANCE = new EmptyResourceResponse();
    public static final EmptyResourceResponse INSTANCE_OK = new EmptyResourceResponse("ok");
    private String state = "empty";

    public EmptyResourceResponse() {
    }

    public EmptyResourceResponse(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
