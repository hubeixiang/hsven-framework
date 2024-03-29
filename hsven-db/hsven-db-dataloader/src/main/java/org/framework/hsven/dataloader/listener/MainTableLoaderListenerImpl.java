package org.framework.hsven.dataloader.listener;

import org.framework.hsven.dataloader.api.IDBSqlQueryLoaderListener;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.beans.db.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.dependency.EnumTableType;
import org.framework.hsven.dataloader.beans.loader.LazyRelatedFieldsAndRowIndex;
import org.framework.hsven.dataloader.beans.loader.LazyRelatedFieldsAndRowIndexGroup;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndexEntity;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndexGroup;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.RelatedLoaderHandlerHolder;
import org.framework.hsven.dataloader.related.TableLoadResult;
import org.framework.hsven.dataloader.related.child.ChildTableConfigCacheEntity;
import org.framework.hsven.dataloader.related.child.task.SimpleChildTableBeforeLoaderCacheTask;
import org.framework.hsven.dataloader.related.child.task.SimpleChildTableLazyLoaderTask;
import org.framework.hsven.dataloader.related.child.task.SimpleChildTableLoaderTask;
import org.framework.hsven.dataloader.related.dependency.CallableRelatedTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleChildTableCallableTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleChildTableGroup;
import org.framework.hsven.dataloader.related.dependency.SimpleChildTableLazyCallableTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleLazyChildTableGroup;
import org.framework.hsven.dataloader.related.dependency.SimpleMainTableCallableTaskDependency;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.framework.hsven.executor.exception.PoolExcuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 关联加载主子表数据时,主表的单条数据的处理类,定义的处理流程
 */
public class MainTableLoaderListenerImpl implements IDBSqlQueryLoaderListener {
    private static Logger logger = LoggerFactory.getLogger(MainTableLoaderListenerImpl.class);
    private final RelatedLoaderHandlerHolder relatedLoaderHandlerHolder;
    private final TableLoadDefine tableLoadDefine;
    private final SimpleMainTable currentLoaderTable;
    private final SimpleMainTableCallableTaskDependency simpleMainTableCallableTaskDependency;
    private final AtomicLong taskId = new AtomicLong(0);
    private DBColumnMetaDataDefine currentLoaderTableDBColumnMetaDataDefine;
    //预先加载的字表的数据缓存
    //Map<SimpleChildTable.getIdentify(),ChildTableConfigCacheEntity>
    private Map<String, ChildTableConfigCacheEntity> loaderPrefetchChildTableDataMap = new HashMap<String, ChildTableConfigCacheEntity>();
    private long mainTableLoadInitBeginTimeMs = 0;
    private long mainTableLoadBeginTimeMs = 0;

    public MainTableLoaderListenerImpl(RelatedLoaderHandlerHolder relatedLoaderHandlerHolder, TableLoadDefine tableLoadDefine, SimpleMainTableCallableTaskDependency simpleMainTableCallableTaskDependency) {
        this.relatedLoaderHandlerHolder = relatedLoaderHandlerHolder;
        this.tableLoadDefine = tableLoadDefine;
        this.currentLoaderTable = this.tableLoadDefine.getSimpleMainTable();
        this.simpleMainTableCallableTaskDependency = simpleMainTableCallableTaskDependency;
    }


    @Override
    public String listenerIdentification() {
        return String.format("MainTableLoader");
    }

    @Override
    public void init(DataSourceConfig dataSourceConfig, QueryConfig queryConfig) {
        logger.info(String.format("%s mainTable load init", logIdentify()));
        mainTableLoadInitBeginTimeMs = System.currentTimeMillis();
        //初始化需要预先查询缓存的子表,数据缓存实体
        List<SimpleChildTable> prefetchChildTableList = simpleMainTableCallableTaskDependency.getPrefetchChildTableList();
        if (CollectionUtils.isEmpty(prefetchChildTableList)) {
            return;
        }
        for (SimpleChildTable simpleChildTable : prefetchChildTableList) {
            ChildTableConfigCacheEntity childTableConfigCacheEntity = loaderPrefetchChildTableDataMap.get(simpleChildTable.getIdentify());
            if (childTableConfigCacheEntity == null) {
                childTableConfigCacheEntity = new ChildTableConfigCacheEntity(this.tableLoadDefine.getDefineType(), this.tableLoadDefine, simpleChildTable);
                loaderPrefetchChildTableDataMap.put(simpleChildTable.getIdentify(), childTableConfigCacheEntity);
            }
        }
    }

    @Override
    public void loadBegin(DBColumnMetaDataDefine dbColumnMetaDataDefine) {
        this.currentLoaderTableDBColumnMetaDataDefine = dbColumnMetaDataDefine;
        mainTableLoadBeginTimeMs = System.currentTimeMillis();
        logger.info(String.format("%s mainTable load begin", logIdentify()));
    }

    @Override
    public void processRow(DBTableRowInfo dbTableRowInfo) {
        try {
            addMainTableRowDataTable(dbTableRowInfo);

            parserChildTableLoadTaskByDataBaseTableRow(dbTableRowInfo);
        } catch (Exception e) {
            logger.error(String.format("%s %s processRow Exception:%s ,DBTableRowInfo:", this.getClass().getName(), logIdentify(), e.getMessage(), dbTableRowInfo), e);
        } catch (Throwable e) {
            logger.error(String.format("%s %s processRow Throwable:%s ,DBTableRowInfo:", this.getClass().getName(), logIdentify(), e.getMessage(), dbTableRowInfo), e);
        }
    }

    @Override
    public void loadEnd(QueryLoaderResultDesc queryLoaderResultDesc) {
        try {
            //最后一批次的主表数据加载完毕后,创建对应的子表加载任务
            addLastBatchChildTableLoadTasek();
            long end = System.currentTimeMillis();
            logger.info(String.format("%s mainTable load end.cost-from-init=%sms,cost-from-load-begin=%sms,totalResult=%s", logIdentify(), end - mainTableLoadInitBeginTimeMs, end - mainTableLoadBeginTimeMs, queryLoaderResultDesc == null ? 0 : queryLoaderResultDesc.getResultIndex()));

            //加载需要预先缓存的子表数据(如果子表配置了可以预先缓存,需要先将子表的数据预先缓存,以供在关联时直接从缓存获取,而不用每次都从数据库中查询获取)
            long start = System.currentTimeMillis();
            loaderPrefetchChildTableData();
            logger.info(String.format("%s cache child load end.cost=%sms", logIdentify(), System.currentTimeMillis() - start));

            //执行前面创建的子表加载任务
            start = System.currentTimeMillis();
            executeRecursionDependencyChildTableLoadTask(simpleMainTableCallableTaskDependency);
            logger.info(String.format("%s all child load end.cost=%sms", logIdentify(), System.currentTimeMillis() - start));
        } catch (Throwable e) {
            //执行加载完成步骤错误
            queryLoaderResultDesc.setDealFlag(false);
            queryLoaderResultDesc.setDealException(e);
            logger.error(String.format("%s loadEnd %s Throwable:%s ,QueryLoaderResultDesc:", this.getClass().getName(), logIdentify(), e.getMessage(), queryLoaderResultDesc), e);
        } finally {
            long end = System.currentTimeMillis();
            logger.info(String.format("%s allTable load end.cost-from-init=%sms,cost-from--load-begin=%sms,totalResult=%s", logIdentify(), end - mainTableLoadInitBeginTimeMs, end - mainTableLoadBeginTimeMs, queryLoaderResultDesc == null ? 0 : queryLoaderResultDesc.getResultIndex()));
            clearTmp();
        }
        this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().onEnd(queryLoaderResultDesc, null);
    }

    private String logIdentify() {
        return String.format("RelatedLoader defineType=%s", tableLoadDefine.getDefineType());
    }

    /**
     * 主表从数据库中获取数据后,每行数据的处理,将单行数据添加到缓存介质中
     *
     * @param dbTableRowInfo
     */
    private void addMainTableRowDataTable(DBTableRowInfo dbTableRowInfo) {
        Collection<TableField> fieldCollection = this.currentLoaderTable.getTableFieldSet().getFieldSet();
        this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().loadRow(dbTableRowInfo.getDbTableRow().getRowIndex(), dbTableRowInfo, fieldCollection);
    }


    /**
     * 分析主表从数据库中获取数据后,每行数据的数据，映射到关联的字表的关联值,并创建关联的字表对应的任务
     *
     * @param dbTableRowInfo
     */
    private void parserChildTableLoadTaskByDataBaseTableRow(DBTableRowInfo dbTableRowInfo) {
        boolean needCreatTask = false;
        int rowIndex = dbTableRowInfo.getDbTableRow().getRowIndex();
        if (rowIndex > 0 && rowIndex % 200 == 0) {
            needCreatTask = true;
        }
        createRecursionDependencyChildTableLoadTask(simpleMainTableCallableTaskDependency, dbTableRowInfo, needCreatTask, false);
    }

    /**
     * 主表记载完成后,最后一批主表数据的对应的数据，在字表中的加载任务关联与创建
     */
    private void addLastBatchChildTableLoadTasek() {
        createRecursionDependencyChildTableLoadTask(simpleMainTableCallableTaskDependency, null, true, true);
    }

    private void createRecursionDependencyChildTableLoadTask(CallableRelatedTaskDependency callableRelatedTaskDependency, DBTableRowInfo dbTableRowInfo, boolean needCreatTask, boolean isLastBatch) {
        if (callableRelatedTaskDependency == null) {
            return;
        }
        if (callableRelatedTaskDependency.hasCurrentTable()) {
            EnumTableType enumTableType = callableRelatedTaskDependency.getEnumTableType();
            switch (enumTableType) {
                case Child:
                    addRowData2MainTmp((SimpleChildTableCallableTaskDependency) callableRelatedTaskDependency, dbTableRowInfo);
                    break;
                case Lazy_subtable:
                    addRowData2LaxyTmp((SimpleChildTableLazyCallableTaskDependency) callableRelatedTaskDependency, dbTableRowInfo);
                    break;
                case Main:
                    createRecursionDependencyChildTableLoadTask(callableRelatedTaskDependency.getNext(), dbTableRowInfo, needCreatTask, isLastBatch);
                    break;
                default:
                    throw new RuntimeException("Error EnumTableType " + enumTableType);

            }
            if (needCreatTask || isLastBatch) {
                //如果是整批次,或者是最后一批次的数据,则需要创建任务
                creatBatchChildTableLoadTask(callableRelatedTaskDependency);
            }
        }

        if (callableRelatedTaskDependency.hasNext()) {
            createRecursionDependencyChildTableLoadTask(callableRelatedTaskDependency.getNext(), dbTableRowInfo, needCreatTask, isLastBatch);
        }
    }

    private void addRowData2MainTmp(SimpleChildTableCallableTaskDependency simpleChildTableCallableTaskDependency, DBTableRowInfo dbTableRowInfo) {
        //当最后一批数据加载完成后,创建子表相关的数据时,当前数据为空
        if (dbTableRowInfo == null) {
            return;
        }
        Iterator<Map.Entry<String, SimpleChildTableGroup>> it = simpleChildTableCallableTaskDependency.entryTABLEIterator();
        while (it != null && it.hasNext()) {
            Map.Entry<String, SimpleChildTableGroup> entry = it.next();
            SimpleChildTableGroup simpleChildTableGroup = entry.getValue();

            simpleChildTableGroup.getRelatedValuesAndRowIndexGroup().addDBTableRowInfo(this.tableLoadDefine, dbTableRowInfo);
        }
    }

    private void addRowData2LaxyTmp(SimpleChildTableLazyCallableTaskDependency simpleChildTableLazyCallableTaskDependency, DBTableRowInfo dbTableRowInfo) {
        //当最后一批数据加载完成后,创建子表相关的数据时,当前数据为空
        if (dbTableRowInfo == null) {
            return;
        }
        Iterator<Map.Entry<String, SimpleLazyChildTableGroup>> it = simpleChildTableLazyCallableTaskDependency.entryTABLEIterator();
        while (it != null && it.hasNext()) {
            Map.Entry<String, SimpleLazyChildTableGroup> entry = it.next();
            SimpleLazyChildTableGroup simpleLazyChildTableGroup = entry.getValue();

            simpleLazyChildTableGroup.getLazyRelatedFieldsAndRowIndexGroup().addDBTableRowInfo(dbTableRowInfo);
        }
    }

    private void creatBatchChildTableLoadTask(CallableRelatedTaskDependency callableRelatedTaskDependency) {
        EnumTableType enumTableType = callableRelatedTaskDependency.getEnumTableType();
        switch (enumTableType) {
            case Child:
                creatBatchSimpleChildTableLoadTask((SimpleChildTableCallableTaskDependency) callableRelatedTaskDependency);
                break;
            case Lazy_subtable:
                creatBatchSimpleLazyChildTableLoadTask((SimpleChildTableLazyCallableTaskDependency) callableRelatedTaskDependency);
                break;
            case Main:
                break;
            default:
                throw new RuntimeException("Error EnumTableType " + enumTableType);
        }
    }

    private void creatBatchSimpleChildTableLoadTask(SimpleChildTableCallableTaskDependency simpleChildTableCallableTaskDependency) {
        Iterator<Map.Entry<String, SimpleChildTableGroup>> it = simpleChildTableCallableTaskDependency.entryTABLEIterator();
        while (it != null && it.hasNext()) {
            Map.Entry<String, SimpleChildTableGroup> entry = it.next();
            SimpleChildTableGroup simpleChildTableGroup = entry.getValue();

            RelatedValuesAndRowIndexGroup relatedValuesAndRowIndexGroup = simpleChildTableGroup.getRelatedValuesAndRowIndexGroup();
            RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity = relatedValuesAndRowIndexGroup.readAndClear();

            List<SimpleChildTable> simpleChildTables = simpleChildTableGroup.getSimpleChildTableList();
            for (SimpleChildTable simpleChildTable : simpleChildTables) {
                ChildTableConfigCacheEntity childTableConfigCacheEntity = null;
                if (simpleChildTable.isConfigCache()) {
                    childTableConfigCacheEntity = loaderPrefetchChildTableDataMap.get(simpleChildTable.getIdentify());
                }
                String childTaskId = this.tableLoadDefine.getDefineType() + "-" + simpleChildTable.getTableAlias() + "-" + this.taskId.getAndIncrement();
                SimpleChildTableLoaderTask iTableLoaderTask = new SimpleChildTableLoaderTask(childTaskId, this.relatedLoaderHandlerHolder, this.tableLoadDefine, simpleChildTable, childTableConfigCacheEntity, relatedValuesAndRowIndexEntity);
                if (iTableLoaderTask != null) {
                    simpleChildTableCallableTaskDependency.getCurrentCallableRelatedTask().add(iTableLoaderTask);
                }
            }
        }
    }

    private void creatBatchSimpleLazyChildTableLoadTask(SimpleChildTableLazyCallableTaskDependency simpleChildTableLazyCallableTaskDependency) {
        Iterator<Map.Entry<String, SimpleLazyChildTableGroup>> it = simpleChildTableLazyCallableTaskDependency.entryTABLEIterator();
        while (it != null && it.hasNext()) {
            Map.Entry<String, SimpleLazyChildTableGroup> entry = it.next();
            SimpleLazyChildTableGroup simpleLazyChildTableGroup = entry.getValue();

            LazyRelatedFieldsAndRowIndexGroup lazyRelatedFieldsAndRowIndexGroup = simpleLazyChildTableGroup.getLazyRelatedFieldsAndRowIndexGroup();
            LazyRelatedFieldsAndRowIndex lazyRelatedFieldsAndRowIndex = lazyRelatedFieldsAndRowIndexGroup.readAndClear();

            List<SimpleChildTable> simpleChildTables = simpleLazyChildTableGroup.getSimpleChildTableList();
            for (SimpleChildTable simpleChildTable : simpleChildTables) {
                ChildTableConfigCacheEntity childTableConfigCacheEntity = null;
                if (simpleChildTable.isConfigCache()) {
                    childTableConfigCacheEntity = loaderPrefetchChildTableDataMap.get(simpleChildTable.getIdentify());
                }
                String childTaskId = this.tableLoadDefine.getDefineType() + "-lazy-" + simpleChildTable.getTableAlias() + "-" + this.taskId.getAndIncrement();
                SimpleChildTableLazyLoaderTask iTableLoaderTask = new SimpleChildTableLazyLoaderTask(childTaskId, this.relatedLoaderHandlerHolder, this.tableLoadDefine, simpleChildTable, childTableConfigCacheEntity, lazyRelatedFieldsAndRowIndex);
                if (iTableLoaderTask != null) {
                    simpleChildTableLazyCallableTaskDependency.getCurrentCallableRelatedTask().add(iTableLoaderTask);
                }
            }
        }
    }

    /**
     * 分析子表中需要预先加载的表,并将其数据缓存在内存中
     */
    private void loaderPrefetchChildTableData() throws PoolExcuteException {
        List<SimpleChildTable> prefetchChildTableList = simpleMainTableCallableTaskDependency.getPrefetchChildTableList();
        if (CollectionUtils.isEmpty(prefetchChildTableList)) {
            return;
        }
        List<Callable<TableLoadResult>> prefectCacheTaskList = new ArrayList<>();
        for (Map.Entry<String, ChildTableConfigCacheEntity> entity : loaderPrefetchChildTableDataMap.entrySet()) {
            ChildTableConfigCacheEntity childTableConfigCacheEntity = entity.getValue();
            SimpleChildTableBeforeLoaderCacheTask prefectCacheTask = new SimpleChildTableBeforeLoaderCacheTask(relatedLoaderHandlerHolder.getiDataSourceProvider(), childTableConfigCacheEntity);
            prefectCacheTaskList.add(prefectCacheTask);
        }
        if (prefectCacheTaskList.size() > 0) {
            relatedLoaderHandlerHolder.getiThreadPoolProvider().executBySync(null, prefectCacheTaskList);
        }
        prefectCacheTaskList.clear();
    }

    private void executeRecursionDependencyChildTableLoadTask(CallableRelatedTaskDependency currentCallableRelatedTaskDependency) {
        if (currentCallableRelatedTaskDependency == null) {
            return;
        }
        if (currentCallableRelatedTaskDependency.hasCurrentCallableRelatedTask()) {
            try {
                relatedLoaderHandlerHolder.getiThreadPoolProvider().executBySync(null, currentCallableRelatedTaskDependency.getCurrentCallableRelatedTask());
                logger.info(String.format("%s %s executeRecursionDependencyChildTableLoadTask", this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), this.listenerIdentification()));
            } catch (Exception e) {
                logger.error("executeRecursionDependencyChildTableLoadTask", e);
            }
        }

        if (currentCallableRelatedTaskDependency.hasNext()) {
            executeRecursionDependencyChildTableLoadTask(currentCallableRelatedTaskDependency.getNext());
        }
    }


    private void clearTmp() {
        if (relatedLoaderHandlerHolder.getiThreadPoolProvider() != null) {
            relatedLoaderHandlerHolder.getiThreadPoolProvider().shutdown(null);
        }
        if (loaderPrefetchChildTableDataMap != null) {
            for (Map.Entry<String, ChildTableConfigCacheEntity> entry : loaderPrefetchChildTableDataMap.entrySet()) {
                if (entry.getValue() != null) {
                    entry.getValue().destory();
                }
            }
            loaderPrefetchChildTableDataMap.clear();
        }
        loaderPrefetchChildTableDataMap = null;
    }
}
