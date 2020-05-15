package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.dependency.EnumTableType;

public class SimpleChildTableLazyCallableTaskDependency extends AbstractChildCallableRelatedTaskDependency {
    public SimpleChildTableLazyCallableTaskDependency() {
        super(EnumTableType.Lazy_subtable);
    }
}
