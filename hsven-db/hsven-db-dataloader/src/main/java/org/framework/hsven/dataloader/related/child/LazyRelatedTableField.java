package org.framework.hsven.dataloader.related.child;

import org.framework.hsven.dataloader.beans.EnumDbDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LazyRelatedTableField {
    public static Logger logger = LoggerFactory.getLogger(LazyRelatedTableField.class);
    //要查找的字段名称
    private String fieldName;
    //要查找的字段类型
    private EnumDbDataType dataType;
    //要查找的行号
    private List<Integer> relatedTableRowIndexList_tmp = new ArrayList<Integer>();

    public void addRowIndex(Integer rowIndex) {
        if (rowIndex == null) {
            return;
        }
        relatedTableRowIndexList_tmp.add(rowIndex);
    }

    public LazyRelatedTableField readAndClear() {
        LazyRelatedTableField lazyRelatedTableField = new LazyRelatedTableField();
        lazyRelatedTableField.setDataType(this.getDataType());
        lazyRelatedTableField.setFieldName(this.getFieldName());
        try {
            lazyRelatedTableField.relatedTableRowIndexList_tmp = this.relatedTableRowIndexList_tmp;
            this.relatedTableRowIndexList_tmp = new ArrayList<Integer>();
        } catch (Exception e) {
            logger.error("LazyRelatedTableField readAndClear Exception", e);
        }
        return lazyRelatedTableField;
    }

    public void destory() {
        if (relatedTableRowIndexList_tmp != null) {
            relatedTableRowIndexList_tmp.clear();
        }
        relatedTableRowIndexList_tmp = null;
    }

    public EnumDbDataType getDataType() {
        return dataType;
    }

    public void setDataType(EnumDbDataType dataType) {
        this.dataType = dataType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldValueStr) {
        this.fieldName = fieldValueStr;
    }

    public List<Integer> getRelatedTableRowIndexList_tmp() {
        return relatedTableRowIndexList_tmp;
    }

    @Override
    public String toString() {
        return "LazyRelatedTableField [dataType=" + dataType + ", fieldName=" + fieldName + ", relatedTableRowIndexList="
                + relatedTableRowIndexList_tmp + "]";
    }

}
