package org.framework.hsven.dataloader.beans.loader;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DefineRelatedValues {
    private final List<DefineRelatedValue> defineRelatedValueList = new ArrayList<>();
    private String relatedFieldsKeyValue = "";

    public void addTableRelatedFieldDefine(DefineRelatedValue defineRelatedValue) {
        defineRelatedValueList.add(defineRelatedValue);
        if (StringUtils.isEmpty(relatedFieldsKeyValue)) {
            relatedFieldsKeyValue = String.format("(%s)", defineRelatedValue.getValueForString());
        } else {
            relatedFieldsKeyValue = String.format("%s-(%s)", relatedFieldsKeyValue, defineRelatedValue.getValueForString());
        }
    }

    public List<DefineRelatedValue> getDefineRelatedValueList() {
        return defineRelatedValueList;
    }

    public String getRelatedFieldsKeyValue() {
        return relatedFieldsKeyValue;
    }

    public String toString() {
        return String.format("DefineRelatedValues [relatedFieldsKeyValue=%s,size=%s]", relatedFieldsKeyValue, defineRelatedValueList.size());
    }
}
