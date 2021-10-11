package org.framework.hsven.service.core.support.simple;

public class SimpleResourceResponse<T> implements ResourceResponse {
    private T context;

    public static <T> SimpleResourceResponse of() {
        return new SimpleResourceResponse();
    }

    public static <T> SimpleResourceResponse of(T context) {
        SimpleResourceResponse<T> simpleResourceResponse = new SimpleResourceResponse<T>();
        simpleResourceResponse.setContext(context);
        return simpleResourceResponse;
    }

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        this.context = context;
    }
}
