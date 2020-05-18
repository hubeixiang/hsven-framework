package org.framework.hsven.dataloader.beans.loader;

import java.util.ArrayList;
import java.util.List;

public class LazyRelatedFieldsAndRowIndex {
    private final DefineRelatedFields defineRelatedFields;
    private List<Integer> relatedRowIndexList = new ArrayList<>();

    public LazyRelatedFieldsAndRowIndex(DefineRelatedFields defineRelatedFields) {
        this.defineRelatedFields = defineRelatedFields;
    }

    public DefineRelatedFields getDefineRelatedFields() {
        return defineRelatedFields;
    }

    public List<Integer> getRelatedRowIndexList() {
        return relatedRowIndexList;
    }

    public void destory() {
        if (relatedRowIndexList != null) {
            relatedRowIndexList.clear();
        }
        relatedRowIndexList = null;
    }
}
