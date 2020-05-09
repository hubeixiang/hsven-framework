package org.framework.hsven.dataloader.beans.data;

import org.framework.hsven.dataloader.beans.DBColumnMetaDataDefine;

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

    @Override
    public String toString() {
        return String.format("DBTableRowInfo [dbColumnMetaDataDefine.size=%s,dbTableRow=%s]", dbColumnMetaDataDefine.size(), dbTableRow);
    }
}
