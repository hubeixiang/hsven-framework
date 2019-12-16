package org.framework.hsven.datasource;

import org.framework.hsven.datasource.connection.DataSourceConfig;
import org.framework.hsven.datasource.pool.JdbcPoolConfig;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author sven
 * @date 2019/2/15 14:43
 * 数据源加载时,保存加载的数据源的信息
 */
public class InternalDBContextHelper {
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static InternalDBContextHelper internalDbContextHelper;
	private Set<String> dbNames = new LinkedHashSet<>();
	private ConcurrentMap<String, DataSourceConfig> dbConfigMap = new ConcurrentHashMap<>();
	private JdbcPoolConfig jdbcPoolConfig;

	private InternalDBContextHelper() {
	}

	private void init() {
	}

	public static InternalDBContextHelper getInstance() {

		if (internalDbContextHelper != null) {
			return internalDbContextHelper;
		}

		return createInstance();
	}

	private static synchronized InternalDBContextHelper createInstance() {
		if (internalDbContextHelper != null) {
			return internalDbContextHelper;
		}

		internalDbContextHelper = new InternalDBContextHelper();
		internalDbContextHelper.init();
		return internalDbContextHelper;
	}

	public boolean containsDb(String dbName) {
		boolean contains = false;
		try {
			readWriteLock.readLock().lock();
			contains = dbNames.contains(dbName);
		} finally {
			readWriteLock.readLock().unlock();
		}
		return contains;
	}

	public void addDbName(String dbName, DataSourceConfig dataSourceConfig) {
		try {
			readWriteLock.writeLock().lock();
			if (!dbNames.contains(dbName)) {
				dbNames.add(dbName);
				dataSourceConfig.setName(dbName);
				dbConfigMap.put(dbName, dataSourceConfig);
			}
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	public void removeDbName(String dbName) {
		try {
			readWriteLock.writeLock().lock();
			if (dbNames.contains(dbName)) {
				dbNames.remove(dbName);
				dbConfigMap.remove(dbName);
			}
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	public Set<String> getDbNames() {
		Set<String> names = new HashSet<>();
		try {
			readWriteLock.readLock().lock();
			names.addAll(dbNames);
		} finally {
			readWriteLock.readLock().unlock();
		}
		return names;
	}

	public DataSourceConfig getDataSourceConfig(String dbName) {
		DataSourceConfig dataSourceConfig = null;
		try {
			readWriteLock.readLock().lock();
			dataSourceConfig = dbConfigMap.get(dbName);
		} finally {
			readWriteLock.readLock().unlock();
		}
		return dataSourceConfig;
	}

	public JdbcPoolConfig getJdbcPoolConfig() {
		JdbcPoolConfig tmpJdbcPoolConfig = null;
		try {
			readWriteLock.readLock().lock();
			tmpJdbcPoolConfig = jdbcPoolConfig;
		} finally {
			readWriteLock.readLock().unlock();
		}
		return tmpJdbcPoolConfig;
	}

	public void setJdbcPoolConfig(JdbcPoolConfig jdbcPoolConfig) {
		try {
			readWriteLock.writeLock().lock();
			this.jdbcPoolConfig = jdbcPoolConfig;
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}
}
