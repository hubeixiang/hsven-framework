package org.framework.hsven.dataloader.loader;

import org.framework.hsven.dataloader.api.IDataSourceProvider;
import org.framework.hsven.datasource.InternalDBContextHelper;
import org.framework.hsven.datasource.SpringDataSourceContextUtil;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.framework.hsven.datasource.util.DataSourceNameGenerator;

import javax.sql.DataSource;

public class DefaultDataSourceProvider implements IDataSourceProvider {
    @Override
    public String getProviderName() {
        return "default_datasource_autoconfigure";
    }

    @Override
    public DataSourceConfig getDataSourceConfig(String dbName) {
        return InternalDBContextHelper.getInstance().getDataSourceConfig(dbName);
    }

    @Override
    public String getDataSourceBeanName(String dbName) {
        return DataSourceNameGenerator.getDataSourceBeanName(dbName);
    }


    @Override
    public DataSource dataSourceProvider(String dbName) {
        String dataSourceBeanName = getDataSourceBeanName(dbName);
        Object object = SpringDataSourceContextUtil.getBean(dataSourceBeanName);
        if (object == null) {
            return null;
        } else {
            return (DataSource) object;
        }
    }
}
