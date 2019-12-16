package org.framework.hsven.datasource.pool.atomikos;

import org.framework.hsven.datasource.constants.Constants;
import org.framework.hsven.datasource.pool.JdbcPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = Constants.JDBC_POOL_ATOMIKOS_PREFIX)
public class AtomikosJdbcPoolConfig implements JdbcPoolConfig {
	// <property name="poolSize" value="10" />
	private int poolSize = 10;
	// <property name="minPoolSize" value="10" />
	private int minPoolSize = 10;
	// <property name="maxPoolSize" value="30" />
	private int maxPoolSize = 30;
	// <property name="borrowConnectionTimeout" value="60" />
	private int borrowConnectionTimeout = 60;
	// <property name="reapTimeout" value="20" />
	// 最大获取数据时间，如果不设置这个值，Atomikos使用默认的5分钟，那么在处理大批量数据读取的时候，一旦超过5分钟，就会抛出类似
	// Resultset is close 的错误.
	private int reapTimeout = 0;
	// <property name="maxIdleTime" value="60" />
	private int maxIdleTime = 120;
	// <property name="maintenanceInterval" value="60" />
	// <maintenance-interval>60</maintenance-interval> <!--连接回收时间-->
	private int maintenanceInterval = 120;
	// <property name="loginTimeout" value="60" />
	// <login-timeout>0</login-timeout>
	// <!--java数据库连接池，最大可等待获取datasouce的时间-->
	private int loginTimeout = 60;
	// <property name="testQuery">
	private String testQuery = "select 1 as con from dual";
	// Sets the maximum amount of seconds that a connection is kept in the pool
	// before it is destroyed automatically.
	// Optional, defaults to 0 (no limit).
	private int maxLifetime = 0;

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getBorrowConnectionTimeout() {
		return borrowConnectionTimeout;
	}

	public void setBorrowConnectionTimeout(int borrowConnectionTimeout) {
		this.borrowConnectionTimeout = borrowConnectionTimeout;
	}

	public int getReapTimeout() {
		return reapTimeout;
	}

	public void setReapTimeout(int reapTimeout) {
		this.reapTimeout = reapTimeout;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getMaintenanceInterval() {
		return maintenanceInterval;
	}

	public void setMaintenanceInterval(int maintenanceInterval) {
		this.maintenanceInterval = maintenanceInterval;
	}

	public int getLoginTimeout() {
		return loginTimeout;
	}

	public void setLoginTimeout(int loginTimeout) {
		this.loginTimeout = loginTimeout;
	}

	public String getTestQuery() {
		return testQuery;
	}

	public void setTestQuery(String testQuery) {
		this.testQuery = testQuery;
	}

	public int getMaxLifetime() {
		return maxLifetime;
	}

	public void setMaxLifetime(int maxLifetime) {
		this.maxLifetime = maxLifetime;
	}
}
