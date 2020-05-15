package org.framework.hsven.dataloader.beans.loader;

import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;

import java.util.HashMap;
import java.util.Map;

public class RelatedValuesAndRowIndexEntity {
    //Map<defineRelatedValues.getRelatedFieldsKeyValue(),RelatedValuesAndRowIndex>
    private Map<String, RelatedValuesAndRowIndex> tableDefineRelatedValues_RelatedValuesAndRowIndex = new HashMap<>();

    public boolean addDBTableRowInfo(DefineRelatedFields defineRelatedFields, DBTableRowInfo dbTableRowInfo) {
        DefineRelatedValues defineRelatedValues = DefineRelatedValuesUtil.createTableDefineRelatedValues(defineRelatedFields, dbTableRowInfo);
        if (defineRelatedValues == null) {
            return false;
        }

        RelatedValuesAndRowIndex relatedValuesAndRowIndex = null;
        if (tableDefineRelatedValues_RelatedValuesAndRowIndex.containsKey(defineRelatedValues.getRelatedFieldsKeyValue())) {
            relatedValuesAndRowIndex = tableDefineRelatedValues_RelatedValuesAndRowIndex.get(defineRelatedValues);
        } else {
            relatedValuesAndRowIndex = new RelatedValuesAndRowIndex(defineRelatedValues);
            tableDefineRelatedValues_RelatedValuesAndRowIndex.put(defineRelatedValues.getRelatedFieldsKeyValue(), relatedValuesAndRowIndex);
        }

        relatedValuesAndRowIndex.addRelatedRowIndex(dbTableRowInfo.getRowIndex());
        return true;
    }

    public RelatedValuesAndRowIndex getRelatedValuesAndRowIndexByValuesKey(String relatedFieldsKeyValue) {
        return tableDefineRelatedValues_RelatedValuesAndRowIndex.get(relatedFieldsKeyValue);
    }

    public void destory() {
        if (tableDefineRelatedValues_RelatedValuesAndRowIndex != null) {
            tableDefineRelatedValues_RelatedValuesAndRowIndex.clear();
        }
        tableDefineRelatedValues_RelatedValuesAndRowIndex = null;
    }
}
