package org.framework.hsven.dataloader.beans.related;


import org.framework.hsven.utils.ClassEqualsUtils;

/**
 * 关联关系中的主表设置
 */
public class MainTable {
    //主表表的查询定义
    private TableDefine tableDefine;
    //是否需要做唯一
    private boolean distinct;
    //需要查询出来的字段
    private TableFieldSet fieldSet;

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

    public TableFieldSet getFieldSet() {
        return fieldSet;
    }

    public void setFieldSet(TableFieldSet fieldSet) {
        this.fieldSet = fieldSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = ClassEqualsUtils.hashcode(prime, result, distinct);
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
        MainTable other = (MainTable) obj;
        if (!ClassEqualsUtils.isequals(this.distinct, other.distinct)) {
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
        builder.append("MainTable [tableDefine=").append(tableDefine);
        builder.append(", distinct=").append(distinct);
        builder.append(", fieldSet=").append(fieldSet);
        builder.append("]");
        return builder.toString();
    }
}
