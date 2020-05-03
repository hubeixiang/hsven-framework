package org.framework.hsven.datasource.connection;

import org.framework.hsven.datasource.constants.Constants;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = Constants.DATA_SOURCE_PREFIX)
public class HDataSourceProperties {
	private Map<String, DataSourceConfig> db = new HashMap<>();

	public Map<String, DataSourceConfig> getDb() {
		return db;
	}

	public void setDb(Map<String, DataSourceConfig> db) {
		this.db = db;
	}
}
