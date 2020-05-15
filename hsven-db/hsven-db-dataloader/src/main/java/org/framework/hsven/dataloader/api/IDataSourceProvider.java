package org.framework.hsven.dataloader.api;

import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.datasource.model.DataSourceConfig;

import javax.sql.DataSource;

/**
 * 数据加载固定流程中使用到的数据源获取接口,外部引用本模块需要实现的接口
 * 如果不实现,先使用默认的数据源提供实现(使用模块db-datasource-autoconfigure中的数据源加载方式)
 */
public interface IDataSourceProvider {
    public String getProviderName();

    public DataSourceType getDataSourceType(String dbName);

    public DataSourceConfig getDataSourceConfig(String dbName);

    public String getDataSourceBeanName(String dbName);

    public DataSource dataSourceProvider(String dbName);
}
