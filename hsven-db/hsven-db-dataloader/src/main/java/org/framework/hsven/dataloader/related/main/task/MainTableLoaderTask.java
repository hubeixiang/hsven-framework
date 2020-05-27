package org.framework.hsven.dataloader.related.main.task;

import org.framework.hsven.dataloader.beans.dependency.StructSql;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.listener.MainTableLoaderListenerImpl;
import org.framework.hsven.dataloader.loader.DBSqlQueryLoader;
import org.framework.hsven.dataloader.loader.DBSqlQueryLoaderUtil;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.ITableLoaderTask;
import org.framework.hsven.dataloader.related.RelatedLoaderHandlerHolder;
import org.framework.hsven.dataloader.related.TableLoadResult;
import org.framework.hsven.dataloader.related.dependency.SimpleMainTableCallableTaskDependency;
import org.framework.hsven.dataloader.related.main.MainTableLoadPartitionContext;
import org.framework.hsven.dataloader.valid.related.SimpleMainTableUtil;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTableLoaderTask implements ITableLoaderTask<TableLoadResult> {
    private static Logger logger = LoggerFactory.getLogger(MainTableLoaderTask.class);
    private final RelatedLoaderHandlerHolder relatedLoaderHandlerHolder;
    private final MainTableLoadPartitionContext mainTableLoadPartitionContext;
    private final TableLoadDefine tableLoadDefine;
    private final SimpleMainTableCallableTaskDependency callableTaskDependency;
    private long taskCreateTimeMS = System.currentTimeMillis();
    private long taskDealBeginTimeMS;
    private long taskDealEndTimeMS;

    public MainTableLoaderTask(RelatedLoaderHandlerHolder relatedLoaderHandlerHolder, MainTableLoadPartitionContext mainTableLoadPartitionContext, TableLoadDefine tableLoadDefine, SimpleMainTableCallableTaskDependency callableTaskDependency) {
        this.relatedLoaderHandlerHolder = relatedLoaderHandlerHolder;
        this.mainTableLoadPartitionContext = mainTableLoadPartitionContext == null ? new MainTableLoadPartitionContext() : mainTableLoadPartitionContext;
        this.tableLoadDefine = tableLoadDefine;
        this.callableTaskDependency = callableTaskDependency;
    }

    @Override
    public TableLoadResult call() throws Exception {
        TableLoadResult tableLoadResult = load();
        return tableLoadResult;
    }

    private TableLoadResult load() {
        long resultIndex = 0;
        long dealUsingTimeMS = -1;
        String sql = null;
        boolean flag = false;
        SimpleMainTable simpleMainTable = callableTaskDependency.getCurrentTable(null);
        QueryLoaderResultDesc queryLoaderResultDesc = null;
        try {
            taskDealBeginTimeMS = System.currentTimeMillis();
            queryLoaderResultDesc = startMainTableLoader();
            if (queryLoaderResultDesc != null) {
                resultIndex = queryLoaderResultDesc.getResultIndex();
                sql = queryLoaderResultDesc.getSql();
                flag = queryLoaderResultDesc.isDealFlag();
            }else {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
            logger.error(String.format("%s MainTableLoaderTask,%s,Exception:%s,sql:[%s]", this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), logIdentify(), e.getMessage(), sql), e);
        } catch (Throwable e) {
            flag = false;
            logger.error(String.format("%s MainTableLoaderTask,%s,Throwable:%s,sql:[%s]", this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), logIdentify(), e.getMessage(), sql), e);
        } finally {
            taskDealEndTimeMS = System.currentTimeMillis();
            dealUsingTimeMS = this.taskDealEndTimeMS - taskDealBeginTimeMS;
            logger.info(String.format("%s MainTableLoaderTask,%s,dealUsingTimeMS:%s,resultIndex:%s,sql:[%s]", this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), logIdentify(), dealUsingTimeMS, resultIndex, sql));
        }

        TableLoadResult tableLoadResult = new TableLoadResult();
        tableLoadResult.setTaskId(simpleMainTable.getIdentify());
        tableLoadResult.setTaskCreateTimeMS(this.taskCreateTimeMS);
        tableLoadResult.setDealResult(flag);
        tableLoadResult.setQueryLoaderResultDesc(queryLoaderResultDesc);
        tableLoadResult.setDealUsingTimeMS(dealUsingTimeMS);
        return tableLoadResult;
    }

    @Override
    public void destory() {
    }

    private QueryLoaderResultDesc startMainTableLoader() {
        MainTableLoaderListenerImpl mainTableLoaderListener = new MainTableLoaderListenerImpl(this.relatedLoaderHandlerHolder, this.tableLoadDefine, callableTaskDependency);
        QueryConfig queryConfig = createQueryConfig();
        DBSqlQueryLoader dbSqlQueryLoader = DBSqlQueryLoaderUtil.createDBSqlQueryLoader(mainTableLoaderListener, queryConfig, this.relatedLoaderHandlerHolder.getiDataSourceProvider());
        return dbSqlQueryLoader.load();
    }

    private QueryConfig createQueryConfig() {
        SimpleMainTable simpleMainTable = callableTaskDependency.getCurrentTable(null);
        QueryConfig queryConfig = new QueryConfig();
        DataSourceType dataSourceType = relatedLoaderHandlerHolder.getiDataSourceProvider().getDataSourceType(simpleMainTable.getTableDefine().getDbName());
        StructSql structSql = SimpleMainTableUtil.structSimpleSql(dataSourceType, tableLoadDefine, simpleMainTable, this.mainTableLoadPartitionContext.getPartitionFieldValueSet());
        if (!structSql.hasSql()) {
            throw new RuntimeException(String.format("%s MainTableLoaderTask,defineType:%s,can't struct sql", this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), tableLoadDefine.getDefineType()));
        }
        queryConfig.setDbName(simpleMainTable.getTableDefine().getDbName());
        queryConfig.setSql(structSql.getWholeSql());
        queryConfig.setSqlName(simpleMainTable.getIdentify());
        queryConfig.setFetchSize(10000);
        queryConfig.setQueryTimeoutSeconds(simpleMainTable.getTableDefine().getQueryTimeoutSeconds());
        return queryConfig;
    }

    private String logIdentify() {
        return String.format("RelatedLoader defineType=%s", tableLoadDefine.getDefineType());
    }
}
