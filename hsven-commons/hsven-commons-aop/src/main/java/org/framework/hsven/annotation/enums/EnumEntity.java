package org.framework.hsven.annotation.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个枚举类的具体枚举值的描述
 */
public class EnumEntity implements Serializable {
    private static final long serialVersionUID = 42L;
    private List<EnumValue> data = new ArrayList<>();

    public List<EnumValue> getData() {
        return data;
    }

    public void setData(List<EnumValue> data) {
        this.data = data;
    }
}
