package org.framework.hsven.dataloader.listener;

import org.framework.hsven.dataloader.api.IDBSqlQueryLoaderListener;
import org.framework.hsven.dataloader.api.IRelatedTableLoadListener;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.beans.db.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedValues;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedValuesUtil;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndex;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndexEntity;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.RelatedLoaderHandlerHolder;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * 关联加载主子表数据时,子表的单条数据的实时处理类(需要批量适时关联查询字表时使用)
 */
public class ChildTableLoaderListenerImpl implements IDBSqlQueryLoaderListener {
    private static Logger logger = LoggerFactory.getLogger(ChildTableLoaderListenerImpl.class);
    private final RelatedLoaderHandlerHolder relatedLoaderHandlerHolder;
    private final TableLoadDefine tableLoadDefine;
    private final SimpleChildTable currentSimpleChildTable;
    private final RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity;

    public ChildTableLoaderListenerImpl(RelatedLoaderHandlerHolder relatedLoaderHandlerHolder, TableLoadDefine tableLoadDefine, SimpleChildTable currentSimpleChildTable, RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity) {
        this.relatedLoaderHandlerHolder = relatedLoaderHandlerHolder;
        this.tableLoadDefine = tableLoadDefine;
        this.currentSimpleChildTable = currentSimpleChildTable;
        this.relatedValuesAndRowIndexEntity = relatedValuesAndRowIndexEntity;
    }

    @Override
    public String listenerIdentification() {
        return String.format("ChildTableLoader");
    }

    @Override
    public void init(DataSourceConfig dataSourceConfig, QueryConfig queryConfig) {

    }

    @Override
    public void loadBegin(DBColumnMetaDataDefine dbColumnMetaDataDefine) {

    }

    @Override
    public void processRow(DBTableRowInfo dbTableRowInfo) {
        DefineRelatedValues relatedValues = DefineRelatedValuesUtil.createTableDefineRelatedValuesByLocalField(currentSimpleChildTable.getDefineRelatedFields(), dbTableRowInfo);
        if (relatedValuesAndRowIndexEntity == null || !relatedValuesAndRowIndexEntity.hasRelatedValues()) {
            return;
        }
        if (relatedValues == null || relatedValues.isValuesIsNull()) {
            return;
        }
        RelatedValuesAndRowIndex relatedValuesAndRowIndex = relatedValuesAndRowIndexEntity.getRelatedValuesAndRowIndexByValuesKey(relatedValues.getRelatedFieldsKeyValue());
        if (relatedValuesAndRowIndex == null || !relatedValuesAndRowIndex.hasRelatedRowIndex()) {
            return;
        }
        appendChildDataTableRow2MainTable(relatedLoaderHandlerHolder.getiRelatedTableLoadListener(), relatedValuesAndRowIndex.getRelatedRowIndexList(), currentSimpleChildTable.getTableFieldSet(), dbTableRowInfo);
    }

    @Override
    public void loadEnd(QueryLoaderResultDesc queryLoaderResultDesc) {

    }

    /**
     * 将当前子表的本行数据,附加到主表数据上
     *
     * @param iRelatedTableLoadListener 关联数据存储处理程序
     * @param mainTableRowIndexList     主表已经关联上了子表的本地条数据
     * @param tableFieldSet             附加的子表相关字段到主表上
     * @param dbTableRowInfo            当前行数据
     */
    private void appendChildDataTableRow2MainTable(final IRelatedTableLoadListener iRelatedTableLoadListener, List<Integer> mainTableRowIndexList, TableFieldSet tableFieldSet, DBTableRowInfo dbTableRowInfo) {
        if (iRelatedTableLoadListener == null) {
            return;
        }
        if (mainTableRowIndexList == null || mainTableRowIndexList.size() == 0) {
            return;
        }
        if (tableFieldSet == null || !tableFieldSet.hasTableField()) {
            return;
        }

        if (dbTableRowInfo == null || dbTableRowInfo.size() == 0) {
            return;
        }
        Collection<TableField> fieldSet = tableFieldSet.getFieldSet();
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
                iRelatedTableLoadListener.loadRow(rowIndex, dbTableRowInfo, fieldSet);
            } catch (Exception e) {
                logger.error(String.format("%s appendChildDataTableRow2MainTable Exception.mainTable rowIndex:%s,fieldValue:%s", iRelatedTableLoadListener.relatedListenerIdentification(), rowIndex, dbTableRowInfo.getDbTableRow()));
            } catch (Throwable e) {
                logger.error(String.format("%s appendChildDataTableRow2MainTable Throwable.mainTable rowIndex:%s,fieldValue:%s", iRelatedTableLoadListener.relatedListenerIdentification(), rowIndex, dbTableRowInfo.getDbTableRow()));
            }
        }

    }
}
