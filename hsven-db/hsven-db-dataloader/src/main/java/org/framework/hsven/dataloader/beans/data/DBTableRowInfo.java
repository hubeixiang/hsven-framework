package org.framework.hsven.dataloader.beans.data;

import org.framework.hsven.dataloader.beans.db.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.db.DBColumnValue;

public class DBTableRowInfo {
    private DBColumnMetaDataDefine dbColumnMetaDataDefine;
    private DBTableRow dbTableRow;

    public DBTableRowInfo(DBColumnMetaDataDefine dbColumnMetaDataDefine, DBTableRow dbTableRow) {
        this.dbColumnMetaDataDefine = dbColumnMetaDataDefine;
        this.dbTableRow = dbTableRow;
    }

    public DBColumnMetaDataDefine getDbColumnMetaDataDefine() {
        return dbColumnMetaDataDefine;
    }

    public DBTableRow getDbTableRow() {
        return dbTableRow;
    }

    public int getRowIndex() {
        return dbTableRow != null ? dbTableRow.getRowIndex() : -1;
    }

    public int size() {
        return dbTableRow != null ? dbTableRow.size() : 0;
    }

    public boolean isOkLoader() {
        return dbTableRow != null && dbTableRow.isOkLoader();
    }

    public DBColumnValue getDataBaseColumnValue(String columnName) {
        if (dbTableRow == null) {
            return null;
        } else {
            return dbTableRow.getDataBaseColumnValue(dbColumnMetaDataDefine, columnName);
        }
    }

    public DBColumnValue getDataBaseColumnValue(int columnIndex) {
        if (dbTableRow == null) {
            return null;
        } else {
            return dbTableRow.getDataBaseColumnValue(dbColumnMetaDataDefine, columnIndex);
        }
    }

    @Override
    public String toString() {
        return String.format("DBTableRowInfo [dbColumnMetaDataDefine.size=%s,dbTableRow=%s]", dbColumnMetaDataDefine.size(), dbTableRow);
    }
}
