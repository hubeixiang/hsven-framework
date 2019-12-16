package org.framework.hsven.datasource.pool.atomikos;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import org.framework.hsven.datasource.connection.DataSourceConfig;
import org.framework.hsven.datasource.enums.DatabaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.util.Assert.notNull;

public class AtomikosXADataSourceFactoryBean implements FactoryBean<AtomikosNonXADataSourceBean>, InitializingBean {
	private Logger logger = LoggerFactory.getLogger(AtomikosXADataSourceFactoryBean.class);

	private AtomikosNonXADataSourceBean atomikosNonXADataSourceBean;
	private DataSourceConfig dataSourceConfig;
	private AtomikosJdbcPoolConfig atomikosJdbcPoolConfig;

	public DataSourceConfig getDataSourceConfig() {
		return dataSourceConfig;
	}

	public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}

	public AtomikosJdbcPoolConfig getAtomikosJdbcPoolConfig() {
		return atomikosJdbcPoolConfig;
	}

	public void setAtomikosJdbcPoolConfig(AtomikosJdbcPoolConfig atomikosJdbcPoolConfig) {
		this.atomikosJdbcPoolConfig = atomikosJdbcPoolConfig;
	}

	@Override
	public AtomikosNonXADataSourceBean getObject() throws Exception {
		if (this.atomikosNonXADataSourceBean == null) {
			afterPropertiesSet();
		}
		return this.atomikosNonXADataSourceBean;
	}

	@Override
	public Class<?> getObjectType() {
		return this.atomikosNonXADataSourceBean == null ? AtomikosNonXADataSourceBean.class : this.atomikosNonXADataSourceBean.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		notNull(dataSourceConfig, "Property 'dataSource' is required");
		if (atomikosNonXADataSourceBean == null) {
			//设置AtomikosEnvironment日志环境
			AtomikosEnvironment atomikosEnvironment = new AtomikosEnvironment();
			atomikosEnvironment.enviroment();
			atomikosNonXADataSourceBean = new AtomikosNonXADataSourceBean();
			initConnectionInfo(dataSourceConfig, atomikosNonXADataSourceBean);
			initConnectionPoolConfig(dataSourceConfig, atomikosNonXADataSourceBean, atomikosJdbcPoolConfig);
			atomikosNonXADataSourceBean.init();
		}
	}

	private void initConnectionInfo(DataSourceConfig dataBaseConfig, AtomikosNonXADataSourceBean atomikosNonXADataSourceBean) {
		// <property name="uniqueResourceName" value="dlDB" />
		atomikosNonXADataSourceBean.setUniqueResourceName(dataBaseConfig.getName());
		// <property name="driverClassName">
		// <value>${jdbc.driver}</value>
		// </property>
		atomikosNonXADataSourceBean.setDriverClassName(dataBaseConfig.getDriver());
		// <property name="url">
		// <value>${jdbc.url}</value>
		// </property>
		atomikosNonXADataSourceBean.setUrl(dataBaseConfig.getUrl());
		// <property name="user">
		// <value>${jdbc.username}</value>
		// </property>

		atomikosNonXADataSourceBean.setUser(dataBaseConfig.getUsername());
		// <property name="password">
		// <value>${jdbc.password}</value>
		// </property>
		atomikosNonXADataSourceBean.setPassword(dataBaseConfig.getPassword());
	}

	private void initConnectionPoolConfig(DataSourceConfig dataBaseConfig, AtomikosNonXADataSourceBean atomikosNonXADataSourceBean,
			AtomikosJdbcPoolConfig atomikosJdbcPoolConfig) throws SQLException {
		AtomikosJdbcPoolConfig jdbcPoolConfig = atomikosJdbcPoolConfig;
		if (jdbcPoolConfig == null) {
			jdbcPoolConfig = new AtomikosJdbcPoolConfig();
		}
		// <property name="poolSize" value="10" />
		atomikosNonXADataSourceBean.setPoolSize(jdbcPoolConfig.getPoolSize());
		// <property name="minPoolSize" value="10" />
		atomikosNonXADataSourceBean.setMinPoolSize(jdbcPoolConfig.getMinPoolSize());
		// <property name="maxPoolSize" value="30" />
		atomikosNonXADataSourceBean.setMaxPoolSize(jdbcPoolConfig.getMaxPoolSize());
		// <property name="borrowConnectionTimeout" value="60" />
		atomikosNonXADataSourceBean.setBorrowConnectionTimeout(jdbcPoolConfig.getBorrowConnectionTimeout());
		// <property name="reapTimeout" value="20" />
		atomikosNonXADataSourceBean.setReapTimeout(jdbcPoolConfig.getReapTimeout());
		// <!-- 最大空闲时间 -->
		// <property name="maxIdleTime" value="60" />
		atomikosNonXADataSourceBean.setMaxIdleTime(jdbcPoolConfig.getMaxIdleTime());
		// <property name="maintenanceInterval" value="60" />
		atomikosNonXADataSourceBean.setMaintenanceInterval(jdbcPoolConfig.getMaintenanceInterval());
		// <property name="loginTimeout" value="60" />
		atomikosNonXADataSourceBean.setLoginTimeout(jdbcPoolConfig.getLoginTimeout());
		// <property name="testQuery">
		// <value>select 1 from dual</value>
		// </property>
		atomikosNonXADataSourceBean.setTestQuery(jdbcPoolConfig.getTestQuery());
		// setTransactionIsolation
		// maxLifetime,defaults to 0 (no limit)
		atomikosNonXADataSourceBean.setMaxLifetime(jdbcPoolConfig.getMaxLifetime());
		atomikosNonXADataSourceBean.setBorrowConnectionTimeout(60);

		DatabaseType dataSourceType = DatabaseType.ORACLE;
		boolean isNeedSetTransaction = isNeedSetTransaction(dataSourceType);
		if (isNeedSetTransaction) {
			atomikosNonXADataSourceBean.setDefaultIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED);
		}
	}

	private boolean isNeedSetTransaction(DatabaseType dataSourceType) {
		boolean needSetTransaction = false;
		switch (dataSourceType) {

		case INFOMIX:
			needSetTransaction = true;
			break;

		case SYSBASE:
			needSetTransaction = true;
			break;
		default:
			needSetTransaction = false;
			break;
		}

		return needSetTransaction;
	}
}
