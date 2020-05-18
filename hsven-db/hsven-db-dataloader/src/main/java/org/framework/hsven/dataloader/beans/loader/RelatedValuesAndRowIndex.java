package org.framework.hsven.dataloader.beans.loader;

import java.util.ArrayList;
import java.util.List;

public class RelatedValuesAndRowIndex {
    private final DefineRelatedValues defineRelatedValues;
    private List<Integer> relatedRowIndexList = new ArrayList<>();

    public RelatedValuesAndRowIndex(DefineRelatedValues defineRelatedValues) {
        this.defineRelatedValues = defineRelatedValues;
    }

    public void addRelatedRowIndex(int rowIndex) {
        relatedRowIndexList.add(rowIndex);
    }

    public DefineRelatedValues getDefineRelatedValues() {
        return defineRelatedValues;
    }

    public List<Integer> getRelatedRowIndexList() {
        return relatedRowIndexList;
    }

    public boolean hasRelatedRowIndex() {
        return relatedRowIndexList != null && relatedRowIndexList.size() > 0;
    }

    public void destory() {
        if (relatedRowIndexList != null) {
            relatedRowIndexList.clear();
        }
        relatedRowIndexList = null;
    }
}
