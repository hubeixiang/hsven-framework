package org.framework.hsven.dataloader.beans.related;

import org.framework.hsven.dataloader.beans.EnumDbDataType;
import org.framework.hsven.dataloader.util.StringFormatUtil;

public class TableField {
    private String tableAlias;
    private String fieldAlias;
    //定义的查询出来别名的类型
    private EnumDbDataType fieldAliasDisplayEnumDbDataType;
    private String tableFieldName;
    private String secondTableFieldName;

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = StringFormatUtil.formatNullOrTrim(tableAlias);
    }

    public String getFieldAlias() {
        return fieldAlias;
    }

    public void setFieldAlias(String fieldAlias) {
        this.fieldAlias = StringFormatUtil.formatNullOrTrim(fieldAlias);
    }

    public EnumDbDataType getFieldAliasDisplayEnumDbDataType() {
        return fieldAliasDisplayEnumDbDataType;
    }

    public void setFieldAliasDisplayEnumDbDataType(EnumDbDataType fieldAliasDisplayEnumDbDataType) {
        this.fieldAliasDisplayEnumDbDataType = fieldAliasDisplayEnumDbDataType;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = StringFormatUtil.formatNullOrTrim(tableFieldName);
    }

    public String getSecondTableFieldName() {
        return secondTableFieldName;
    }

    public void setSecondTableFieldName(String secondTableFieldName) {
        this.secondTableFieldName = StringFormatUtil.formatNullOrTrim(secondTableFieldName);
    }


    @Override
    public String toString() {
        return String.format("TableField [tableAlias=%s,fieldAlias=%s,fieldAliasDisplayEnumDbDataType=%s,tableFieldName=%s,secondTableFieldName=%s]", tableAlias, fieldAlias, fieldAliasDisplayEnumDbDataType, tableFieldName, secondTableFieldName);
    }
}
