package org.framework.hsven.dataloader.listener;

import org.framework.hsven.dataloader.api.IDBSqlQueryLoaderListener;
import org.framework.hsven.dataloader.beans.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.datasource.model.DataSourceConfig;

/**
 * 关联加载主子表数据时,子表的单条数据的缓存处理类(先一次性查询数据,并将数据进行缓存,后面与主表关联时直接从缓存数据中获取并关联)
 */
public class ChildTableCacheLoaderListenerImpl implements IDBSqlQueryLoaderListener {
    @Override
    public String listenerIdentification() {
        return String.format("ChildTableLoader2Cache");
    }

    @Override
    public void init(DataSourceConfig dataSourceConfig, QueryConfig queryConfig) {

    }

    @Override
    public void loadBegin(DBColumnMetaDataDefine dbColumnMetaDataDefine) {

    }

    @Override
    public void processRow(DBTableRowInfo dbTableRowInfo) {

    }

    @Override
    public void loadEnd(QueryLoaderResultDesc queryLoaderResultDesc) {

    }
}
