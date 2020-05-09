package org.framework.hsven.dataloader.listener;

import org.framework.hsven.dataloader.api.IDBSqlQueryLoaderListener;
import org.framework.hsven.dataloader.beans.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.datasource.model.DataSourceConfig;

public class DefaultDBSqlQueryLoaderListener implements IDBSqlQueryLoaderListener {
    @Override
    public String listenerIdentification() {
        return "defualt_listener";
    }

    @Override
    public void init(DataSourceConfig dataSourceConfig, QueryConfig queryConfig) {
        System.out.println(dataSourceConfig);
    }

    @Override
    public void loadBegin(final DBColumnMetaDataDefine dbColumnMetaDataDefine) {
        System.out.println("pre:" + dbColumnMetaDataDefine);
    }

    @Override
    public void processRow(DBTableRowInfo dbTableRowInfo) {
        System.out.println(dbTableRowInfo);
    }

    @Override
    public void loadEnd(QueryLoaderResultDesc queryLoaderResultDesc) {
        System.out.println(queryLoaderResultDesc);
    }
}
