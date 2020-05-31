package org.framework.hsven.dataloader.beans.data;


import org.framework.hsven.dataloader.beans.db.DBColumnMetaData;
import org.framework.hsven.dataloader.beans.db.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.db.DBColumnValue;

import java.util.HashMap;
import java.util.Map;

public class DBTableRowInfoUtil {
    public static Map<String, String> convert2Map(DBTableRowInfo dbTableRowInfo, boolean keyLowerCase) {
        Map<String, String> result = new HashMap<>();
        if (dbTableRowInfo == null) {
            return result;
        }
        DBColumnMetaDataDefine dbColumnMetaDataDefine = dbTableRowInfo.getDbColumnMetaDataDefine();
        if (dbColumnMetaDataDefine == null || dbColumnMetaDataDefine.size() <= 0) {
            return result;
        }
        int size = dbColumnMetaDataDefine.size();
        for (int i = 0; i < size; i++) {
            DBColumnMetaData dbColumnMetaData = dbColumnMetaDataDefine.getDBColumnMetaData(i);
            DBColumnValue dbColumnValue = dbTableRowInfo.getDataBaseColumnValue(dbColumnMetaData.getColumnIndex());
            String key = keyLowerCase ? dbColumnMetaData.getColumnNameLowerCase() : dbColumnMetaData.getColumnName();
            if (dbColumnValue == null || dbColumnValue.getColumnValue() == null) {
                result.put(key, "");
            } else {
                result.put(key, dbColumnValue.getColumnValueStr());
            }
        }
        return result;
    }

    public static Map<String, String> convert2Mapfilter(DBTableRowInfo dbTableRowInfo, boolean keyLowerCase) {
        Map<String, String> result = new HashMap<>();
        if (dbTableRowInfo == null) {
            return result;
        }
        DBColumnMetaDataDefine dbColumnMetaDataDefine = dbTableRowInfo.getDbColumnMetaDataDefine();
        if (dbColumnMetaDataDefine == null || dbColumnMetaDataDefine.size() <= 0) {
            return result;
        }
        int size = dbColumnMetaDataDefine.size();
        for (int i = 0; i < size; i++) {
            DBColumnMetaData dbColumnMetaData = dbColumnMetaDataDefine.getDBColumnMetaData(i);
            DBColumnValue dbColumnValue = dbTableRowInfo.getDataBaseColumnValue(dbColumnMetaData.getColumnIndex());
            String key = keyLowerCase ? dbColumnMetaData.getColumnNameLowerCase() : dbColumnMetaData.getColumnName();
            if (dbColumnValue == null || dbColumnValue.getColumnValue() == null) {
            } else {
                result.put(key, dbColumnValue.getColumnValueStr());
            }
        }
        return result;
    }
}
