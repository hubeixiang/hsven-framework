package org.framework.hsven.dataloader.beans.related;

import org.framework.hsven.dataloader.beans.loader.DefineRelatedFields;
import org.framework.hsven.utils.ClassEqualsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 查询关联的字表,与主表之间进行左外关联
 */
public class SimpleChildTable {
    public static Logger logger = LoggerFactory.getLogger(SimpleChildTable.class);
    //利用定义的关联字段集合(TableRelatedFieldSet)生成的关联实体,不用外部填写
    //在验证此配置对象是否正确时,填写对应的值
    private final DefineRelatedFields defineRelatedFields = new DefineRelatedFields();
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
    private TableFieldSet tableFieldSet;
    private TableRelatedFieldSet tableRelatedFieldSet;

    public String getIdentify() {
        return toString();
    }

    public DefineRelatedFields getDefineRelatedFields() {
        return defineRelatedFields;
    }

    public boolean hasTableField() {
        return tableFieldSet != null && tableFieldSet.hasTableField();
    }

    public boolean hasTableRelatedField() {
        return tableRelatedFieldSet != null && tableRelatedFieldSet.hasTableRelatedField();
    }

    public String getTableAlias() {
        return tableDefine == null ? "" : tableDefine.getTableAlias();
    }

    public TableDefine getTableDefine() {
        return tableDefine;
    }

    public void setTableDefine(TableDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public TableFieldSet getTableFieldSet() {
        return tableFieldSet;
    }

    public void setTableFieldSet(TableFieldSet fieldSet) {
        this.tableFieldSet = fieldSet;
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
        result = ClassEqualsUtils.hashcode(prime, result, tableFieldSet);
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
        SimpleChildTable other = (SimpleChildTable) obj;
        if (!ClassEqualsUtils.isEquals(this.tableRelatedFieldSet, other.tableRelatedFieldSet)) {
            return false;
        }
        if (!ClassEqualsUtils.isEquals(this.tableDefine, other.tableDefine)) {
            return false;
        }
        if (!ClassEqualsUtils.isEquals(this.tableFieldSet, other.tableFieldSet)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SimpleChildTable [isConfigCache=").append(isConfigCache);
        builder.append(", tableDefine=").append(tableDefine);
        builder.append(", fieldSet=").append(tableFieldSet);
        builder.append(", tableRelatedFieldSet=").append(tableRelatedFieldSet);
        builder.append("]");
        return builder.toString();
    }

}
