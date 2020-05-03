package org.framework.hsven.datasource.pool.c3p0;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.sql.SQLException;

import static org.springframework.util.Assert.notNull;

public class C3p0DataSourceFactoryBean implements FactoryBean<AtomikosNonXADataSourceBean>, InitializingBean {
	private AtomikosNonXADataSourceBean atomikosNonXADataSourceBean;
	private DataSourceConfig dataSourceConfig;
	private C3p0JdbcPoolConfig c3p0JdbcPoolConfig;

	public DataSourceConfig getDataSourceConfig() {
		return dataSourceConfig;
	}

	public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}

	public C3p0JdbcPoolConfig getC3p0JdbcPoolConfig() {
		return c3p0JdbcPoolConfig;
	}

	public void setC3p0JdbcPoolConfig(C3p0JdbcPoolConfig c3p0JdbcPoolConfig) {
		this.c3p0JdbcPoolConfig = c3p0JdbcPoolConfig;
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
			initConnectionPoolConfig(dataSourceConfig, atomikosNonXADataSourceBean, c3p0JdbcPoolConfig);
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
			C3p0JdbcPoolConfig c3p0JdbcPoolConfig) throws SQLException {
	}

}
