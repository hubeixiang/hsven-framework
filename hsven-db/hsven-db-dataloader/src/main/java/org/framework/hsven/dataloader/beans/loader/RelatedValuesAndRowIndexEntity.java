package org.framework.hsven.dataloader.beans.loader;

import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RelatedValuesAndRowIndexEntity {
    //Map<defineRelatedValues.getRelatedFieldsKeyValue(),RelatedValuesAndRowIndex>
    private Map<String, RelatedValuesAndRowIndex> tableDefineRelatedValues_RelatedValuesAndRowIndex = new HashMap<>();

    public Iterator<Map.Entry<String, RelatedValuesAndRowIndex>> entryRelatedValuesAndRowIndex() {
        return tableDefineRelatedValues_RelatedValuesAndRowIndex == null ? null : tableDefineRelatedValues_RelatedValuesAndRowIndex.entrySet().iterator();
    }


    public boolean addDBTableRowInfo(DefineRelatedFields defineRelatedFields, DBTableRowInfo dbTableRowInfo) {
        DefineRelatedValues defineRelatedValues = DefineRelatedValuesUtil.createTableDefineRelatedValues(defineRelatedFields, dbTableRowInfo);
        return addDefineRelatedValues(defineRelatedFields, defineRelatedValues, dbTableRowInfo.getRowIndex());
    }

    public boolean addDefineRelatedValues(DefineRelatedFields defineRelatedFields, DefineRelatedValues defineRelatedValues, Integer rowIndex) {
        if (defineRelatedValues == null || defineRelatedValues.isValuesIsNull()) {
            return false;
        }

        RelatedValuesAndRowIndex relatedValuesAndRowIndex = null;
        if (tableDefineRelatedValues_RelatedValuesAndRowIndex.containsKey(defineRelatedValues.getRelatedFieldsKeyValue())) {
            relatedValuesAndRowIndex = tableDefineRelatedValues_RelatedValuesAndRowIndex.get(defineRelatedValues.getRelatedFieldsKeyValue());
        } else {
            relatedValuesAndRowIndex = new RelatedValuesAndRowIndex(defineRelatedValues);
            tableDefineRelatedValues_RelatedValuesAndRowIndex.put(defineRelatedValues.getRelatedFieldsKeyValue(), relatedValuesAndRowIndex);
        }

        relatedValuesAndRowIndex.addRelatedRowIndex(rowIndex);
        return true;
    }

    public boolean hasRelatedValues() {
        return tableDefineRelatedValues_RelatedValuesAndRowIndex != null && tableDefineRelatedValues_RelatedValuesAndRowIndex.size() > 0;
    }

    public RelatedValuesAndRowIndex getRelatedValuesAndRowIndexByValuesKey(String relatedFieldsKeyValue) {
        return tableDefineRelatedValues_RelatedValuesAndRowIndex.get(relatedFieldsKeyValue);
    }

    public void destory() {
        if (tableDefineRelatedValues_RelatedValuesAndRowIndex != null) {
            for (Map.Entry<String, RelatedValuesAndRowIndex> entry : tableDefineRelatedValues_RelatedValuesAndRowIndex.entrySet()) {
                if (entry.getValue() != null) {
                    entry.getValue().destory();
                }
            }
            tableDefineRelatedValues_RelatedValuesAndRowIndex.clear();
        }
        tableDefineRelatedValues_RelatedValuesAndRowIndex = null;
    }
}
