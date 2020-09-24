package org.framework.hsven.annotation.function;

import java.io.Serializable;

public class FunctionValue implements Serializable {
    private static final long serialVersionUID = 42L;
    /**
     * 枚举的值
     */
    private String value = "";
    /**
     * 枚举值对应的含义说明
     */
    private String name = "";

    /**
     * bean对应的参数配置结构描述
     */
    private String format = "";

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
