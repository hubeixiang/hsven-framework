package org.framework.hsven.dataloader.loader;

import org.framework.hsven.dataloader.api.IDBSqlQueryLoaderListener;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.datasource.provider.IDataSourceProvider;
import org.springframework.lang.NonNull;

public class DBSqlQueryLoaderUtil {
    public static DBSqlQueryLoader createDBSqlQueryLoader(@NonNull IDBSqlQueryLoaderListener idbSqlQueryLoaderListener, @NonNull QueryConfig queryConfig, IDataSourceProvider iDataSourceProvider) {
        DBSqlQueryLoader dbSqlQueryLoader = new DBSqlQueryLoader();
        dbSqlQueryLoader.init(idbSqlQueryLoaderListener, queryConfig, iDataSourceProvider);
        return dbSqlQueryLoader;
    }
}
