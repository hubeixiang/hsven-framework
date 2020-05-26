package org.framework.hsven.datasource.provider;

import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.datasource.model.DataSourceConfig;

import javax.sql.DataSource;
import java.util.Set;

/**
 * 多数据源中数据源获取接口
 */
public interface IDataSourceProvider {
    /**
     * 此数据源提供实现的名称
     *
     * @return
     */
    public String getProviderName();

    /**
     * 数据源
     *
     * @return
     */
    public Set<String> listProviderDbNames();

    /**
     * 获取对应的数据源类型
     *
     * @param dbName
     * @return
     */
    public DataSourceType getDataSourceType(String dbName);

    /**
     * 获取对应的数据源配置
     *
     * @param dbName
     * @return
     */
    public DataSourceConfig getDataSourceConfig(String dbName);

    /**
     * 获取对应的数据源在spring application中的Bean的名称
     *
     * @param dbName
     * @return
     */
    public String getDataSourceBeanName(String dbName);

    /**
     * 获取对应数据源的连接
     *
     * @param dbName
     * @return
     */
    public AdapterDataSource dataSourceProvider(String dbName);

    default AdapterDataSource createAdapterDataSource(String dataSourceBeanName, DataSourceConfig dataSourceConfig, DataSource dataSource) {
        AdapterDataSource adapterDataSource = new AdapterDataSource();
        adapterDataSource.setDataSourceBeanName(dataSourceBeanName);
        adapterDataSource.setDataSourceConfig(dataSourceConfig);
        adapterDataSource.setDataSource(dataSource);
        return adapterDataSource;
    }
}
