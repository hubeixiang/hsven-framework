package org.framework.hsven.dataloader.related.child.task;

import org.framework.hsven.dataloader.beans.loader.LazyRelatedFieldsAndRowIndex;
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

/**
 * 不是直接与主表进行关联的子表的数据加载任务
 * 加载有2中方式
 * 1. 当前子表需要适时从数据库查询
 * 2. 当前子表数据不需要适时查询,从已经完成的缓存中获取
 */
public class SimpleChildTableLazyLoaderTask extends AbstractChildTableLoaderTask {
    private static Logger logger = LoggerFactory.getLogger(SimpleChildTableLazyLoaderTask.class);
    private final LazyRelatedFieldsAndRowIndex lazyRelatedFieldsAndRowIndex;

    public SimpleChildTableLazyLoaderTask(String taskId, RelatedLoaderHandlerHolder relatedLoaderHandlerHolder, TableLoadDefine tableLoadDefine, SimpleChildTable currentsimpleChildTable, ChildTableConfigCacheEntity childTableConfigCacheEntity, LazyRelatedFieldsAndRowIndex lazyRelatedFieldsAndRowIndex) {
        super(taskId, relatedLoaderHandlerHolder, tableLoadDefine, currentsimpleChildTable, childTableConfigCacheEntity);
        this.lazyRelatedFieldsAndRowIndex = lazyRelatedFieldsAndRowIndex;
    }

    @Override
    public void destory() {
    }


    @Override
    protected QueryLoaderResultDesc startChildTableLoaderByDatabase() {
        //1. 不是直接与主表关联的子表,查询数据库前需要先从主表中找出当前批次数据的关联主键
        RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity = createLazyRelatedValuesAndRowIndexEntity(lazyRelatedFieldsAndRowIndex);
        //2. 用查找出来的关联主键拼接sql,查询对应主表的数据
        ChildTableLoaderListenerImpl childTableLoaderListener = new ChildTableLoaderListenerImpl();
        QueryConfig queryConfig = createQueryConfig(relatedValuesAndRowIndexEntity);
        DBSqlQueryLoader dbSqlQueryLoader = DBSqlQueryLoaderUtil.createDBSqlQueryLoader(childTableLoaderListener, queryConfig, relatedLoaderHandlerHolder.getiDataSourceProvider());
        QueryLoaderResultDesc queryLoaderResultDesc = dbSqlQueryLoader.load();
        return queryLoaderResultDesc;
    }

    @Override
    protected QueryLoaderResultDesc startChildTableLoaderByPrefetchCache() {
        //1. 不是直接与主表关联的子表,查询数据库前需要先从主表中找出当前批次数据的关联主键
        RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity = createLazyRelatedValuesAndRowIndexEntity(lazyRelatedFieldsAndRowIndex);
        //2. 用查找出来的关联主键,从缓存中匹配数据并将匹配上的数据，加载到对应主表行上
        QueryLoaderResultDesc queryLoaderResultDesc = loadPrefetchCacheDataAppend2MainData(relatedValuesAndRowIndexEntity);
        return queryLoaderResultDesc;
    }
}
