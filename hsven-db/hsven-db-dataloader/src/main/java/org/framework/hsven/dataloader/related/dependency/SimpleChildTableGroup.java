package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.loader.DefineRelatedFields;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndexGroup;

public class SimpleChildTableGroup extends ChildTableGroup {
    private final RelatedValuesAndRowIndexGroup relatedValuesAndRowIndexGroup;

    public SimpleChildTableGroup(DefineRelatedFields defineRelatedFields) {
        super(defineRelatedFields);
        this.relatedValuesAndRowIndexGroup = new RelatedValuesAndRowIndexGroup(defineRelatedFields);
    }

    public RelatedValuesAndRowIndexGroup getRelatedValuesAndRowIndexGroup() {
        return relatedValuesAndRowIndexGroup;
    }

    public void destory() {
        if (relatedValuesAndRowIndexGroup != null) {
            relatedValuesAndRowIndexGroup.destory();
        }
    }
}
