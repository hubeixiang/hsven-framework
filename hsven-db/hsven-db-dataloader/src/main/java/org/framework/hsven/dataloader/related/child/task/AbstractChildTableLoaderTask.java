package org.framework.hsven.dataloader.related.child.task;

import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.beans.dependency.StructSql;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedField;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedFields;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedValue;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedValueUtil;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedValues;
import org.framework.hsven.dataloader.beans.loader.LazyRelatedFieldsAndRowIndex;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndex;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndexEntity;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.ITableLoaderTask;
import org.framework.hsven.dataloader.related.RelatedLoaderHandlerHolder;
import org.framework.hsven.dataloader.related.TableLoadResult;
import org.framework.hsven.dataloader.related.child.ChildTableConfigCacheEntity;
import org.framework.hsven.dataloader.valid.related.SimpleChildTableUtil;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractChildTableLoaderTask implements ITableLoaderTask<TableLoadResult> {
    private static Logger logger = LoggerFactory.getLogger(AbstractChildTableLoaderTask.class);
    protected final String taskId;
    protected final RelatedLoaderHandlerHolder relatedLoaderHandlerHolder;
    protected final TableLoadDefine tableLoadDefine;
    protected final SimpleChildTable currentsimpleChildTable;
    protected final ChildTableConfigCacheEntity childTableConfigCacheEntity;
    protected long taskCreateTimeMS = System.currentTimeMillis();
    protected long taskDealBeginTimeMS;
    protected long taskDealEndTimeMS;

    protected AbstractChildTableLoaderTask(String taskId, RelatedLoaderHandlerHolder relatedLoaderHandlerHolder, TableLoadDefine tableLoadDefine, SimpleChildTable currentsimpleChildTable, ChildTableConfigCacheEntity childTableConfigCacheEntity) {
        this.taskId = taskId;
        this.relatedLoaderHandlerHolder = relatedLoaderHandlerHolder;
        this.tableLoadDefine = tableLoadDefine;
        this.currentsimpleChildTable = currentsimpleChildTable;
        this.childTableConfigCacheEntity = childTableConfigCacheEntity;
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
            queryLoaderResultDesc = startChildTableLoader();
            if (queryLoaderResultDesc != null) {
                resultIndex = queryLoaderResultDesc.getResultIndex();
            }
            flag = true;
        } catch (Exception e) {
            logger.error(String.format("%s AbstractChildTableLoaderTask,defineType:%s,Exception:%s,taskId:[%s],sql:[%s]", relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), tableLoadDefine.getDefineType(), e.getMessage(), taskId, sql), e);
        } catch (Throwable e) {
            logger.error(String.format("%s AbstractChildTableLoaderTask,defineType:%s,Throwable:%s,taskId:[%s],sql:[%s]", relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), tableLoadDefine.getDefineType(), e.getMessage(), taskId, sql), e);
        } finally {
            taskDealEndTimeMS = System.currentTimeMillis();
            dealUsingTimeMS = this.taskDealEndTimeMS - taskDealBeginTimeMS;
            if (flag) {
                logger.info(String.format("%s AbstractChildTableLoaderTask dealEnd,defineType:%s,dealUsingTimeMS:%s,resultIndex:%s,taskId:[%s],sql:[%s]", relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), tableLoadDefine.getDefineType(), dealUsingTimeMS, resultIndex, taskId, sql));
            } else {
                logger.error(String.format("%s AbstractChildTableLoaderTask dealFailure,defineType:%s,dealUsingTimeMS:%s,resultIndex:%s,taskId:[%s],sql:[%s]", relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), tableLoadDefine.getDefineType(), dealUsingTimeMS, resultIndex, taskId, sql));
            }
        }

        TableLoadResult tableLoadResult = new TableLoadResult();
        tableLoadResult.setTaskId(taskId);
        tableLoadResult.setTaskCreateTimeMS(this.taskCreateTimeMS);
        tableLoadResult.setDealResult(flag);
        tableLoadResult.setQueryLoaderResultDesc(queryLoaderResultDesc);
        tableLoadResult.setDealUsingTimeMS(dealUsingTimeMS);
        return tableLoadResult;
    }

    protected QueryLoaderResultDesc startChildTableLoader() {
        if (currentsimpleChildTable.isConfigCache()) {
            return startChildTableLoaderByPrefetchCache();
        } else {
            return startChildTableLoaderByDatabase();
        }
    }

    protected abstract QueryLoaderResultDesc startChildTableLoaderByDatabase();

    protected abstract QueryLoaderResultDesc startChildTableLoaderByPrefetchCache();

    /**
     * 依据提供的当前要关联的条件数据,拼接实时查询数据库的sql语句
     *
     * @param currentRelatedValuesAndRowIndexEntity
     * @return
     */
    protected QueryConfig createQueryConfig(RelatedValuesAndRowIndexEntity currentRelatedValuesAndRowIndexEntity) {
        QueryConfig queryConfig = new QueryConfig();
        DataSourceType dataSourceType = relatedLoaderHandlerHolder.getiDataSourceProvider().getDataSourceType(currentsimpleChildTable.getTableDefine().getDbName());
        StructSql structSql = SimpleChildTableUtil.structSimpleSql(dataSourceType, tableLoadDefine, currentsimpleChildTable, currentRelatedValuesAndRowIndexEntity);
        if (!structSql.hasSql()) {
            throw new RuntimeException(String.format("%s SimpleChildTableLoaderTask taskId=%s,defineType:%s,can't struct sql", this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), taskId, tableLoadDefine.getDefineType()));
        }
        queryConfig.setDbName(currentsimpleChildTable.getTableDefine().getDbName());
        queryConfig.setSql(structSql.getWholeSql());
        queryConfig.setSqlName(currentsimpleChildTable.getIdentify());
        queryConfig.setFetchSize(10000);
        queryConfig.setQueryTimeoutSeconds(currentsimpleChildTable.getTableDefine().getQueryTimeoutSeconds());
        return queryConfig;
    }

    /**
     * 间接关联主表时,需要临时查询主表已经缓存好的结果集中的关联值与关联的行号
     * 转换位为RelatedValuesAndRowIndexEntity的原因是为了方便进行当前子表的数据的关联查询,或者与预先查询缓存的子表中的数据进行关联
     *
     * @param lazyRelatedFieldsAndRowIndex 当前需要关联的字段与需要关联处理的行号
     * @return
     */
    protected RelatedValuesAndRowIndexEntity createLazyRelatedValuesAndRowIndexEntity(LazyRelatedFieldsAndRowIndex lazyRelatedFieldsAndRowIndex) {
        DefineRelatedFields defineRelatedFields = lazyRelatedFieldsAndRowIndex.getDefineRelatedFields();
        List<Integer> relatedTableRowIndexList = lazyRelatedFieldsAndRowIndex.getRelatedRowIndexList();
        //将值查询出来生成mainTableField_mainTableFieldValueSet_map对象,在调用此对象进行后续处理
        RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity = null;
        if (!CollectionUtils.isEmpty(relatedTableRowIndexList)) {
            relatedValuesAndRowIndexEntity = new RelatedValuesAndRowIndexEntity();
            for (Integer rowIndex : relatedTableRowIndexList) {
                DefineRelatedValues defineRelatedValues = new DefineRelatedValues();
                for (DefineRelatedField defineRelatedField : defineRelatedFields.getDefineRelatedFieldList()) {
                    Object fieldValue = relatedLoaderHandlerHolder.getiRelatedTableLoadListener().getFieldValue(rowIndex, defineRelatedField.getRelatedField());
                    DefineRelatedValue defineRelatedValue = DefineRelatedValueUtil.createDefineRelatedValue(defineRelatedField, fieldValue);
                    defineRelatedValues.addTableRelatedFieldDefine(defineRelatedValue);
                }
                if (defineRelatedValues.isValuesIsNull()) {
                    continue;
                }
                relatedValuesAndRowIndexEntity.addDefineRelatedValues(defineRelatedFields, defineRelatedValues, rowIndex);
            }
        }

        return relatedValuesAndRowIndexEntity;
    }

    /**
     * 从预先缓存的值中找出与当前关联值相关的子表数据,并将此数据逐行添加到主表数据中
     *
     * @param relatedValuesAndRowIndexEntity
     */
    protected QueryLoaderResultDesc loadPrefetchCacheDataAppend2MainData(RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity) {
        QueryLoaderResultDesc queryLoaderResultDesc = new QueryLoaderResultDesc();
        queryLoaderResultDesc.setSqlName("AbstractChildTableLoaderTask.loadPrefetchCacheDataAppend2MainData");
        queryLoaderResultDesc.setSql(currentsimpleChildTable.getIdentify());
        if (childTableConfigCacheEntity == null) {
            return queryLoaderResultDesc;
        }
        if (relatedValuesAndRowIndexEntity == null || !relatedValuesAndRowIndexEntity.hasRelatedValues()) {
            return queryLoaderResultDesc;
        }

        long resultIndex = 0;
        Iterator<Map.Entry<String, RelatedValuesAndRowIndex>> it = relatedValuesAndRowIndexEntity.entryRelatedValuesAndRowIndex();
        while (it != null && it.hasNext()) {
            RelatedValuesAndRowIndex relatedValuesAndRowIndex = it.next().getValue();
            DefineRelatedValues defineRelatedValues = relatedValuesAndRowIndex.getDefineRelatedValues();
            DBTableRowInfo dbTableRowInfo = childTableConfigCacheEntity.findRelatedDataBaseTableRow(defineRelatedValues.getRelatedFieldsKeyValue());
            if (dbTableRowInfo == null) {
                continue;
            }
            resultIndex++;
            List<Integer> mainTableRowIndexList = relatedValuesAndRowIndex.getRelatedRowIndexList();
            loadPrefetchCacheDataAppend2MainData(mainTableRowIndexList, dbTableRowInfo);
        }
        queryLoaderResultDesc.setResultIndex(resultIndex);
        return queryLoaderResultDesc;
    }

    private void loadPrefetchCacheDataAppend2MainData(List<Integer> mainTableRowIndexList, DBTableRowInfo dbTableRowInfo) {
        if (dbTableRowInfo == null || !currentsimpleChildTable.hasTableField()) {
            return;
        }
        Collection<TableField> fieldSet = currentsimpleChildTable.getTableFieldSet().getFieldSet();

        int mainTableRowIndexListSize = 0;
        if (mainTableRowIndexList != null) {
            mainTableRowIndexListSize = mainTableRowIndexList.size();
        }
        for (int i = 0; i < mainTableRowIndexListSize; i++) {
            Integer rowIndex = null;
            try {
                rowIndex = mainTableRowIndexList.get(i);
                if (rowIndex == null) {
                    continue;
                }
                relatedLoaderHandlerHolder.getiRelatedTableLoadListener().loadRow(rowIndex, dbTableRowInfo, fieldSet);
            } catch (Exception e) {
                logger.error("SimpleChildTableLazyLoaderTask loadCacheChildDataTableRow Exception.mainTable rowIndex:" + rowIndex + ",fieldValue:" + dbTableRowInfo);
            } catch (Throwable e) {
                logger.error("SimpleChildTableLazyLoaderTask loadCacheChildDataTableRow Throwable.mainTable rowIndex:" + rowIndex + ",fieldValue:" + dbTableRowInfo);
            }
        }
    }
}
