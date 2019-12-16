package org.framework.hsven.mybatis.autoconfigure;

import org.framework.hsven.datasource.connection.DatasourceLoaderNames;
import org.framework.hsven.datasource.enums.JdbcPoolTypeEnum;
import org.framework.hsven.datasource.autoconfigure.HDataSourceAutoConfiguration;
import org.framework.hsven.datasource.util.DataSourceUtil;
import org.framework.hsven.mybatis.config.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author sven
 * @date 2019/12/2 11:44
 */
@Configuration
@AutoConfigureAfter({ HDataSourceAutoConfiguration.class })
public class MyBatisAutoConfiguration {
	private static Logger logger = LoggerFactory.getLogger(MyBatisAutoConfiguration.class);

	@Bean
	public static MyBatisBeanRegistryFactoryPostProcessor myBatisBeanRegistryFactoryPostProcessor(ConfigurableEnvironment environment) {
		Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);// 设置Binder
		Binder binder = new Binder(sources);
		// 属性绑定
		ApplicationMybatis applicationMybatis = null;
		DatasourceLoaderNames datasourceLoaderNames = DataSourceUtil.binderOfProperties(binder, DatasourceLoaderNames.class);
		if (datasourceLoaderNames != null) {
			if (datasourceLoaderNames.getPoolType() == null) {
				//未填写数据库连接池默认使用druid
				datasourceLoaderNames.setPoolType(JdbcPoolTypeEnum.druid);
			}
			MybatisProperties mybatisProperties = DataSourceUtil.binderOfProperties(binder, MybatisProperties.class);
			applicationMybatis = new ApplicationMybatis(datasourceLoaderNames, mybatisProperties);
		}
		return new MyBatisBeanRegistryFactoryPostProcessor(environment, applicationMybatis);
	}

}
