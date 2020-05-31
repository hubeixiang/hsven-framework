package org.framework.hsven.dataloader.beans.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DBColumnMetaDataDefineUtil {
    public static Map<String, DBColumnMetaData> conver2DBColumnMetaDataMap(DBColumnMetaDataDefine dbColumnMetaDataDefine) {
        Map<String, DBColumnMetaData> result = new HashMap<>();
        if (dbColumnMetaDataDefine == null) {
            return result;
        }
        Iterator<DBColumnMetaData> it = dbColumnMetaDataDefine.iterator();
        while (it != null && it.hasNext()) {
            DBColumnMetaData dbColumnMetaData = it.next();
            result.put(dbColumnMetaData.getColumnNameLowerCase(), dbColumnMetaData);
        }
        return result;
    }

    public static List<DBColumnMetaData> conver2DBColumnMetaDataList(DBColumnMetaDataDefine dbColumnMetaDataDefine) {
        List<DBColumnMetaData> result = new ArrayList<>();
        if (dbColumnMetaDataDefine == null) {
            return result;
        }
        Iterator<DBColumnMetaData> it = dbColumnMetaDataDefine.iterator();
        while (it != null && it.hasNext()) {
            DBColumnMetaData dbColumnMetaData = it.next();
            result.add(dbColumnMetaData);
        }
        return result;
    }
}
