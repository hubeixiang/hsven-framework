package org.framework.hsven.datasource.pool.druid;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import org.framework.hsven.datasource.connection.DataSourceConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.sql.SQLException;

import static org.springframework.util.Assert.notNull;

public class DruidXADataSourceFactoryBean implements FactoryBean<AtomikosNonXADataSourceBean>, InitializingBean {
	private AtomikosNonXADataSourceBean atomikosNonXADataSourceBean;
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
			atomikosNonXADataSourceBean = new AtomikosNonXADataSourceBean();
			initConnectionInfo(dataSourceConfig, atomikosNonXADataSourceBean);
			initConnectionPoolConfig(dataSourceConfig, atomikosNonXADataSourceBean, druidJdbcPoolConfig);
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
			DruidJdbcPoolConfig druidJdbcPoolConfig) throws SQLException {
	}
}
