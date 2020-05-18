package org.framework.hsven.dataloader.beans.db;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBColumnValue {

    private final DBColumnMetaData columnDefine;
    private final Object columnValue;

    public DBColumnValue(DBColumnMetaData columnDefine, Object columnValue) {
        this.columnDefine = columnDefine;
        this.columnValue = columnValue;
    }

    public String getColumnName() {
        if (columnDefine == null) {
            return null;
        }
        return columnDefine.getColumnName();
    }

    public DBColumnMetaData getColumnDefine() {
        return columnDefine;
    }

    public Object getColumnValue() {
        return columnValue;
    }

    public String getColumnValueStr() {
        if (columnValue == null) {
            return null;
        }

        if (columnValue instanceof Date) {
            Date date_columnValue = (Date) columnValue;
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String str = dataFormat.format(date_columnValue);
            return str;
        }

        return columnValue.toString();
    }

    @Override
    public String toString() {
        String columnName = null;
        if (columnDefine != null) {
            columnName = columnDefine.getColumnName();
        }
        return "DBColumnValue [columnName=" + columnName + ", columnValue=" + columnValue + "]";
    }

}
