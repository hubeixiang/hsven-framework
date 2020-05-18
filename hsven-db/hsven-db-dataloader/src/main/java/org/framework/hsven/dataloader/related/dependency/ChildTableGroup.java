package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.loader.DefineRelatedFields;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;

import java.util.ArrayList;
import java.util.List;

public class ChildTableGroup {
    private final DefineRelatedFields defineRelatedFields;
    private List<SimpleChildTable> simpleChildTableList = new ArrayList<>();

    public ChildTableGroup(DefineRelatedFields defineRelatedFields) {
        this.defineRelatedFields = defineRelatedFields;
    }

    public String getRelatedFieldsKeys() {
        return defineRelatedFields.getRelatedFieldsKeys();
    }

    public DefineRelatedFields getDefineRelatedFields() {
        return defineRelatedFields;
    }

    public List<SimpleChildTable> getSimpleChildTableList() {
        return simpleChildTableList;
    }
}
