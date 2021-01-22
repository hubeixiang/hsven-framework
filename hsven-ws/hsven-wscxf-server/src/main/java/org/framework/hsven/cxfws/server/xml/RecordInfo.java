package org.framework.hsven.cxfws.server.xml;

import java.util.ArrayList;
import java.util.List;

public class RecordInfo {
    private List<FieldInfo> record = new ArrayList<>();

    public static RecordInfo of() {
        return new RecordInfo();
    }

    public List<FieldInfo> getRecord() {
        return record;
    }

    public void setRecord(List<FieldInfo> record) {
        this.record = record;
    }
}
