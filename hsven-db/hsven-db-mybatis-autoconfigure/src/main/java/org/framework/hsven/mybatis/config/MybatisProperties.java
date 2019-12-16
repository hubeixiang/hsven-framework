package org.framework.hsven.mybatis.config;

import org.framework.hsven.datasource.constants.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sven
 * @date 2019/12/2 10:18
 */
@ConfigurationProperties(prefix = Constants.MY_BATIS_PREFIX)
public class MybatisProperties {
	private Map<String, MybatisConfig> db = new HashMap<>();

	public Map<String, MybatisConfig> getDb() {
		return db;
	}

	public void setDb(Map<String, MybatisConfig> db) {
		this.db = db;
	}
}
