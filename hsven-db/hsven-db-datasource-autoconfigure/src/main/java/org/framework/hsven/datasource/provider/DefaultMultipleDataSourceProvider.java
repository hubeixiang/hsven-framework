package org.framework.hsven.datasource.provider;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.datasource.InternalDBContextHelper;
import org.framework.hsven.datasource.SpringDataSourceContextUtil;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.framework.hsven.datasource.util.DataSourceNameGenerator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Set;

/**
 * 使用本模块进行自动注解加载多数据源的数据库名与数据源对象映射转换服务
 */
@Service("defaultMultipleDataSourceProvider")
public class DefaultMultipleDataSourceProvider implements IDataSourceProvider {
    @Override
    public String getProviderName() {
        return "default_multiple_datasource_autoconfigure";
    }

    @Override
    public Set<String> listProviderDbNames() {
        return InternalDBContextHelper.getInstance().getDbNames();
    }

    @Override
    public DataSourceType getDataSourceType(String dbName) {
        DataSourceConfig dataSourceConfig = getDataSourceConfig(dbName);
        return dataSourceConfig == null ? null : dataSourceConfig.getDatasourceType();
    }

    @Override
    public DataSourceConfig getDataSourceConfig(String dbName) {
        return InternalDBContextHelper.getInstance().getDataSourceConfig(dbName);
    }

    @Override
    public String getDataSourceBeanName(String dbName) {
        return DataSourceNameGenerator.generatorDataSourceRelevantBeanName(dbName);
    }


    @Override
    public AdapterDataSource dataSourceProvider(String dbName) {
        String dataSourceBeanName = getDataSourceBeanName(dbName);
        DataSourceConfig dataSourceConfig = getDataSourceConfig(dbName);
        Object object = SpringDataSourceContextUtil.getBean(dataSourceBeanName);
        if (object == null) {
            return null;
        } else {
            if (object instanceof javax.sql.DataSource) {
                return createAdapterDataSource(dataSourceBeanName, dataSourceConfig, (DataSource) object);
            } else {
                throw new RuntimeException(String.format("dbName=%s,dataSourceBeanName=%s SpringDataSourceContextUtil.getBean result object not instanceof javax.sql.DataSource", dbName, dataSourceBeanName));
            }
        }
    }

    @Override
    public <T> T getDataSourceRelevantBean(String dbName, Class<T> classzz) {
        String dataSourceBeanName = getDataSourceBeanName(dbName);
        if (StringUtils.isNotEmpty(dataSourceBeanName)) {
            return SpringDataSourceContextUtil.getDataSourceRelevant(dbName, classzz);
        } else {
            throw new RuntimeException(String.format("dbName=%s,dataSourceBeanName=%s SpringDataSourceContextUtil.getBean result object not instanceof javax.sql.DataSource", dbName, dataSourceBeanName));
        }
    }
}
