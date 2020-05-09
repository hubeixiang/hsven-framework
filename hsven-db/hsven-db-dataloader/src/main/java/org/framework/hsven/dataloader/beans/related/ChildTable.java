package org.framework.hsven.dataloader.beans.related;

import org.framework.hsven.utils.ClassEqualsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 查询关联的字表,与主表之间进行左外关联
 */
public class ChildTable {
    public static Logger logger = LoggerFactory.getLogger(ChildTable.class);
    // <childTable database="NMOSDB" tableAlias="vendor_name" isCache="true">
    // <tableName>vendor_name</tableName>
    // <where></where>
    // <relatedFieldSet>
    // <relatedField childTableField="vendor_id" mainTableField="vendor_id"></relatedField>
    // </relatedFieldSet>
    // </childTable>
    //设置字表是否进行全部数据查询缓存,关联到主表时直接从缓存中获取。默认是不用进行缓存,每次关联再次查询获取
    private boolean isConfigCache = false;
    private TableDefine tableDefine;
    private TableFieldSet fieldSet;
    private TableRelatedFieldSet tableRelatedFieldSet;


    public String getTableAlias() {
        return tableDefine == null ? "" : tableDefine.getTableAlias();
    }

    public TableDefine getTableDefine() {
        return tableDefine;
    }

    public void setTableDefine(TableDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public TableFieldSet getFieldSet() {
        return fieldSet;
    }

    public void setFieldSet(TableFieldSet fieldSet) {
        this.fieldSet = fieldSet;
    }

    public TableRelatedFieldSet getTableRelatedFieldSet() {
        return tableRelatedFieldSet;
    }

    public void setTableRelatedFieldSet(TableRelatedFieldSet tableRelatedFieldSet) {
        this.tableRelatedFieldSet = tableRelatedFieldSet;
    }

    public boolean isConfigCache() {
        return isConfigCache;
    }

    public void setConfigCache(boolean configCache) {
        isConfigCache = configCache;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = ClassEqualsUtils.hashcode(prime, result, tableRelatedFieldSet);
        result = ClassEqualsUtils.hashcode(prime, result, tableDefine);
        result = ClassEqualsUtils.hashcode(prime, result, fieldSet);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChildTable other = (ChildTable) obj;
        if (!ClassEqualsUtils.isEquals(this.tableRelatedFieldSet, other.tableRelatedFieldSet)) {
            return false;
        }
        if (!ClassEqualsUtils.isEquals(this.tableDefine, other.tableDefine)) {
            return false;
        }
        if (!ClassEqualsUtils.isEquals(this.fieldSet, other.fieldSet)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ChildTable [isConfigCache=").append(isConfigCache);
        builder.append(", tableDefine=").append(tableDefine);
        builder.append(", fieldSet=").append(fieldSet);
        builder.append(", tableRelatedFieldSet=").append(tableRelatedFieldSet);
        builder.append("]");
        return builder.toString();
    }

}
