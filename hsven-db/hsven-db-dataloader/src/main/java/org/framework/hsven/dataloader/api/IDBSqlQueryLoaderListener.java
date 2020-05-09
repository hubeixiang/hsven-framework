package org.framework.hsven.dataloader.api;

import org.framework.hsven.dataloader.beans.DBColumnMetaDataDefine;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.datasource.model.DataSourceConfig;

/**
 * 单个sql的数据查询加载过程中,定义的各自必须要实现的数据处理接口类
 */
public interface IDBSqlQueryLoaderListener {
    /**
     * 标志是那个数据查询加载监听
     *
     * @return
     */
    public String listenerIdentification();

    /**
     * 创建完毕加载流程后,初始化加载流程时只需的监听初始化信息
     *
     * @param dataSourceConfig
     * @param queryConfig
     */
    public void init(DataSourceConfig dataSourceConfig, QueryConfig queryConfig);

    /**
     * 不得随意修改其中的内容
     *
     * @param dbColumnMetaDataDefine
     */
    public void loadBegin(final DBColumnMetaDataDefine dbColumnMetaDataDefine);

    public void processRow(final DBTableRowInfo dbTableRowInfo);

    public void loadEnd(QueryLoaderResultDesc queryLoaderResultDesc);
}
