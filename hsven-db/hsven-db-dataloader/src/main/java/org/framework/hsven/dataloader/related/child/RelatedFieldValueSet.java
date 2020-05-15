package org.framework.hsven.dataloader.related.child;

import org.framework.hsven.dataloader.beans.EnumDbDataType;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.dataloader.beans.related.TableRelatedFieldSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RelatedFieldValueSet {
    public static Logger logger = LoggerFactory.getLogger(RelatedFieldValueSet.class);

    private final TableRelatedField tableRelatedField;
    private Map<String, MainTableFieldValue> relatedFieldValue_rowIndexList_map = new HashMap<String, MainTableFieldValue>();

    public RelatedFieldValueSet(TableRelatedField tableRelatedField) {
        this.tableRelatedField = tableRelatedField;
    }

    public void addFieldValue(EnumDbDataType dataType, Object fieldValue, int rowIndex) {

        if (fieldValue == null) {
            return;
        }
        String str_fieldValue = fieldValue.toString();
        str_fieldValue = str_fieldValue.trim();
        if (str_fieldValue.length() == 0) {

            return;
        }
        MainTableFieldValue mainTableFieldValue = relatedFieldValue_rowIndexList_map.get(str_fieldValue);

        if (mainTableFieldValue == null) {
            mainTableFieldValue = new MainTableFieldValue();
            mainTableFieldValue.setDataType(dataType);
            mainTableFieldValue.setFieldValueStr(str_fieldValue);
            mainTableFieldValue.addRowIndex(rowIndex);
            relatedFieldValue_rowIndexList_map.put(str_fieldValue, mainTableFieldValue);
        } else {
            mainTableFieldValue.addRowIndex(rowIndex);
        }

    }

    public Map<String, MainTableFieldValue> readAndClear() {
        Map<String, MainTableFieldValue> return_values = null;
        try {
            //			Collection<MainTableFieldValue> values = relatedFieldValue_rowIndexList_map.values();
            //			return_values = getRelatedFieldValueMap(values);
            //			relatedFieldValue_rowIndexList_map.clear();
            return_values = this.relatedFieldValue_rowIndexList_map;
            this.relatedFieldValue_rowIndexList_map = new HashMap<String, MainTableFieldValue>();
        } catch (Exception e) {
            logger.error("RelatedFieldValueSet readAndClear Exception", e);
        }

        return return_values;
    }

    private Map<String, MainTableFieldValue> getRelatedFieldValueMap(Collection<MainTableFieldValue> createTask_relatedFieldValueSet) {
        Map<String, MainTableFieldValue> map = new HashMap<String, MainTableFieldValue>();
        if (createTask_relatedFieldValueSet == null) {
            return map;
        }

        for (MainTableFieldValue mainTableFieldValue : createTask_relatedFieldValueSet) {
            if (mainTableFieldValue == null) {
                continue;
            }

            map.put(mainTableFieldValue.getFieldValueStr(), mainTableFieldValue);
        }
        return map;
    }

    public TableRelatedField getTableRelatedField() {
        return tableRelatedField;
    }
}
