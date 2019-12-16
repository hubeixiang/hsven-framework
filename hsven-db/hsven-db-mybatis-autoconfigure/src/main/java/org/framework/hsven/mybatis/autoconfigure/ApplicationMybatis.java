package org.framework.hsven.mybatis.autoconfigure;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.datasource.connection.DatasourceLoaderNames;
import org.framework.hsven.mybatis.config.MybatisProperties;

/**
 * @author sven
 * @date 2019/12/2 9:56
 */
public class ApplicationMybatis {
	private DatasourceLoaderNames datasourceLoaderNames;
	private MybatisProperties mybatisProperties;

	public ApplicationMybatis(DatasourceLoaderNames datasourceLoaderNames, MybatisProperties mybatisProperties) {
		this.datasourceLoaderNames = datasourceLoaderNames;
		this.mybatisProperties = mybatisProperties;
	}

	public DatasourceLoaderNames getDatasourceLoaderNames() {
		return datasourceLoaderNames;
	}

	public MybatisProperties getMybatisProperties() {
		return mybatisProperties;
	}

	public boolean isValid() {
		if (datasourceLoaderNames != null && StringUtils.isNotEmpty(datasourceLoaderNames.getNames())) {
			if (mybatisProperties != null && mybatisProperties.getDb() != null && mybatisProperties.getDb().size() > 0) {
				return true;
			}
		}
		return false;
	}
}
