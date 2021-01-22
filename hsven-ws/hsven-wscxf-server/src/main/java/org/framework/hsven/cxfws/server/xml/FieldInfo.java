package org.framework.hsven.cxfws.server.xml;

public class FieldInfo implements AbstractFieldInfo{
    private String fieldChName;
    private String fieldEnName;
    private String fieldContent;

    public static FieldInfo of(String fieldChName, String fieldEnName, String fieldContent) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.fieldChName = fieldChName;
        fieldInfo.fieldEnName = fieldEnName;
        fieldInfo.fieldContent = fieldContent;
        return fieldInfo;
    }

    public String getFieldChName() {
        return fieldChName;
    }

    public void setFieldChName(String fieldChName) {
        this.fieldChName = fieldChName;
    }

    public String getFieldEnName() {
        return fieldEnName;
    }

    public void setFieldEnName(String fieldEnName) {
        this.fieldEnName = fieldEnName;
    }

    public String getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(String fieldContent) {
        this.fieldContent = fieldContent;
    }
}
