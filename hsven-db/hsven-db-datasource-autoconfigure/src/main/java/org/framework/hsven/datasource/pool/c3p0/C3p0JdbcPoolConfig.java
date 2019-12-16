package org.framework.hsven.datasource.pool.c3p0;

import org.framework.hsven.datasource.constants.Constants;
import org.framework.hsven.datasource.pool.JdbcPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = Constants.JDBC_POOL_C3P0_PREFIX)
public class C3p0JdbcPoolConfig implements JdbcPoolConfig {
}
