package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.loader.DefineRelatedFields;
import org.framework.hsven.dataloader.beans.loader.LazyRelatedFieldsAndRowIndexGroup;

public class SimpleLazyChildTableGroup extends ChildTableGroup {
    private final LazyRelatedFieldsAndRowIndexGroup lazyRelatedFieldsAndRowIndexGroup;

    public SimpleLazyChildTableGroup(DefineRelatedFields defineRelatedFields) {
        super(defineRelatedFields);
        this.lazyRelatedFieldsAndRowIndexGroup = new LazyRelatedFieldsAndRowIndexGroup(defineRelatedFields);
    }

    public LazyRelatedFieldsAndRowIndexGroup getLazyRelatedFieldsAndRowIndexGroup() {
        return lazyRelatedFieldsAndRowIndexGroup;
    }

    public void destory() {
        if (lazyRelatedFieldsAndRowIndexGroup != null) {
            lazyRelatedFieldsAndRowIndexGroup.destory();
        }
    }
}
