package org.framework.hsven.cxfws.server.xml;

import java.util.ArrayList;
import java.util.List;

public class OpDetail {
    private List<RecordInfo> opDetail = new ArrayList<>();

    public static OpDetail of() {
        return new OpDetail();
    }

    public List<RecordInfo> getOpDetail() {
        return opDetail;
    }

    public void setOpDetail(List<RecordInfo> opDetail) {
        this.opDetail = opDetail;
    }
}
