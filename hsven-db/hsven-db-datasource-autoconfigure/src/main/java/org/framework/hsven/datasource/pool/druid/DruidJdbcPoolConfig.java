package org.framework.hsven.datasource.pool.druid;

import org.framework.hsven.datasource.constants.Constants;
import org.framework.hsven.datasource.pool.JdbcPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = Constants.JDBC_POOL_DRUID_PREFIX)
public class DruidJdbcPoolConfig implements JdbcPoolConfig {
}
