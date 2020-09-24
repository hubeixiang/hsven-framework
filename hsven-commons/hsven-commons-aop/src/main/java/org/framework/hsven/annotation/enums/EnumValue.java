package org.framework.hsven.annotation.enums;

import java.io.Serializable;

public class EnumValue implements Serializable {
    private static final long serialVersionUID = 42L;
    /**
     * 枚举的值
     */
    private String value = "";
    /**
     * 枚举值对应的含义说明
     */
    private String name = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
