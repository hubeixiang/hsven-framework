package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.dependency.EnumTableType;

public class CallableRelatedTaskDependencyUtil {

    public Object instanceofTableType(CallableRelatedTaskDependency callableRelatedTaskDependency) {
        EnumTableType enumTableType = callableRelatedTaskDependency.getEnumTableType();
        switch (enumTableType) {
            case Main:
                break;
            case Child:
                break;
            case Lazy_subtable:
                break;
            default:
                throw new RuntimeException("Error EnumTableType = " + enumTableType);
        }
        return null;
    }
}
