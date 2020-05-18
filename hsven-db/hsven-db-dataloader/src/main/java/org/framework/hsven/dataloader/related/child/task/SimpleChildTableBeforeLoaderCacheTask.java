package org.framework.hsven.dataloader.related.child.task;

import org.framework.hsven.dataloader.api.IDataSourceProvider;
import org.framework.hsven.dataloader.beans.dependency.StructSql;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.listener.SimpleChildTableBeforeLoaderCacheListenerImpl;
import org.framework.hsven.dataloader.loader.DBSqlQueryLoader;
import org.framework.hsven.dataloader.loader.DBSqlQueryLoaderUtil;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.ITableLoaderTask;
import org.framework.hsven.dataloader.related.TableLoadResult;
import org.framework.hsven.dataloader.related.child.ChildTableConfigCacheEntity;
import org.framework.hsven.dataloader.valid.related.SimpleChildTableUtil;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 需要需要将子表数据全部查询完,并进行全部缓存的任务
 *
 * @see SimpleChildTableBeforeLoaderCacheListenerImpl
 */
public class SimpleChildTableBeforeLoaderCacheTask implements ITableLoaderTask<TableLoadResult> {
    private static Logger logger = LoggerFactory.getLogger(SimpleChildTableBeforeLoaderCacheTask.class);
    private final IDataSourceProvider iDataSourceProvider;
    private final ChildTableConfigCacheEntity childTableConfigCacheEntity;
    private long taskCreateTimeMS = System.currentTimeMillis();
    private long taskDealBeginTimeMS;
    private long taskDealEndTimeMS;

    public SimpleChildTableBeforeLoaderCacheTask(IDataSourceProvider iDataSourceProvider, ChildTableConfigCacheEntity childTableConfigCacheEntity) {
        this.iDataSourceProvider = iDataSourceProvider;
        this.childTableConfigCacheEntity = childTableConfigCacheEntity;
    }

    @Override
    public TableLoadResult call() throws Exception {
        boolean flag = false;
        long dealUsingTimeMS = -1;
        QueryLoaderResultDesc queryLoaderResultDesc = null;
        long resultIndex = 0;
        try {

            taskDealBeginTimeMS = System.currentTimeMillis();
            queryLoaderResultDesc = loadConfigCacheChildTable();
            if (queryLoaderResultDesc != null) {
                resultIndex = queryLoaderResultDesc.getResultIndex();
            }
            flag = true;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + " Exception,taskInfo=" + childTableConfigCacheEntity.getIdentify(), e);
        } catch (Throwable e) {
            logger.error(this.getClass().getName() + " Throwable,taskInfo=" + childTableConfigCacheEntity.getIdentify(), e);
        } finally {
            taskDealEndTimeMS = System.currentTimeMillis();
            dealUsingTimeMS = this.taskDealEndTimeMS - taskDealBeginTimeMS;
            if (flag) {
                logger.info(this.getClass().getName() + " dealEnd,taskInfo=" + childTableConfigCacheEntity.getIdentify() + ", dealUsingTimeMS : " + dealUsingTimeMS + "ms" + ",resultIndex : " + resultIndex);
            } else {
                logger.error(this.getClass().getName() + " dealFail,taskInfo=" + childTableConfigCacheEntity.getIdentify() + ", dealUsingTimeMS : " + dealUsingTimeMS + "ms" + ",resultIndex : " + resultIndex);
            }
        }

        TableLoadResult tableLoadResult = new TableLoadResult();
        tableLoadResult.setTaskId(this.childTableConfigCacheEntity.getIdentify());
        tableLoadResult.setTaskCreateTimeMS(this.taskCreateTimeMS);
        tableLoadResult.setDealResult(flag);
        tableLoadResult.setQueryLoaderResultDesc(queryLoaderResultDesc);
        tableLoadResult.setDealUsingTimeMS(dealUsingTimeMS);
        return tableLoadResult;
    }

    @Override
    public void destory() {

    }

    private QueryLoaderResultDesc loadConfigCacheChildTable() {
        SimpleChildTableBeforeLoaderCacheListenerImpl listener = new SimpleChildTableBeforeLoaderCacheListenerImpl(this.childTableConfigCacheEntity);

        QueryConfig queryConfig = getQueryConfig();

        DBSqlQueryLoader dbSqlQueryLoader = DBSqlQueryLoaderUtil.createDBSqlQueryLoader(listener, queryConfig, iDataSourceProvider);
        QueryLoaderResultDesc queryLoaderResultDesc = dbSqlQueryLoader.load();
        return queryLoaderResultDesc;
    }

    private QueryConfig getQueryConfig() {
        TableLoadDefine tableLoadDefine = this.childTableConfigCacheEntity.getTableLoadDefine();
        SimpleChildTable simpleChildTable = this.childTableConfigCacheEntity.getSimpleChildTable();
        QueryConfig queryConfig = new QueryConfig();
        DataSourceType dbType = iDataSourceProvider.getDataSourceType(simpleChildTable.getTableDefine().getDbName());
        StructSql structSql = SimpleChildTableUtil.toCacheAllDataSql(dbType, tableLoadDefine, simpleChildTable);
        if (structSql == null || !structSql.hasSql()) {
            logger.error("ChildTableConfigCacheTask cache load fail,sql is null.taskInfo=" + childTableConfigCacheEntity.getIdentify());
            return null;
        }
        queryConfig.setDbName(simpleChildTable.getTableDefine().getDbName());
        queryConfig.setSql(structSql.getWholeSql());
        queryConfig.setFetchSize(10000);
        queryConfig.setQueryTimeoutSeconds(simpleChildTable.getTableDefine().getQueryTimeoutSeconds());
        queryConfig.setSqlName(simpleChildTable.getIdentify());
        return queryConfig;
    }
}
