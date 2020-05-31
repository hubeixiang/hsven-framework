package org.framework.hsven.datasource.autoconfigure;

import org.framework.hsven.datasource.InternalDBContextHelper;
import org.framework.hsven.datasource.connection.DatasourceLoaderNames;
import org.framework.hsven.datasource.connection.HDataSourceProperties;
import org.framework.hsven.datasource.constants.Constants;
import org.framework.hsven.datasource.enums.JdbcPoolTypeEnum;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.framework.hsven.datasource.pool.JdbcPoolConfig;
import org.framework.hsven.datasource.pool.atomikos.AtomikosJdbcPoolConfig;
import org.framework.hsven.datasource.pool.atomikos.AtomikosXADataSourceFactoryBean;
import org.framework.hsven.datasource.pool.c3p0.C3p0DataSourceFactoryBean;
import org.framework.hsven.datasource.pool.c3p0.C3p0JdbcPoolConfig;
import org.framework.hsven.datasource.pool.druid.DruidJdbcPoolConfig;
import org.framework.hsven.datasource.util.DataSourceNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Set;

public class DataSourcesBeanRegistryFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private Logger logger = LoggerFactory.getLogger(DataSourcesBeanRegistryFactoryPostProcessor.class);

    private ConfigurableEnvironment environment;
    private ApplicationDataSource applicationDataSource;
    private BeanDefinitionRegistry registry;

    public DataSourcesBeanRegistryFactoryPostProcessor(ConfigurableEnvironment environment, ApplicationDataSource applicationDataSource) {
        this.environment = environment;
        this.applicationDataSource = applicationDataSource;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;

        if (applicationDataSource != null && applicationDataSource.isValid()) {
            createDataSourceBeans();
        } else {
            logger.debug("No custom related data source configured");
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    private void createDataSourceBeans() {
        DatasourceLoaderNames datasourceLoaderNames = applicationDataSource.getDatasourceLoaderNames();
        JdbcPoolTypeEnum poolType = datasourceLoaderNames.getPoolType();
        Set<String> dbNames = datasourceLoaderNames.dbNames();

        HDataSourceProperties hDataSourceProperties = applicationDataSource.gethDataSourceProperties();
        if (hDataSourceProperties == null) {
            logger.error(String.format("data source properties %s prefix application not exist!", Constants.DATA_SOURCE_PREFIX));
            return;
        }
        JdbcPoolConfig jdbcPoolConfig = applicationDataSource.getJdbcPoolConfig();
        String currentDbName = null;
        boolean hasJdbcPool = false;
        try {
            boolean primary = true;
            for (String dbName : dbNames) {
                currentDbName = dbName;
                if (hDataSourceProperties.getDb().containsKey(dbName)) {
                    boolean ok = false;
                    DataSourceConfig dataSourceConfig = hDataSourceProperties.getDb().get(dbName);
                    dataSourceConfig.setName(dbName);
                    switch (poolType) {
                        case atomikos:
                            createAtomikosDataSourceBeans(primary, dbName, dataSourceConfig,
                                    jdbcPoolConfig == null ? null : ((AtomikosJdbcPoolConfig) jdbcPoolConfig));
                            primary = false;
                            ok = true;
                            break;
                        case c3p0:
                            createC3p0DataSourceBeans(primary, dbName, dataSourceConfig,
                                    jdbcPoolConfig == null ? null : ((C3p0JdbcPoolConfig) jdbcPoolConfig));
                            primary = false;
                            ok = true;
                            break;
                        case druid:
                            createDruidDataSourceBeans(primary, dbName, dataSourceConfig,
                                    jdbcPoolConfig == null ? null : ((DruidJdbcPoolConfig) jdbcPoolConfig));
                            primary = false;
                            ok = true;
                        default:
                            logger.error(String.format("custom data source pool type=%s not implements", poolType));
                            break;
                    }
                    if (ok) {
                        InternalDBContextHelper.getInstance().addDbName(dbName, dataSourceConfig);
                        if (!hasJdbcPool) {
                            InternalDBContextHelper.getInstance().setJdbcPoolConfig(jdbcPoolConfig);
                            hasJdbcPool = true;
                        }
                        //创建spring 的 JdbcTemplate
                        createSpringJdbcTemplate(dbName);
                    }
                } else {
                    logger.error(String.format("%s.%s data source application not exists!", Constants.DATA_SOURCE_PREFIX, dbName));
                }
            }
        } catch (Exception e) {
            logger.error(
                    String.format("%s.createDataSourceBeans(%s) Exception :%s", this.getClass().getName(), currentDbName, e.getMessage()),
                    e);
        }
    }

    private void createAtomikosDataSourceBeans(boolean primary, String dbName, DataSourceConfig dataSourceConfig,
                                               AtomikosJdbcPoolConfig jdbcPoolConfig) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AtomikosXADataSourceFactoryBean.class);
        builder.addPropertyValue("dataSourceConfig", dataSourceConfig);
        builder.addPropertyValue("atomikosJdbcPoolConfig", jdbcPoolConfig);

        final BeanDefinition definition = builder.getBeanDefinition();
        definition.setPrimary(primary);
        registry.registerBeanDefinition(DataSourceNameGenerator.generatorDataSourceRelevantBeanName(dbName, DataSource.class), definition);
    }

    private void createC3p0DataSourceBeans(boolean primary, String dbName, DataSourceConfig dataSourceConfig,
                                           C3p0JdbcPoolConfig jdbcPoolConfig) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(C3p0DataSourceFactoryBean.class);
        builder.addPropertyValue("dataSourceConfig", dataSourceConfig);
        builder.addPropertyValue("atomikosJdbcPoolConfig", jdbcPoolConfig);

        final BeanDefinition definition = builder.getBeanDefinition();
        definition.setPrimary(primary);
        registry.registerBeanDefinition(DataSourceNameGenerator.generatorDataSourceRelevantBeanName(dbName, DataSource.class), definition);
    }

    private void createDruidDataSourceBeans(boolean primary, String dbName, DataSourceConfig dataSourceConfig,
                                            DruidJdbcPoolConfig jdbcPoolConfig) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AtomikosXADataSourceFactoryBean.class);
        builder.addPropertyValue("dataSourceConfig", dataSourceConfig);
        builder.addPropertyValue("atomikosJdbcPoolConfig", jdbcPoolConfig);

        final BeanDefinition definition = builder.getBeanDefinition();
        definition.setPrimary(primary);
        registry.registerBeanDefinition(DataSourceNameGenerator.generatorDataSourceRelevantBeanName(dbName, DataSource.class), definition);
    }

    private void createSpringJdbcTemplate(String dbName) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(JdbcTemplate.class);
        builder.addConstructorArgReference(DataSourceNameGenerator.generatorDataSourceRelevantBeanName(dbName, DataSource.class));

        registry.registerBeanDefinition(DataSourceNameGenerator.generatorDataSourceRelevantBeanName(dbName, JdbcTemplate.class), builder.getBeanDefinition());
    }
}
