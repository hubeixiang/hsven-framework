package org.framework.hsven.datasource.pool.c3p0;

import org.framework.hsven.datasource.constants.Constants;
import org.framework.hsven.datasource.pool.JdbcPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = Constants.JDBC_POOL_C3P0_PREFIX)
public class C3p0JdbcPoolConfig implements JdbcPoolConfig {
    // <property name="poolSize" value="10" />
    private int poolSize = 10;
    // <property name="minPoolSize" value="10" />
    private int minPoolSize = 1;
    // <property name="maxPoolSize" value="30" />
    private int maxPoolSize = 100;
    // <property name="borrowConnectionTimeout" value="60" />
    private int borrowConnectionTimeout = 60;
    // <property name="maxIdleTime" value="60" />
    private int maxIdleTime = 120;
    // <property name="loginTimeout" value="60" />
    // <login-timeout>0</login-timeout>
    // <!--java数据库连接池，最大可等待获取datasouce的时间-->
    private int loginTimeout = 60;
    //c3p0连接池使用
    //mysql 没2秒检查所有连接池中的空闲连接 default:0
    private int idleConnectionTestPeriod = 600;
    //获取连接时测试有效性,每次都验证连接是否可用
    private boolean testConnectionOnCheckin = true;
    //获取连接时测试有效性,每次都验证连接是否可用
    private boolean testConnectionOnCheckout = true;
    private int unreturnedConnectionTimeout = 500;

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

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getLoginTimeout() {
        return loginTimeout;
    }

    public void setLoginTimeout(int loginTimeout) {
        this.loginTimeout = loginTimeout;
    }

    public int getIdleConnectionTestPeriod() {
        return idleConnectionTestPeriod;
    }

    public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
        this.idleConnectionTestPeriod = idleConnectionTestPeriod;
    }

    public boolean isTestConnectionOnCheckin() {
        return testConnectionOnCheckin;
    }

    public void setTestConnectionOnCheckin(boolean testConnectionOnCheckin) {
        this.testConnectionOnCheckin = testConnectionOnCheckin;
    }

    public boolean isTestConnectionOnCheckout() {
        return testConnectionOnCheckout;
    }

    public void setTestConnectionOnCheckout(boolean testConnectionOnCheckout) {
        this.testConnectionOnCheckout = testConnectionOnCheckout;
    }

    public int getUnreturnedConnectionTimeout() {
        return unreturnedConnectionTimeout;
    }

    public void setUnreturnedConnectionTimeout(int unreturnedConnectionTimeout) {
        this.unreturnedConnectionTimeout = unreturnedConnectionTimeout;
    }
}
