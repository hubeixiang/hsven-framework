package org.framework.hsven.datasource.autoconfigure;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import org.framework.hsven.datasource.connection.DatasourceLoaderNames;
import org.framework.hsven.datasource.connection.HDataSourceProperties;
import org.framework.hsven.datasource.enums.JdbcPoolTypeEnum;
import org.framework.hsven.datasource.pool.atomikos.AtomikosJdbcPoolConfig;
import org.framework.hsven.datasource.pool.c3p0.C3p0JdbcPoolConfig;
import org.framework.hsven.datasource.pool.druid.DruidJdbcPoolConfig;
import org.framework.hsven.datasource.util.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author sven
 * @date 2019/12/2 11:45
 */
@ConditionalOnClass(AtomikosNonXADataSourceBean.class)
@Configuration
public class HDataSourceAutoConfiguration {
	private static Logger logger = LoggerFactory.getLogger(HDataSourceAutoConfiguration.class);

	@Bean
	public static DataSourcesBeanRegistryFactoryPostProcessor dataSourcesBeanRegistryFactoryPostProcessor(
			ConfigurableEnvironment environment) {
		Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);// 设置Binder
		Binder binder = new Binder(sources);
		// 属性绑定
		ApplicationDataSource applicationDataSource = null;
		DatasourceLoaderNames datasourceLoaderNames = DataSourceUtil.binderOfProperties(binder, DatasourceLoaderNames.class);
		if (datasourceLoaderNames != null) {
			if (datasourceLoaderNames.getPoolType() == null) {
				//未填写数据库连接池默认使用druid
				datasourceLoaderNames.setPoolType(JdbcPoolTypeEnum.druid);
			}
			HDataSourceProperties hDataSourceProperties = DataSourceUtil.binderOfProperties(binder, HDataSourceProperties.class);
			JdbcPoolTypeEnum typeEnum = datasourceLoaderNames.getPoolType();
			switch (typeEnum) {
			case atomikos:
				AtomikosJdbcPoolConfig atomikosNonXaJdbcPoolConfig = DataSourceUtil
						.binderOfProperties(binder, AtomikosJdbcPoolConfig.class);
				applicationDataSource = new ApplicationDataSource(datasourceLoaderNames, hDataSourceProperties,
						atomikosNonXaJdbcPoolConfig);
				break;
			case c3p0:
				C3p0JdbcPoolConfig c3p0JdbcPoolConfig = DataSourceUtil.binderOfProperties(binder, C3p0JdbcPoolConfig.class);
				applicationDataSource = new ApplicationDataSource(datasourceLoaderNames, hDataSourceProperties, c3p0JdbcPoolConfig);
				break;
			case druid:
				DruidJdbcPoolConfig druidJdbcPoolConfig = DataSourceUtil.binderOfProperties(binder, DruidJdbcPoolConfig.class);
				applicationDataSource = new ApplicationDataSource(datasourceLoaderNames, hDataSourceProperties, druidJdbcPoolConfig);
				break;
			default:
				logger.error(String.format("custom data source pool type=%s not implements", datasourceLoaderNames.getPoolType()));
				applicationDataSource = null;
				break;
			}
		}
		return new DataSourcesBeanRegistryFactoryPostProcessor(environment, applicationDataSource);
	}
}
