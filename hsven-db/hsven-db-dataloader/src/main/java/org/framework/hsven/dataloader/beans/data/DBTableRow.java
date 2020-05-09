package org.framework.hsven.dataloader.beans.data;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.DBColumnMetaData;
import org.framework.hsven.dataloader.beans.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.DBColumnValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DBTableRow implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(DBTableRow.class);
    //查询出来的数据行号,first 0,1,2...
    private final int rowIndex;
    private final int columnSize;
    private final List<DBColumnValue> rowData;
    //当前行数据是否正常取得,null时也是正常取得,如果是异常取得,可以查看rowInfo的具体信息
    private RowInfo rowInfo;

    /**
     * @param rowIndex   查询出来的数据行号,first 0
     * @param columnSize 当前行的字段个数
     */
    public DBTableRow(int rowIndex, int columnSize) {
        this.rowIndex = rowIndex;
        this.columnSize = columnSize;
        Assert.isTrue(columnSize > 0, "DBTableRow columnSize must > 0");
        this.rowData = new ArrayList<>(columnSize);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int size() {
        return rowData.size();
    }

    /**
     * 判断当前行的数据是否正常加载完毕
     *
     * @return
     */
    public boolean isOkLoader() {
        return rowInfo == null || !rowInfo.isException();
    }

    public boolean addColumnValue(DBColumnMetaDataDefine dbColumnMetaDataDefine, DBColumnValue dbColumnValue) {
        if (dbColumnValue == null) {
            logger.error("addColumnValue,dbColumnValue is null.");
            return false;
        }
        String columnName = dbColumnValue.getColumnName();
        if (columnName == null || columnName.length() == 0) {
            logger.error("addColumnValue,columnName is null.DBColumnValue:" + dbColumnValue);
            return false;
        }

        if (dbColumnValue.getColumnDefine() == null) {
            logger.error("addColumnValue,columnName.DBColumnMetaData is null.DBColumnValue:" + dbColumnValue);
            return false;
        }
        DBColumnMetaData dbColumnMetaData = dbColumnMetaDataDefine.getDBColumnMetaData(dbColumnValue.getColumnDefine().getColumnNameLowerCase());
        DBColumnMetaData dbColumnMetaDataValue = dbColumnValue.getColumnDefine();
        if (!dbColumnMetaData.equals(dbColumnMetaDataValue)) {
            logger.error(String.format("addColumnValue DBColumnMetaData Must be consistent.dbColumnMetaDataValue=%s,dbColumnMetaData=%s", dbColumnMetaDataValue, dbColumnMetaData));
            return false;
        }
        if (rowData.size() >= columnSize) {
            logger.error(String.format("addColumnValue DBColumnMetaData size=%s & columnSize=%s Must be consistent.dbColumnMetaDataValue=%s,dbColumnMetaData=%s", dbColumnMetaDataDefine.size(), columnSize, dbColumnMetaDataValue, dbColumnMetaData));
            return false;
        }
        rowData.add(dbColumnMetaData.getColumnIndex(), dbColumnValue);
        return true;
    }

    public DBColumnValue getDataBaseColumnValue(DBColumnMetaDataDefine dbColumnMetaDataDefine, String columnName) {
        if (StringUtils.isEmpty(columnName)) {
            return null;
        }

        String lowerCase_columnName = columnName.toLowerCase();
        DBColumnMetaData dbColumnMetaData = dbColumnMetaDataDefine.getDBColumnMetaData(lowerCase_columnName);
        if (dbColumnMetaData == null) {
            return null;
        }
        int columnIndex = dbColumnMetaData.getColumnIndex();
        return getDataBaseColumnValue(dbColumnMetaDataDefine, columnIndex);
    }

    public DBColumnValue getDataBaseColumnValue(DBColumnMetaDataDefine dbColumnMetaDataDefine, int columnIndex) {
        if (columnIndex < 0 || columnIndex >= rowData.size()) {
            return null;
        }

        return rowData.size() > columnIndex ? rowData.get(columnIndex) : null;
    }

    public void destory() {
        rowData.clear();
    }


    public RowInfo getRowInfo() {
        return rowInfo;
    }

    public void setRowInfo(RowInfo rowInfo) {
        this.rowInfo = rowInfo;
    }

    @Override
    public String toString() {
        return String.format("DBTableRow[rowIndex=%s,columnSize=%s,rowData=%s,rowInfo=%s]", rowIndex, columnSize, rowData, rowInfo);
    }
}
