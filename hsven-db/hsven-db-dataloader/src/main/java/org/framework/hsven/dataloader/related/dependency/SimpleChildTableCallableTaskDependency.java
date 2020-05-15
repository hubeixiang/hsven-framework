package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.dependency.EnumTableType;

public class SimpleChildTableCallableTaskDependency extends AbstractChildCallableRelatedTaskDependency {

    public SimpleChildTableCallableTaskDependency() {
        super(EnumTableType.Child);
    }
}
