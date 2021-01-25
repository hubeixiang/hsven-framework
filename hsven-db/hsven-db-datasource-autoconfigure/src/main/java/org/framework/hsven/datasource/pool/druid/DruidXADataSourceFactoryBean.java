package org.framework.hsven.datasource.pool.druid;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.sql.SQLException;

import static org.springframework.util.Assert.notNull;

public class DruidXADataSourceFactoryBean implements FactoryBean<DruidXADataSource>, InitializingBean {
    private DruidXADataSource druidXADataSource;
    private DataSourceConfig dataSourceConfig;
    private DruidJdbcPoolConfig druidJdbcPoolConfig;

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    public DruidJdbcPoolConfig getDruidJdbcPoolConfig() {
        return druidJdbcPoolConfig;
    }

    public void setDruidJdbcPoolConfig(DruidJdbcPoolConfig druidJdbcPoolConfig) {
        this.druidJdbcPoolConfig = druidJdbcPoolConfig;
    }

    @Override
    public DruidXADataSource getObject() throws Exception {
        if (this.druidXADataSource == null) {
            afterPropertiesSet();
        }
        return this.druidXADataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return this.druidXADataSource == null ? DruidXADataSource.class : this.druidXADataSource.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(dataSourceConfig, "Property 'dataSource' is required");
        if (druidXADataSource == null) {
            druidXADataSource = new DruidXADataSource();
            initConnectionInfo(dataSourceConfig, druidXADataSource);
            initConnectionPoolConfig(dataSourceConfig, druidXADataSource, druidJdbcPoolConfig);
//            druidXADataSource.init();
        }
    }

    private void initConnectionInfo(DataSourceConfig dataBaseConfig, DruidXADataSource xaDataSource) {
        xaDataSource.setDriverClassName(dataBaseConfig.getDriver());
        xaDataSource.setUrl(dataBaseConfig.getUrl());
        xaDataSource.setUsername(dataBaseConfig.getUsername());
        xaDataSource.setPassword(dataBaseConfig.getPassword());
    }

    private void initConnectionPoolConfig(DataSourceConfig dataBaseConfig, DruidXADataSource xaDataSource,
                                          DruidJdbcPoolConfig druidJdbcPoolConfig) throws SQLException {
    }
}
