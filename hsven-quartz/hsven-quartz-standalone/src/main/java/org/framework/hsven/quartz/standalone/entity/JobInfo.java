package org.framework.hsven.quartz.standalone.entity;

import org.framework.hsven.quartz.core.entity.JobIdentity;

public class JobInfo extends JobIdentity {
    private Object param;

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
