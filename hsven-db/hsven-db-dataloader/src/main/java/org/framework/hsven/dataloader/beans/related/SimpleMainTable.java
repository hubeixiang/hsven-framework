package org.framework.hsven.dataloader.beans.related;


import org.framework.hsven.utils.ClassEqualsUtils;

/**
 * 关联关系中的主表设置
 */
public class SimpleMainTable {
    //主表表的查询定义
    private TableDefine tableDefine;
    //是否需要做唯一
    private boolean distinct;
    //需要查询出来的字段
    private TableFieldSet tableFieldSet;

    public String getIdentify() {
        return toString();
    }

    public boolean hasTableField() {
        return tableFieldSet != null && tableFieldSet.hasTableField();
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

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public TableFieldSet getTableFieldSet() {
        return tableFieldSet;
    }

    public void setTableFieldSet(TableFieldSet fieldSet) {
        this.tableFieldSet = fieldSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = ClassEqualsUtils.hashcode(prime, result, distinct);
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
        SimpleMainTable other = (SimpleMainTable) obj;
        if (!ClassEqualsUtils.isequals(this.distinct, other.distinct)) {
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
        builder.append("SimpleMainTable [tableDefine=").append(tableDefine);
        builder.append(", distinct=").append(distinct);
        builder.append(", fieldSet=").append(tableFieldSet);
        builder.append("]");
        return builder.toString();
    }
}
