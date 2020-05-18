package org.framework.hsven.dataloader.listener;

import org.framework.hsven.dataloader.api.IDBSqlQueryLoaderListener;
import org.framework.hsven.dataloader.beans.db.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.child.ChildTableConfigCacheEntity;
import org.framework.hsven.dataloader.related.child.task.SimpleChildTableBeforeLoaderCacheTask;
import org.framework.hsven.datasource.model.DataSourceConfig;

/**
 * 需要需要将子表数据全部查询完,并进行全部缓存的任务,对应的加载每行数据的处理方式
 *
 * @see SimpleChildTableBeforeLoaderCacheTask
 */
public class SimpleChildTableBeforeLoaderCacheListenerImpl implements IDBSqlQueryLoaderListener {
    private final ChildTableConfigCacheEntity childTableConfigCacheEntity;

    public SimpleChildTableBeforeLoaderCacheListenerImpl(ChildTableConfigCacheEntity childTableConfigCacheEntity) {
        this.childTableConfigCacheEntity = childTableConfigCacheEntity;
    }

    @Override
    public String listenerIdentification() {
        return "ChildTableLoader2Cache";
    }

    @Override
    public void init(DataSourceConfig dataSourceConfig, QueryConfig queryConfig) {

    }

    @Override
    public void loadBegin(DBColumnMetaDataDefine dbColumnMetaDataDefine) {

    }

    @Override
    public void processRow(DBTableRowInfo dbTableRowInfo) {
        childTableConfigCacheEntity.cacheDataBaseTableRow(dbTableRowInfo);
    }

    @Override
    public void loadEnd(QueryLoaderResultDesc queryLoaderResultDesc) {

    }
}
