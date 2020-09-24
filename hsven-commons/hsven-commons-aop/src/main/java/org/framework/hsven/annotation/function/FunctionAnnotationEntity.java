package org.framework.hsven.annotation.function;

import java.io.Serializable;

public class FunctionAnnotationEntity implements Serializable {
    private static final long serialVersionUID = 42L;
    private String group;
    private Class<?> groupClass;
    private String groupName;
    private FunctionValue functionValue;

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

    public FunctionValue getFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(FunctionValue functionValue) {
        this.functionValue = functionValue;
    }
}
