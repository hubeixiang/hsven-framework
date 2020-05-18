package org.framework.hsven.dataloader.related.child.task;

import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndexEntity;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.listener.ChildTableLoaderListenerImpl;
import org.framework.hsven.dataloader.loader.DBSqlQueryLoader;
import org.framework.hsven.dataloader.loader.DBSqlQueryLoaderUtil;
import org.framework.hsven.dataloader.loader.model.QueryConfig;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.RelatedLoaderHandlerHolder;
import org.framework.hsven.dataloader.related.child.ChildTableConfigCacheEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleChildTableLoaderTask extends AbstractChildTableLoaderTask {
    private static Logger logger = LoggerFactory.getLogger(SimpleChildTableLoaderTask.class);
    private final RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity;

    public SimpleChildTableLoaderTask(String taskId, RelatedLoaderHandlerHolder relatedLoaderHandlerHolder, TableLoadDefine tableLoadDefine, SimpleChildTable currentsimpleChildTable, ChildTableConfigCacheEntity childTableConfigCacheEntity, RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity) {
        super(taskId, relatedLoaderHandlerHolder, tableLoadDefine, currentsimpleChildTable, childTableConfigCacheEntity);
        this.relatedValuesAndRowIndexEntity = relatedValuesAndRowIndexEntity;
    }

    @Override
    public void destory() {
    }


    @Override
    protected QueryLoaderResultDesc startChildTableLoaderByDatabase() {
        ChildTableLoaderListenerImpl childTableLoaderListener = new ChildTableLoaderListenerImpl();
        QueryConfig queryConfig = createQueryConfig(relatedValuesAndRowIndexEntity);
        DBSqlQueryLoader dbSqlQueryLoader = DBSqlQueryLoaderUtil.createDBSqlQueryLoader(childTableLoaderListener, queryConfig, relatedLoaderHandlerHolder.getiDataSourceProvider());
        QueryLoaderResultDesc queryLoaderResultDesc = dbSqlQueryLoader.load();
        return queryLoaderResultDesc;
    }

    @Override
    protected QueryLoaderResultDesc startChildTableLoaderByPrefetchCache() {
        //1. 用指定的关联主键值,从缓存中匹配数据并将匹配上的数据，加载到对应主表行上
        QueryLoaderResultDesc queryLoaderResultDesc = loadPrefetchCacheDataAppend2MainData(relatedValuesAndRowIndexEntity);
        return queryLoaderResultDesc;
    }
}
