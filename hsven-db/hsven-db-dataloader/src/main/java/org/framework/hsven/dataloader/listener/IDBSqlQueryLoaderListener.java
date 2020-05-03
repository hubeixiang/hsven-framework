package org.framework.hsven.dataloader.listener;

import org.framework.hsven.dataloader.beans.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.data.DBTableRow;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.datasource.model.DataSourceConfig;

public interface IDBSqlQueryLoaderListener {
    /**
     * 标志是那个数据查询加载监听
     *
     * @return
     */
    public String listenerIdentification();

    public void init(DataSourceConfig dataSourceConfig, QueryConfig queryConfig);

    /**
     * 不得随意修改其中的内容
     *
     * @param dbColumnMetaDataDefine
     */
    public void loadBegin(final DBColumnMetaDataDefine dbColumnMetaDataDefine);

    public void processRow(final DBColumnMetaDataDefine dbColumnMetaDataDefine, DBTableRow dbTableRow);

    public void loadEnd(QueryLoaderResultDesc queryLoaderResultDesc);
}
