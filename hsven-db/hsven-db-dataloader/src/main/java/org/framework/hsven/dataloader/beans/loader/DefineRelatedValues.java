package org.framework.hsven.dataloader.beans.loader;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DefineRelatedValues {
    private final List<DefineRelatedValue> defineRelatedValueList = new ArrayList<>();
    private String relatedFieldsKeyValue = "";
    //标志是否所有的值都为null,注意与""区分开
    private boolean valuesIsNull = true;

    public void addTableRelatedFieldDefine(DefineRelatedValue defineRelatedValue) {
        if (defineRelatedValue.getValue() != null) {
            valuesIsNull = false;
        }
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

    /**
     * 所有的关联健的值都为null,注意不是""
     *
     * @return
     */
    public boolean isValuesIsNull() {
        return valuesIsNull;
    }

    /**
     * 拼接sql的字段是否是单个
     *
     * @return
     */
    public boolean isSingle() {
        return defineRelatedValueList.size() == 1;
    }

    public String toString() {
        return String.format("DefineRelatedValues [relatedFieldsKeyValue=%s,valuesIsNull=%s,size=%s]", relatedFieldsKeyValue, valuesIsNull, defineRelatedValueList.size());
    }
}
