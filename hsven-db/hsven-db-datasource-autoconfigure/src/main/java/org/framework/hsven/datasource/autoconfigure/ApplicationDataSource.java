package org.framework.hsven.datasource.autoconfigure;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.datasource.connection.DatasourceLoaderNames;
import org.framework.hsven.datasource.connection.HDataSourceProperties;
import org.framework.hsven.datasource.pool.JdbcPoolConfig;

/**
 * @author sven
 * @date 2019/12/2 15:37
 */
public class ApplicationDataSource {
	private DatasourceLoaderNames datasourceLoaderNames;

	private HDataSourceProperties hDataSourceProperties;

	private JdbcPoolConfig jdbcPoolConfig;

	public ApplicationDataSource(DatasourceLoaderNames datasourceLoaderNames, HDataSourceProperties hDataSourceProperties,
			JdbcPoolConfig jdbcPoolConfig) {
		this.datasourceLoaderNames = datasourceLoaderNames;
		this.hDataSourceProperties = hDataSourceProperties;
		this.jdbcPoolConfig = jdbcPoolConfig;
	}

	public DatasourceLoaderNames getDatasourceLoaderNames() {
		return datasourceLoaderNames;
	}

	public HDataSourceProperties gethDataSourceProperties() {
		return hDataSourceProperties;
	}

	public JdbcPoolConfig getJdbcPoolConfig() {
		return jdbcPoolConfig;
	}

	public boolean isValid() {
		if (datasourceLoaderNames != null && StringUtils.isNotEmpty(datasourceLoaderNames.getNames())
				&& datasourceLoaderNames.getPoolType() != null) {
			if (hDataSourceProperties != null && hDataSourceProperties.getDb() != null && hDataSourceProperties.getDb().size() > 0) {
				return true;
			}
		}
		return false;
	}
}
