package org.framework.hsven.datasource.provider;

import org.framework.hsven.datasource.model.DataSourceConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据源适配实体
 */
public class AdapterDataSource {
    private DataSourceConfig dataSourceConfig;
    private String dataSourceBeanName;
    private DataSource dataSource;

    /**
     * 数据源是否可用
     *
     * @return
     */
    public boolean isEnable() {
        return dataSource != null;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    public String getDataSourceBeanName() {
        return dataSourceBeanName;
    }

    public void setDataSourceBeanName(String dataSourceBeanName) {
        this.dataSourceBeanName = dataSourceBeanName;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    public Connection getConnection(String username, String password)
            throws SQLException {
        return this.dataSource.getConnection(username, password);
    }
}
