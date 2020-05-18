package org.framework.hsven.dataloader.beans.loader;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefineRelatedFields {
    private final List<DefineRelatedField> defineRelatedFieldList = new ArrayList<>();
    private final Map<String, DefineRelatedField> tableRelatedFieldDefineMap = new HashMap<>();
    private String relatedFieldsKeys = "";
    private boolean isComplete = false;

    public boolean hasTableRelatedFieldDefine() {
        return defineRelatedFieldList != null && defineRelatedFieldList.size() > 0;
    }

    public void addTableRelatedFieldDefine(DefineRelatedField defineRelatedField) {
        defineRelatedFieldList.add(defineRelatedField);
        if (StringUtils.isEmpty(relatedFieldsKeys)) {
            relatedFieldsKeys = String.format("(%s)", defineRelatedField.getRelatedField());
        } else {
            relatedFieldsKeys = String.format("%s-(%s)", relatedFieldsKeys, defineRelatedField.getRelatedField());
        }
        tableRelatedFieldDefineMap.put(defineRelatedField.getRelatedField(), defineRelatedField);
    }

    public List<DefineRelatedField> getDefineRelatedFieldList() {
        return defineRelatedFieldList;
    }

    public DefineRelatedField getDefineRelatedField(TableRelatedField tableRelatedField) {
        return tableRelatedFieldDefineMap.get(tableRelatedField.getMainTableField());
    }

    public String getRelatedFieldsKeys() {
        return relatedFieldsKeys;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isSingle() {
        return defineRelatedFieldList.size() == 1;
    }

    @Override
    public String toString() {
        return String.format("DefineRelatedFields [isComplete=%s,relatedFieldsKeys=%s,tableRelatedFieldDefineMap=%s]", isComplete, relatedFieldsKeys, tableRelatedFieldDefineMap);
    }
}
