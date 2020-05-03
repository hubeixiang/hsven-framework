package org.framework.hsven.dataloader.loader.model;

import java.io.Serializable;

/**
 * 查询加载的结果描述信息
 */
public class QueryLoaderResultDesc extends DealFalgDesc implements Serializable {
    private static final long serialVersionUID = 4742546195532085551L;
    private String sqlName;
    private String sql;
    //加载的数据量
    private long loaderTotal = 0;
    private int fetchSize;
    //连接获取查询statement的耗时
    private long usingTimeMS_getJdbcStatement = -1;
    //执行查询耗时
    private long usingTimeMS_executeQuerySql = -1;
    //获取查询结果集描述耗时
    private long usingTimeMS_getMetaData = -1;
    //处理数据耗时
    private long usingTimeMS_processData = -1;

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public long getUsingTimeMS_getJdbcStatement() {
        return usingTimeMS_getJdbcStatement;
    }

    public void setUsingTimeMS_getJdbcStatement(long usingTimeMS_getJdbcStatement) {
        this.usingTimeMS_getJdbcStatement = usingTimeMS_getJdbcStatement;
    }

    public long getUsingTimeMS_executeQuerySql() {
        return usingTimeMS_executeQuerySql;
    }

    public void setUsingTimeMS_executeQuerySql(long usingTimeMS_executeQuerySql) {
        this.usingTimeMS_executeQuerySql = usingTimeMS_executeQuerySql;
    }

    public long getUsingTimeMS_getMetaData() {
        return usingTimeMS_getMetaData;
    }

    public void setUsingTimeMS_getMetaData(long usingTimeMS_getMetaData) {
        this.usingTimeMS_getMetaData = usingTimeMS_getMetaData;
    }

    public long getUsingTimeMS_processData() {
        return usingTimeMS_processData;
    }

    public void setUsingTimeMS_processData(long usingTimeMS_processData) {
        this.usingTimeMS_processData = usingTimeMS_processData;
    }

    public long getLoaderTotal() {
        return loaderTotal;
    }

    public void setLoaderTotal(long loaderTotal) {
        this.loaderTotal = loaderTotal;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n");
        builder.append("QueryLoaderResultDesc [").append(super.toString());
        builder.append(",sqlName=").append(sqlName);
        builder.append(", loaderTotal=").append(loaderTotal);
        builder.append(", fetchSize=").append(fetchSize).append("\r\n");
        builder.append(", stepUsingTimeMS(");
        builder.append("statement=").append(usingTimeMS_getJdbcStatement);
        builder.append(", query=").append(usingTimeMS_executeQuerySql);
        builder.append(", metaData=").append(usingTimeMS_getMetaData);
        builder.append(", processRow=").append(usingTimeMS_processData);
        builder.append(")").append("\r\n");
        builder.append(", sql=").append(sql);
        builder.append("]");
        return builder.toString();
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

}
