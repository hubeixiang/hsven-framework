package org.framework.hsven.dataloader.beans.related;

public class TableField {
    private String tableAlias;
    private String fieldAlias;
    private String tableFieldName;
    private String secondTableFieldName;

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getFieldAlias() {
        return fieldAlias;
    }

    public void setFieldAlias(String fieldAlias) {
        this.fieldAlias = fieldAlias;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

    public String getSecondTableFieldName() {
        return secondTableFieldName;
    }

    public void setSecondTableFieldName(String secondTableFieldName) {
        this.secondTableFieldName = secondTableFieldName;
    }

    @Override
    public String toString() {
        return String.format("TableField [tableAlias=%s,fieldAlias=%s,tableFieldName=%s,secondTableFieldName=%s]", tableAlias, fieldAlias, tableFieldName, secondTableFieldName);
    }
}
