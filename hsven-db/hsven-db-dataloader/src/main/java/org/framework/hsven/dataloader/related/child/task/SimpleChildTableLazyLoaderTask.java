package org.framework.hsven.dataloader.related.child.task;

import org.framework.hsven.dataloader.api.IDataSourceProvider;
import org.framework.hsven.dataloader.api.IRelatedTableLoadListener;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.ITableLoaderTask;
import org.framework.hsven.dataloader.related.TableLoadResult;
import org.framework.hsven.dataloader.related.child.ChildTableConfigCacheEntity;
import org.framework.hsven.dataloader.related.dependency.SimpleChildTableLazyCallableTaskDependency;
import org.framework.hsven.dataloader.related.main.MainTableLoadPartitionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 不是直接与主表进行关联的子表的数据加载任务
 * 加载有2中方式
 * 1. 当前子表需要适时从数据库查询
 * 2. 当前子表数据不需要适时查询,从已经完成的缓存中获取
 */
public class SimpleChildTableLazyLoaderTask implements ITableLoaderTask<TableLoadResult> {
    private static Logger logger = LoggerFactory.getLogger(SimpleChildTableLazyLoaderTask.class);
    private final IRelatedTableLoadListener iRelatedTableLoadListener;
    private final IDataSourceProvider iDataSourceProvider;
    private final MainTableLoadPartitionContext mainTableLoadPartitionContext;
    private final TableLoadDefine tableLoadDefine;
    private final SimpleChildTable currentsimpleChildTable;
    private final ChildTableConfigCacheEntity childTableConfigCacheEntity;
    private final SimpleChildTableLazyCallableTaskDependency callableTaskDependency;
    private long taskCreateTimeMS = System.currentTimeMillis();
    private long taskDealBeginTimeMS;
    private long taskDealEndTimeMS;

    public SimpleChildTableLazyLoaderTask(IRelatedTableLoadListener iRelatedTableLoadListener, IDataSourceProvider iDataSourceProvider, MainTableLoadPartitionContext mainTableLoadPartitionContext, TableLoadDefine tableLoadDefine, SimpleChildTable currentsimpleChildTable, ChildTableConfigCacheEntity childTableConfigCacheEntity, SimpleChildTableLazyCallableTaskDependency callableTaskDependency) {
        this.iRelatedTableLoadListener = iRelatedTableLoadListener;
        this.iDataSourceProvider = iDataSourceProvider;
        this.mainTableLoadPartitionContext = mainTableLoadPartitionContext;
        this.tableLoadDefine = tableLoadDefine;
        this.currentsimpleChildTable = currentsimpleChildTable;
        this.childTableConfigCacheEntity = childTableConfigCacheEntity;
        this.callableTaskDependency = callableTaskDependency;
    }


    @Override
    public TableLoadResult call() throws Exception {
        TableLoadResult tableLoadResult = load();
        return tableLoadResult;
    }

    @Override
    public void destory() {

    }

    private TableLoadResult load() {
        long resultIndex = 0;
        long dealUsingTimeMS = -1;
        String sql = null;
        boolean flag = false;
        QueryLoaderResultDesc queryLoaderResultDesc = null;
        try {
            taskDealBeginTimeMS = System.currentTimeMillis();
            queryLoaderResultDesc = startChildTableLazyLoader();
            if (queryLoaderResultDesc != null) {
                resultIndex = queryLoaderResultDesc.getResultIndex();
            }
            flag = true;
        } catch (Exception e) {
            logger.error(String.format("%s SimpleChildTableLazyLoaderTask,defineType:%s,Exception:%s,sql:[%s]", iRelatedTableLoadListener.relatedListenerIdentification(), tableLoadDefine.getDefineType(), e.getMessage(), sql), e);
        } catch (Throwable e) {
            logger.error(String.format("%s SimpleChildTableLazyLoaderTask,defineType:%s,Throwable:%s,sql:[%s]", iRelatedTableLoadListener.relatedListenerIdentification(), tableLoadDefine.getDefineType(), e.getMessage(), sql), e);
        } finally {
            taskDealEndTimeMS = System.currentTimeMillis();
            dealUsingTimeMS = this.taskDealEndTimeMS - taskDealBeginTimeMS;
            logger.info(String.format("%s SimpleChildTableLazyLoaderTask,defineType:%s,dealUsingTimeMS:%s,resultIndex:%s,sql:[%s]", iRelatedTableLoadListener.relatedListenerIdentification(), tableLoadDefine.getDefineType(), dealUsingTimeMS, resultIndex, sql));
        }

        TableLoadResult tableLoadResult = new TableLoadResult();
        tableLoadResult.setTaskId(getIdentify());
        tableLoadResult.setTaskCreateTimeMS(this.taskCreateTimeMS);
        tableLoadResult.setDealResult(flag);
        tableLoadResult.setQueryLoaderResultDesc(queryLoaderResultDesc);
        tableLoadResult.setDealUsingTimeMS(dealUsingTimeMS);
        return tableLoadResult;
    }

    private String getIdentify() {
        return null;
    }

    private QueryLoaderResultDesc startChildTableLazyLoader() {
        return new QueryLoaderResultDesc();
    }
}
