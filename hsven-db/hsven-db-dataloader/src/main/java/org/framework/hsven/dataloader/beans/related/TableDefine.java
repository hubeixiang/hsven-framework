package org.framework.hsven.dataloader.beans.related;

import org.framework.hsven.utils.ClassEqualsUtils;

import java.io.Serializable;

public class TableDefine implements Serializable {
    private static final long serialVersionUID = 4L;
    //查询超时设置
    private int queryTimeoutSeconds = 5 * 60;
    //查询的数据库名
    private String dbName;
    //查询的表名,也可以是查询sql
    private String tableName;
    //查询完毕后对外的的数据集别名
    private String tableAlias;
    //附加的查询条件
    private String where;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableAlias() {
        return tableAlias == null ? "" : tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = ClassEqualsUtils.hashcode(prime, result, dbName);
        result = ClassEqualsUtils.hashcode(prime, result, tableName);
        result = ClassEqualsUtils.hashcode(prime, result, tableAlias);
        result = ClassEqualsUtils.hashcode(prime, result, where);
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
        TableDefine other = (TableDefine) obj;
        if (!ClassEqualsUtils.isEquals(dbName, other.dbName)) {
            return false;
        }
        if (!ClassEqualsUtils.isEquals(tableName, other.tableName)) {
            return false;
        }
        if (!ClassEqualsUtils.isEquals(tableAlias, other.tableAlias)) {
            return false;
        }
        if (!ClassEqualsUtils.isEquals(where, other.where)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("TableDefine[dbName=%s,tableName=%s,tableAlias=%s,where=%s,queryTimeoutSeconds=%s",
                dbName, tableName, tableAlias, where, queryTimeoutSeconds);
    }
}
