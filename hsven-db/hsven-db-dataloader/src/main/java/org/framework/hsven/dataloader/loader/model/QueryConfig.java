package org.framework.hsven.dataloader.loader.model;

public class QueryConfig {
    private String dbName;
    private String sqlName;
    private String sql;
    // <=0 表明全部获取,>0表明获取前topSize条数据
    private int topSize = -1;
    //分批次获取的数据量
    private int fetchSize;
    //查询超时时间
    private int queryTimeoutSeconds;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public int getTopSize() {
        return topSize;
    }

    public void setTopSize(int topSize) {
        this.topSize = topSize;
    }

    public boolean isTopQuery() {
        return topSize > 0;
    }

    @Override
    public String toString() {
        return "QueryConfig [dbName=" + dbName + ",sqlName=" + sqlName + ",topSize=" + topSize + ", fetchSize=" + fetchSize + ", queryTimeoutSeconds=" + queryTimeoutSeconds + ", sql=" + sql + "]";
    }

    public int getQueryTimeoutSeconds() {
        return queryTimeoutSeconds;
    }

    public void setQueryTimeoutSeconds(int queryTimeoutSeconds) {
        this.queryTimeoutSeconds = queryTimeoutSeconds;
    }

}
