package org.framework.hsven.mybatis.autoconfigure;

import org.framework.hsven.mybatis.config.MybatisConfig;
import org.framework.hsven.mybatis.name.MybatisBeanNameGenerator;
import org.framework.hsven.datasource.util.DataSourceNameGenerator;
import org.framework.hsven.mybatis.config.MybatisProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;
import java.util.Set;

public class MyBatisBeanRegistryFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	private Logger logger = LoggerFactory.getLogger(MyBatisBeanRegistryFactoryPostProcessor.class);

	private ConfigurableEnvironment environment;
	private ApplicationMybatis applicationMybatis;
	private BeanDefinitionRegistry registry;

	public MyBatisBeanRegistryFactoryPostProcessor(ConfigurableEnvironment environment, ApplicationMybatis applicationMybatis) {
		this.environment = environment;
		this.applicationMybatis = applicationMybatis;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		this.registry = registry;

		if (applicationMybatis != null && applicationMybatis.isValid()) {
			createMybatisRelatedBeans();
		} else {
			logger.debug("No custom mybatis related configured");
		}
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	private MybatisProperties getMybatisProperties() {
		return applicationMybatis.getMybatisProperties();
	}

	private void createMybatisRelatedBeans() {
		MybatisProperties mybatisProperties = getMybatisProperties();
		Set<String> dbNames = applicationMybatis.getDatasourceLoaderNames().dbNames();
		for (Map.Entry<String, MybatisConfig> entry : mybatisProperties.getDb().entrySet()) {
			String dbName = entry.getKey();
			if (dbNames.contains(dbName)) {
				MybatisConfig mybatisConfig = entry.getValue();
				if (mybatisConfig.getFactor().isValid()) {
					createSqlSessionFactoryBean(dbName, mybatisConfig);

					if (mybatisConfig.getScanner().isValid()) {
						createMapperScannerConfigurerBean(dbName, mybatisConfig);
					}
					createSqlSessionTemplate(dbName);
				}
			} else {
				logger.warn(String.format("mybatis config related db=%s,but this data source is not valid!", dbName));
			}
		}
	}

	private void createSqlSessionFactoryBean(String dbName, MybatisConfig config) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryBean.class);
		builder.addPropertyReference("dataSource", DataSourceNameGenerator.getDataSourceBeanName(dbName));
		builder.addPropertyValue("configLocation", config.getFactor().getConfigLocation());
		builder.addPropertyValue("mapperLocations", config.getFactor().getMapperLocations());

		registry.registerBeanDefinition(DataSourceNameGenerator.getSqlSessionFactoryBeanName(dbName), builder.getBeanDefinition());
	}

	private void createMapperScannerConfigurerBean(String dbName, MybatisConfig config) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
		builder.addPropertyValue("sqlSessionFactoryBeanName", DataSourceNameGenerator.getSqlSessionFactoryBeanName(dbName));
		builder.addPropertyValue("basePackage", config.getScanner().getBasePackage());
		builder.addPropertyValue("nameGenerator", new MybatisBeanNameGenerator(false, dbName));

		registry.registerBeanDefinition(DataSourceNameGenerator.getMapperScannerConfigurerBeanName(dbName), builder.getBeanDefinition());
	}

	private void createSqlSessionTemplate(String dbName) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionTemplate.class);
		builder.addConstructorArgReference(DataSourceNameGenerator.getSqlSessionFactoryBeanName(dbName));

		registry.registerBeanDefinition(DataSourceNameGenerator.getSqlSessionTemplate(dbName), builder.getBeanDefinition());
	}
}
