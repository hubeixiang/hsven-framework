package org.framework.hsven.annotation.enums;

import java.io.Serializable;

/**
 * 一个枚举类的具体枚举值的描述
 */
public class EnumAnnotationEntity implements Serializable {
    private static final long serialVersionUID = 42L;
    private String group;
    private Class<?> groupClass;
    private String groupName;
    private EnumEntity enumEntity;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Class<?> getGroupClass() {
        return groupClass;
    }

    public void setGroupClass(Class<?> groupClass) {
        this.groupClass = groupClass;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public EnumEntity getEnumEntity() {
        return enumEntity;
    }

    public void setEnumEntity(EnumEntity enumEntity) {
        this.enumEntity = enumEntity;
    }
}
