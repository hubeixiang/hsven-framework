package org.framework.hsven.datasource.pool.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.datasource.model.DataSourceConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import static org.springframework.util.Assert.notNull;

public class C3p0DataSourceFactoryBean implements FactoryBean<ComboPooledDataSource>, InitializingBean {
    private ComboPooledDataSource comboPooledDataSource;
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
    public ComboPooledDataSource getObject() throws Exception {
        if (this.comboPooledDataSource == null) {
            afterPropertiesSet();
        }
        return this.comboPooledDataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return this.comboPooledDataSource == null ? ComboPooledDataSource.class : this.comboPooledDataSource.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(dataSourceConfig, "Property 'dataSource' is required");
        if (comboPooledDataSource == null) {
            comboPooledDataSource = new ComboPooledDataSource();
            initConnectionInfo(dataSourceConfig, comboPooledDataSource);
            initConnectionPoolConfig(dataSourceConfig, comboPooledDataSource, c3p0JdbcPoolConfig);
        }
    }

    private void initConnectionInfo(DataSourceConfig dataBaseConfig, ComboPooledDataSource comboPooledDataSource) throws PropertyVetoException {
        // <property name="uniqueResourceName" value="dlDB" />
        comboPooledDataSource.setDataSourceName(dataBaseConfig.getName());
        // <property name="driverClassName">
        // <value>${jdbc.driver}</value>
        // </property>
        comboPooledDataSource.setDriverClass(dataBaseConfig.getDriver());
        // <property name="url">
        // <value>${jdbc.url}</value>
        // </property>
        comboPooledDataSource.setJdbcUrl(dataBaseConfig.getUrl());
        // <property name="user">
        // <value>${jdbc.username}</value>
        // </property>

        comboPooledDataSource.setUser(dataBaseConfig.getUsername());
        // <property name="password">
        // <value>${jdbc.password}</value>
        // </property>
        comboPooledDataSource.setPassword(dataBaseConfig.getPassword());
    }

    private void initConnectionPoolConfig(DataSourceConfig dataBaseConfig, ComboPooledDataSource comboPooledDataSource,
                                          C3p0JdbcPoolConfig c3p0JdbcPoolConfig) throws SQLException {
        DataSourceType dataSourceType = dataBaseConfig.getDatasourceType();
        comboPooledDataSource.setInitialPoolSize(c3p0JdbcPoolConfig.getPoolSize());
        comboPooledDataSource.setMinPoolSize(c3p0JdbcPoolConfig.getMinPoolSize());
        comboPooledDataSource.setMaxPoolSize(c3p0JdbcPoolConfig.getMaxPoolSize());
        // 获取连接失败重新获等待最大时间，在这个时间内如果有可用连接，将返回
        comboPooledDataSource.setCheckoutTimeout(c3p0JdbcPoolConfig.getBorrowConnectionTimeout() * 1000);
        // 最大闲置时间，超过最小连接池连接的连接将将关闭
        comboPooledDataSource.setMaxIdleTime(c3p0JdbcPoolConfig.getMaxIdleTime());
        // java数据库连接池，最大可等待获取datasouce的时间
        comboPooledDataSource.setLoginTimeout(c3p0JdbcPoolConfig.getLoginTimeout());

        //以下是c3p0连接池特色又的需要的参数
        // 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。默认值: 3
        comboPooledDataSource.setAcquireIncrement(4);
        // c3p0全局的PreparedStatements缓存的大小。如果maxStatements与maxStatementsPerConnection均为0，则缓存不生效，只要有一个不为0，则语句的缓存就能生效。如果默认值:
        // 0
        comboPooledDataSource.setMaxStatements(0);
        // 连接对象的最大生存时间，起始时间从连接池从数据库中创建连接对象开始计算。0表示永远不销毁
        comboPooledDataSource.setMaxConnectionAge(1800);
        // 每1800秒检查所有连接池中的空闲连接。Default: 0
        comboPooledDataSource.setIdleConnectionTestPeriod(c3p0JdbcPoolConfig.getIdleConnectionTestPeriod());

        // <!--定义所有连接测试都执行的测试语句。在使用连接测试的情况下这个一显著提高测试速度。测试的表必须在初始数据源的时候就存在。Default:
        // null-->// 目前只需要mysql有这个
        if (dataSourceType.equals(DataSourceType.MYSQL)) {
            String testQuery = comboPooledDataSource.getPreferredTestQuery();
            if (testQuery == null || testQuery.trim().length() == 0) {
                comboPooledDataSource.setPreferredTestQuery("select now()");
            }
        }

        comboPooledDataSource.setTestConnectionOnCheckin(c3p0JdbcPoolConfig.isTestConnectionOnCheckin());
        // <!-- 获取连接时测试有效性，每次都验证连接是否可用 -->
        comboPooledDataSource.setTestConnectionOnCheckout(c3p0JdbcPoolConfig.isTestConnectionOnCheckout());

        int timeout = c3p0JdbcPoolConfig.getUnreturnedConnectionTimeout();
        if (timeout > 0) {
            comboPooledDataSource.setDebugUnreturnedConnectionStackTraces(true);
            comboPooledDataSource.setUnreturnedConnectionTimeout(timeout);
        }
        // 当连接池用完时客户端调用getConnection()后等待获取新连接的时间
        // source.setCheckoutTimeout(StringUtil.String2Integer(p.getProperty("jdbc.checkoutTimeout"),1000));
        // c3p0是异步操作的，缓慢的JDBC操作通过帮助进程完成。扩展这些操作可以有效的提升性能
        // 通过多线程实现多个操作同时被执行
        comboPooledDataSource.setNumHelperThreads(5);
        // dirty read
        comboPooledDataSource.setConnectionCustomizerClassName(C3p0ConnectionCustomizer.class.getName());
        // 连接关闭时默认将所有未提交的操作回滚。
        comboPooledDataSource.setAutoCommitOnClose(true);
        // 定义在从数据库获取新连接失败后重复尝试的次数。
        comboPooledDataSource.setAcquireRetryAttempts(3);
    }

}
