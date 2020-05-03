package org.framework.hsven.dataloader.beans;

import org.framework.hsven.datasource.enums.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询加载数据后的 ResultSetMetaData 的解析结果
 */
public class DBColumnMetaDataDefine {
    private static Logger logger = LoggerFactory.getLogger(DBColumnMetaDataDefine.class);
    private final DataSourceType dataSourceType;
    private final List<DBColumnMetaData> metaDataList = new ArrayList<>();
    private final Map<String, DBColumnMetaData> metaDataMap = new HashMap<>();

    public DBColumnMetaDataDefine(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public int size() {
        return metaDataList == null ? 0 : metaDataList.size();
    }

    public void addDBColumnMetaData(DBColumnMetaData dbColumnMetaData) {
        if (dbColumnMetaData == null) {
            logger.error("addDBColumnMetaData,dbColumnMetaData is null.");
            return;
        }
        String columnName = dbColumnMetaData.getColumnName();
        if (columnName == null || columnName.length() == 0) {
            logger.error("addDBColumnMetaData,columnName is null.DBColumnMetaData:" + dbColumnMetaData);
            return;
        }

        String columnNameLowerCase = dbColumnMetaData.getColumnNameLowerCase();
        if (metaDataMap.containsKey(columnNameLowerCase)) {
            logger.error(String.format("addDBColumnMetaData,columnName=%s,columnNameLowerCase=%s already exists.DBColumnMetaData:%s", columnName, columnNameLowerCase, dbColumnMetaData));
            return;
        }
        metaDataMap.put(columnNameLowerCase, dbColumnMetaData);
        int columnIndex = dbColumnMetaData.getColumnIndex();
        metaDataList.add(columnIndex, dbColumnMetaData);
    }

    public DBColumnMetaData getDBColumnMetaData(int columnIndex) {
        return metaDataList.size() > columnIndex ? metaDataList.get(columnIndex) : null;
    }

    public DBColumnMetaData getDBColumnMetaData(String columnNameLowerCase) {
        return metaDataMap.get(columnNameLowerCase);
    }

    @Override
    public String toString() {
        return String.format("DBColumnMetaDataDefine[dataSourceType=%s,columns=%s]", dataSourceType, metaDataList);
    }
}
