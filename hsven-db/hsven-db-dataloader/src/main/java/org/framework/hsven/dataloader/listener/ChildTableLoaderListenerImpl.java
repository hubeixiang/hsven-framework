package org.framework.hsven.dataloader.listener;

import org.framework.hsven.dataloader.api.IDBSqlQueryLoaderListener;
import org.framework.hsven.dataloader.beans.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.datasource.model.DataSourceConfig;

/**
 * 关联加载主子表数据时,子表的单条数据的实时处理类
 */
public class ChildTableLoaderListenerImpl implements IDBSqlQueryLoaderListener {
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

    }

    @Override
    public void loadEnd(QueryLoaderResultDesc queryLoaderResultDesc) {

    }
}
