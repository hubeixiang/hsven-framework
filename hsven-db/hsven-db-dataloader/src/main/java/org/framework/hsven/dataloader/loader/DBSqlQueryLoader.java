package org.framework.hsven.dataloader.loader;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.framework.hsven.dataloader.beans.DBColumnMetaData;
import org.framework.hsven.dataloader.beans.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.DBColumnValue;
import org.framework.hsven.dataloader.beans.EnumDbDataType;
import org.framework.hsven.dataloader.beans.data.DBTableRow;
import org.framework.hsven.dataloader.beans.data.RowInfo;
import org.framework.hsven.dataloader.exception.LoaderException;
import org.framework.hsven.dataloader.listener.IDBSqlQueryLoaderListener;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.util.DBColumnMetaDataUtil;
import org.framework.hsven.datasource.InternalDBContextHelper;
import org.framework.hsven.datasource.SpringDataSourceContextUtil;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.framework.hsven.datasource.util.DataSourceNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSqlQueryLoader extends Thread {
    private static Logger logger = LoggerFactory.getLogger(DBSqlQueryLoader.class);
    private DataSourceConfig dataSourceConfig;
    private QueryConfig queryConfig;
    private IDBSqlQueryLoaderListener callbackListenerHandler;
    private DBColumnMetaDataDefine dbColumnMetaDataDefine;

    public void init(IDBSqlQueryLoaderListener callbackListenerHandler, QueryConfig queryConfig) {
        this.callbackListenerHandler = callbackListenerHandler;
        this.queryConfig = queryConfig;
        Assert.isTrue(this.callbackListenerHandler != null, "Loader Database data LoaderLister can't null");
        Assert.isTrue(this.queryConfig != null, this.callbackListenerHandler.listenerIdentification() + " Loader Database data QueryConfig can't null");
        Assert.isTrue(!StringUtils.isEmpty(this.queryConfig.getDbName()), this.callbackListenerHandler.listenerIdentification() + " Loader Database data QueryConfig'dbName can't empty ");
        Assert.isTrue(!StringUtils.isEmpty(this.queryConfig.getSql()), this.callbackListenerHandler.listenerIdentification() + " Loader Database data QueryConfig'sql can't empty ");
        DataSourceConfig dataSourceConfigTmp = InternalDBContextHelper.getInstance().getDataSourceConfig(this.queryConfig.getDbName());
        Assert.isTrue(dataSourceConfigTmp != null, String.format("%s Loader Database data dbName=%s can't init DataSource", this.callbackListenerHandler.listenerIdentification(), this.queryConfig.getDbName()));
        this.dataSourceConfig = dataSourceConfigTmp;
        this.callbackListenerHandler.init(this.dataSourceConfig, this.queryConfig);
    }

    public void run() {
        load();
    }

    public QueryLoaderResultDesc load() {
        QueryLoaderResultDesc queryLoaderResultDesc = new QueryLoaderResultDesc();
        String dbName = this.queryConfig.getDbName();
        String sql = this.queryConfig.getSql();
        try {
            long beginTimeMS = System.currentTimeMillis();
            queryLoaderResultDesc.setBeginTime(DateFormatUtils.format(beginTimeMS, "yyyy-MM-dd HH:mm:ss.SS"));
            queryLoaderResultDesc.setSqlName(this.queryConfig.getSqlName());
            int fetchSize = queryConfig.getFetchSize();
            queryLoaderResultDesc.setFetchSize(fetchSize);
            queryLoaderResultDesc.setSql(sql);
            queryLoaderResultDesc = executeQuery(queryLoaderResultDesc, beginTimeMS);
        } catch (Exception e) {
            queryLoaderResultDesc.setDealFlag(false);
            queryLoaderResultDesc.setDealException(e);
            logger.error(String.format("%s DBSqlQueryLoader load(%s) Exception:%s ", this.callbackListenerHandler.listenerIdentification(), bufferSqlInfo(), e.getMessage()), e);
        } catch (Throwable e) {
            queryLoaderResultDesc.setDealFlag(false);
            queryLoaderResultDesc.setDealException(e);
            logger.error(String.format("%s DBSqlQueryLoader load(%s) Throwable:%s ", this.callbackListenerHandler.listenerIdentification(), bufferSqlInfo(), e.getMessage()), e);
        } finally {
            callbackListenerHandler.loadEnd(queryLoaderResultDesc);
        }
        return queryLoaderResultDesc;
    }

    private QueryLoaderResultDesc executeQuery(QueryLoaderResultDesc queryLoaderResultDesc, long beginTimeMS) {
        String dbName = this.queryConfig.getDbName();
        String sql = this.queryConfig.getSql();

        long loaderTotal = 0;
        long usingTimeMS_getJdbcStatement = -1;
        long usingTimeMS_executeQuerySql = -1;
        long usingTimeMS_getMetaData = -1;
        long usingTimeMS_processData = -1;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;


        try {
            //第一步:获取连接,创建查询statement
            connection = getConnection(dbName);
            statement = getJdbcStatement(dbName, connection);

            long endTimeMS_statement = System.currentTimeMillis();
            usingTimeMS_getJdbcStatement = endTimeMS_statement - beginTimeMS;
            queryLoaderResultDesc.setUsingTimeMS_getJdbcStatement(usingTimeMS_getJdbcStatement);

            //第二步:执行sql查询
            resultSet = statement.executeQuery(sql);

            long endTimeMS_executeQuerySql = System.currentTimeMillis();
            usingTimeMS_executeQuerySql = endTimeMS_executeQuerySql - endTimeMS_statement;
            queryLoaderResultDesc.setUsingTimeMS_executeQuerySql(usingTimeMS_executeQuerySql);

            //第三步:获取查询结果集的描述信息
            dbColumnMetaDataDefine = parserResultSetMetaData(resultSet);
            this.callbackListenerHandler.loadBegin(dbColumnMetaDataDefine);

            long endTimeMs_getMetaData = System.currentTimeMillis();
            usingTimeMS_getMetaData = endTimeMs_getMetaData - endTimeMS_executeQuerySql;
            queryLoaderResultDesc.setUsingTimeMS_getMetaData(usingTimeMS_getMetaData);

            //第四步:逐行获取查询的数据结果集
            loaderTotal = processResultSetData(resultSet);
            queryLoaderResultDesc.setLoaderTotal(loaderTotal);

            long endTimeMS_processData = System.currentTimeMillis();
            usingTimeMS_processData = endTimeMS_processData - endTimeMs_getMetaData;
            queryLoaderResultDesc.setUsingTimeMS_processData(usingTimeMS_processData);

            queryLoaderResultDesc.setDealFlag(true);
        } catch (LoaderException le) {
            queryLoaderResultDesc.setDealException(le);
            queryLoaderResultDesc.setDealFlag(false);
            logger.error(String.format("%s load LoaderException:%s,%s", this.callbackListenerHandler.listenerIdentification(), le.getMessage(), bufferSqlInfo()), le);
        } catch (Exception e) {
            queryLoaderResultDesc.setDealException(e);
            queryLoaderResultDesc.setDealFlag(false);
            logger.error(String.format("%s load Exception:%s,%s", this.callbackListenerHandler.listenerIdentification(), e.getMessage(), bufferSqlInfo()), e);
        } catch (Throwable t) {
            queryLoaderResultDesc.setDealException(t);
            queryLoaderResultDesc.setDealFlag(false);
            logger.error(String.format("%s load Throwable:%s,%s", this.callbackListenerHandler.listenerIdentification(), t.getMessage(), bufferSqlInfo()), t);
        } finally {
            closeQueryJdbcRelevant(connection, statement, resultSet);
        }

        long endTimeMS = System.currentTimeMillis();
        long usingTimeMS = endTimeMS - beginTimeMS;
        queryLoaderResultDesc.setUsingTimeMS(usingTimeMS);

        return queryLoaderResultDesc;
    }

    private long processResultSetData(ResultSet resultSet) throws SQLException {
        long beginTimeMS_beforeProcessRow = System.currentTimeMillis();
        long beginTimeMS_batch = beginTimeMS_beforeProcessRow;
        int resultIndex = 0;
        int batchId = 0;
        while (resultSet != null && resultSet.next()) {
            try {
                dealResult(resultIndex, resultSet);
                if (resultIndex > 0 && resultIndex % 10000 == 0) {
                    batchId++;
                    long endTimeMS_batch = System.currentTimeMillis();
                    long batch_cost = endTimeMS_batch - beginTimeMS_batch;
                    beginTimeMS_batch = endTimeMS_batch;
                    logger.debug(String.format("%s dealResult batchId=%s Index=%s cost=%s,%s", this.callbackListenerHandler.listenerIdentification(), batchId, resultIndex, batch_cost, bufferSqlInfo()));
                }
            } catch (LoaderException le) {
                logger.error(String.format("%s dealResult batchId=%s Index=%s,%s LoaderException:%s", this.callbackListenerHandler.listenerIdentification(), batchId, resultIndex, bufferSqlInfo(), le.getMessage()), le);
            } catch (Exception e) {
                logger.error(String.format("%s dealResult batchId=%s Index=%s,%s Exception:%s", this.callbackListenerHandler.listenerIdentification(), batchId, resultIndex, bufferSqlInfo(), e.getMessage()), e);
            } catch (Throwable e) {
                logger.error(String.format("%s dealResult batchId=%s Index=%s,%s Throwable:%s", this.callbackListenerHandler.listenerIdentification(), batchId, resultIndex, bufferSqlInfo(), e.getMessage()), e);
            } finally {
                resultIndex++;
                if (queryConfig.isTopQuery() && resultIndex >= queryConfig.getTopSize()) {
                    // top查询时,达到top数后退出
                    break;
                }
            }
        }
        return resultIndex;
    }

    private void dealResult(int resultIndex, ResultSet resultSet) {
        int columnSize = dbColumnMetaDataDefine.size();
        int columnIndex = 0;
        DBTableRow dbTableRow = new DBTableRow(resultIndex, columnSize);
        DBColumnMetaData dbColumnMetaData = null;
        Object columnValue = null;
        try {
            for (; columnIndex < columnSize; columnIndex++) {
                dbColumnMetaData = dbColumnMetaDataDefine.getDBColumnMetaData(columnIndex);
                int columnMetDataIndex = dbColumnMetaData.getColumnMetDataIndex();
                columnValue = resultSet.getObject(columnMetDataIndex);
                Object ruturn_columnValue = DBColumnMetaDataUtil.getColumnValue(dbColumnMetaData, resultSet);
                DBColumnValue dbColumnValue = new DBColumnValue(dbColumnMetaData, ruturn_columnValue);
                boolean ok = dbTableRow.addColumnValue(dbColumnMetaDataDefine, dbColumnValue);
                if (!ok) {
                    logger.error(String.format("%s dealResult Exception.resultIndex=%s,columnIndex=%s,DBColumnMetaData=%s,columnValue=%s,addColumnValue Failure",
                            this.callbackListenerHandler.listenerIdentification(), resultIndex, columnIndex, dbColumnMetaData, columnValue));
                }
            }
        } catch (LoaderException le) {
            RowInfo rowInfo = new RowInfo();
            rowInfo.setException(true);
            rowInfo.setThrowable(le);
            dbTableRow.setRowInfo(rowInfo);
            String errorDesc = String.format("resultIndex=%s,columnIndex=%s,DBColumnMetaData=%s,columnValue=%s,LoaderException:%s", resultIndex, columnIndex, dbColumnMetaData, columnValue, le.getMessage());
            rowInfo.setExceptionDesc(errorDesc);
        } catch (Exception e) {
            RowInfo rowInfo = new RowInfo();
            rowInfo.setException(true);
            rowInfo.setThrowable(e);
            dbTableRow.setRowInfo(rowInfo);
            String errorDesc = String.format("resultIndex=%s,columnIndex=%s,DBColumnMetaData=%s,columnValue=%s,Exception:%s", resultIndex, columnIndex, dbColumnMetaData, columnValue, e.getMessage());
            rowInfo.setExceptionDesc(errorDesc);
            logger.error(String.format("%s dealResult Exception.resultIndex=%s,columnIndex=%s,DBColumnMetaData=%s,columnValue=%s,Exception:%s",
                    this.callbackListenerHandler.listenerIdentification(), resultIndex, columnIndex, dbColumnMetaData, columnValue, e.getMessage()), e);
        } catch (Throwable e) {
            RowInfo rowInfo = new RowInfo();
            rowInfo.setException(true);
            rowInfo.setThrowable(e);
            dbTableRow.setRowInfo(rowInfo);
            String errorDesc = String.format("resultIndex=%s,columnIndex=%s,DBColumnMetaData=%s,columnValue=%s,Exception:%s", resultIndex, columnIndex, dbColumnMetaData, columnValue, e.getMessage());
            rowInfo.setExceptionDesc(errorDesc);
            logger.error(String.format("%s dealResult Throwable.resultIndex=%s,columnIndex=%s,DBColumnMetaData=%s,columnValue=%s,Exception:%s",
                    this.callbackListenerHandler.listenerIdentification(), resultIndex, columnIndex, dbColumnMetaData, columnValue, e.getMessage()), e);
        } finally {
            this.callbackListenerHandler.processRow(dbColumnMetaDataDefine, dbTableRow);
        }
    }

    private DBColumnMetaDataDefine parserResultSetMetaData(ResultSet resultSet) {
        ResultSetMetaData resultSetMetaData = null;
        if (resultSet == null) {
            logger.error(String.format("%s parserDBColumnMetaData %s ResultSet is null", this.callbackListenerHandler.listenerIdentification(), this.queryConfig.getSqlName()));
            throw new LoaderException(String.format("parserDBColumnMetaData ResultSet is null"));
        } else {
            try {
                resultSetMetaData = resultSet.getMetaData();
            } catch (SQLException e) {
                logger.error(String.format("%s parserDBColumnMetaData %s ResultSet.getMetaData() Exception:%s", this.callbackListenerHandler.listenerIdentification(), this.queryConfig.getSqlName(), e.getMessage()), e);
                throw new LoaderException(String.format("parserDBColumnMetaData ResultSet.getMetaData() Exception:%s", e.getMessage()), e);
            }
        }
        try {
            DBColumnMetaDataDefine dbColumnMetaDataDefine = new DBColumnMetaDataDefine(this.dataSourceConfig.getDatasourceType());

            int columnMetDataCount = resultSetMetaData.getColumnCount();
            for (int columnMetDataIndex = 1; columnMetDataIndex <= columnMetDataCount; columnMetDataIndex++) {
                int columnIndex = columnMetDataIndex - 1;
                DBColumnMetaData dbColumnMetaData = parserDBColumnMetaData(resultSetMetaData, columnMetDataIndex, columnIndex);

                if (dbColumnMetaData != null) {
                    dbColumnMetaDataDefine.addDBColumnMetaData(dbColumnMetaData);
                }
            }
            return dbColumnMetaDataDefine;
        } catch (Throwable t) {
            logger.error(String.format("%s parserResultSetMetaData Exception:%s", this.callbackListenerHandler.listenerIdentification(), t.getMessage()), t);
            throw new LoaderException(String.format("parserResultSetMetaData Exception:%s", t.getMessage()), t);
        }
    }

    private DBColumnMetaData parserDBColumnMetaData(ResultSetMetaData resultSetMetaData, int columnMetDataIndex, int columnIndex) {
        String columnName = null;
        try {
            DBColumnMetaData dbColumnMetaData = new DBColumnMetaData();
            //设置当前column在查询的ResultSetMetaData中的顺序相关
            dbColumnMetaData.setColumnIndex(columnIndex);
            dbColumnMetaData.setColumnMetDataIndex(columnMetDataIndex);

            // String getCatalogName(int column) 获取指定列的表目录名称。
            String columnCatalogName = resultSetMetaData.getCatalogName(columnMetDataIndex);
            dbColumnMetaData.setColumnCatalogName(columnCatalogName);
            // String getColumnClassName(int column) 如果调用方法
            String columnClassName = resultSetMetaData.getColumnClassName(columnMetDataIndex);
            dbColumnMetaData.setColumnClassName(columnClassName);
            // ResultSet.getObject 从列中检索值，则返回构造其实例的 Java 类的完全限定名称。
            // int getColumnCount() 返回此 ResultSet 对象中的列数。
            // int getColumnDisplaySize(int column) 指示指定列的最大标准宽度，以字符为单位。
            int columnDisplaySize = resultSetMetaData.getColumnDisplaySize(columnMetDataIndex);
            dbColumnMetaData.setLength(columnDisplaySize);
            // String getColumnLabel(int column) 获取用于打印输出和显示的指定列的建议标题。
            // resultSetMetaData.getColumnLabel(columnIndex);
            // String getColumnName(int column) 获取指定列的名称。
            columnName = resultSetMetaData.getColumnName(columnMetDataIndex);
            String columnLabel = resultSetMetaData.getColumnLabel(columnMetDataIndex);
            // DB2数据库中查询时别名与字段名不一致
            if (!columnName.equalsIgnoreCase(columnLabel)) {
                columnName = columnLabel;
            }
            dbColumnMetaData.setColumnName(columnName);
            // int getColumnType(int column) 检索指定列的 SQL 类型。
            int colunmSqlType = resultSetMetaData.getColumnType(columnMetDataIndex);
            dbColumnMetaData.setColunmSqlType(colunmSqlType);
            // String getColumnTypeName(int column) 检索指定列的数据库特定的类型名称。
            String columnTypeName = resultSetMetaData.getColumnTypeName(columnMetDataIndex);
            dbColumnMetaData.setColumnTypeName(columnTypeName);
            // int getPrecision(int column) 获取指定列的小数位数。
            int precision = resultSetMetaData.getPrecision(columnMetDataIndex);
            dbColumnMetaData.setPrecision(precision);
            // int getScale(int column) 获取指定列的小数点右边的位数。
            int scale = resultSetMetaData.getScale(columnMetDataIndex);
            dbColumnMetaData.setScale(scale);
            // resultSetMetaData.getScale(column);
            // String getSchemaName(int column) 获取指定列的表模式。
            String columnSchema = resultSetMetaData.getSchemaName(columnMetDataIndex);
            dbColumnMetaData.setColumnSchema(columnSchema);
            // String getTableName(int column) 获取指定列的名称。
            String tableName = resultSetMetaData.getTableName(columnMetDataIndex);
            dbColumnMetaData.setColumnTableName(tableName);

            //分析数据库类型与java类型的映射
            EnumDbDataType enumDbDataType = DBColumnMetaDataUtil.getEnumDbDataType(this.dataSourceConfig.getDatasourceType(), columnClassName, colunmSqlType, scale, precision,
                    columnTypeName);
            dbColumnMetaData.setType(enumDbDataType);
            return dbColumnMetaData;
        } catch (Throwable t) {
            logger.error(String.format("%s parserDBColumnMetaData columnMetDataIndex=%s,columnName=%s Exception:%s", this.callbackListenerHandler.listenerIdentification(), columnMetDataIndex, columnName), t);
            throw new LoaderException(String.format("parserDBColumnMetaData columnMetDataIndex=%s,columnName=%s Exception:%s", columnMetDataIndex, columnName, t.getMessage()));
        }
    }

    private DataSource getDataSource(String dbName) {
        String dataSourceBeanName = DataSourceNameGenerator.getDataSourceBeanName(dbName);
        Object object = SpringDataSourceContextUtil.getBean(dataSourceBeanName);
        if (object == null) {
            logger.error(String.format("%s dbName=%s[dataSourceBeanName=%s] can't find at SpringDataSourceContextUtil", this.callbackListenerHandler.listenerIdentification(), dbName, dataSourceBeanName));
            throw new LoaderException(String.format("dbName=%s[dataSourceBeanName=%s] can't find at SpringDataSourceContextUtil", dbName, dataSourceBeanName));
        }
        return (DataSource) object;
    }

    private Connection getConnection(String dbName) throws SQLException {
        DataSource dataSource = getDataSource(dbName);
        Connection connection = dataSource.getConnection();
        if (connection == null) {
            logger.error(String.format("%s dbName=%s getConnection() is null", this.callbackListenerHandler.listenerIdentification(), dbName));
            throw new LoaderException(String.format("dbName=%s getConnection() is null", dbName));
        }
        return connection;
    }

    private Statement getJdbcStatement(String dbName, Connection connection) {
        try {
            Statement statement = connection.createStatement();

            int fetchSize = queryConfig.getFetchSize();
            if (fetchSize < 1) {
                fetchSize = 1000;
            }
            if (queryConfig.isTopQuery()) {
                if (fetchSize > queryConfig.getTopSize()) {
                    fetchSize = queryConfig.getTopSize();
                }
            }
            statement.setFetchSize(fetchSize);
            int queryTimeoutSeconds = queryConfig.getQueryTimeoutSeconds();
            if (queryTimeoutSeconds < 1) {
                queryTimeoutSeconds = 5 * 60;
            }
            statement.setQueryTimeout(queryTimeoutSeconds);
            return statement;
        } catch (Exception e) {
            logger.error(String.format("%s dbName=%s,dataSourceConfig=%s,queryConfig=%s", this.callbackListenerHandler.listenerIdentification(), dbName, dataSourceConfig, queryConfig));
            throw new LoaderException(String.format("dbName=%s can't create statement"));
        }
    }

    private void closeQueryJdbcRelevant(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                logger.error(String.format("%s closeQueryJdbcRelevant ResultSet Exception:%s,dataSourceConfig=%s,queryConfig=%s", this.callbackListenerHandler.listenerIdentification(), e.getMessage(), dataSourceConfig, queryConfig));
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                logger.error(String.format("%s closeQueryJdbcRelevant Statement Exception:%s,dataSourceConfig=%s,queryConfig=%s", this.callbackListenerHandler.listenerIdentification(), e.getMessage(), dataSourceConfig, queryConfig));
            }

        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                logger.error(String.format("%s closeQueryJdbcRelevant Connection Exception:%s,dataSourceConfig=%s,queryConfig=%s", this.callbackListenerHandler.listenerIdentification(), e.getMessage(), dataSourceConfig, queryConfig));
            }
        }
    }

    private String bufferSqlInfo() {
        return String.format("SQLINFO[dataSourceConfig=%s,queryConfig=%s]", this.dataSourceConfig, this.queryConfig);
    }
}
