package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.loader.DefineRelatedFields;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndexGroup;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;

import java.util.ArrayList;
import java.util.List;

public class SimpleChildTableGroup {
    private final DefineRelatedFields defineRelatedFields;
    private final RelatedValuesAndRowIndexGroup relatedValuesAndRowIndexGroup;
    private List<SimpleChildTable> simpleChildTableList = new ArrayList<>();

    public SimpleChildTableGroup(DefineRelatedFields defineRelatedFields) {
        this.defineRelatedFields = defineRelatedFields;
        this.relatedValuesAndRowIndexGroup = new RelatedValuesAndRowIndexGroup(defineRelatedFields);
    }

    public String getRelatedFieldsKeys() {
        return defineRelatedFields.getRelatedFieldsKeys();
    }

    public DefineRelatedFields getDefineRelatedFields() {
        return defineRelatedFields;
    }

    public RelatedValuesAndRowIndexGroup getRelatedValuesAndRowIndexGroup() {
        return relatedValuesAndRowIndexGroup;
    }

    public List<SimpleChildTable> getSimpleChildTableList() {
        return simpleChildTableList;
    }

    public void destory() {
        if (relatedValuesAndRowIndexGroup != null) {
            relatedValuesAndRowIndexGroup.destory();
        }
    }
}
