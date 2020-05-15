package org.framework.hsven.dataloader.related;

import org.framework.hsven.dataloader.api.IDataSourceProvider;
import org.framework.hsven.dataloader.api.IRelatedTableLoadListener;
import org.framework.hsven.dataloader.api.IRelatedTableParserHandler;
import org.framework.hsven.dataloader.api.IThreadPoolProvider;
import org.framework.hsven.dataloader.api.impl.DefaultDataSourceProvider;
import org.framework.hsven.dataloader.api.impl.DefaultRelatedTableLoadListener;
import org.framework.hsven.dataloader.api.impl.DefaultRelatedTableParserHandler;
import org.framework.hsven.dataloader.api.impl.DefaultThreadPoolProvider;

/**
 * 关联加载中,需要外部提供的处理程序Holder
 */
public class RelatedLoaderHandlerHolder {
    //表关联加载配置定义转换方法
    private final IRelatedTableParserHandler iRelatedTableParserHandler;
    //关联加载的数据加载到外部存储介质的存储处理
    private final IRelatedTableLoadListener iRelatedTableLoadListener;
    //加载任务线程池执行,线程池的创建方式
    private final IThreadPoolProvider iThreadPoolProvider;
    //加载时,执行sql时数据源获取方式
    private final IDataSourceProvider iDataSourceProvider;

    public RelatedLoaderHandlerHolder(IRelatedTableParserHandler iRelatedTableParserHandler, IRelatedTableLoadListener iRelatedTableLoadListener, IThreadPoolProvider iThreadPoolProvider, IDataSourceProvider iDataSourceProvider) {
        this.iRelatedTableParserHandler = iRelatedTableParserHandler == null ? new DefaultRelatedTableParserHandler() : iRelatedTableParserHandler;
        this.iDataSourceProvider = iDataSourceProvider == null ? new DefaultDataSourceProvider() : iDataSourceProvider;
        this.iRelatedTableLoadListener = iRelatedTableLoadListener == null ? new DefaultRelatedTableLoadListener() : iRelatedTableLoadListener;
        this.iThreadPoolProvider = iThreadPoolProvider == null ? new DefaultThreadPoolProvider() : iThreadPoolProvider;
    }

    public IRelatedTableParserHandler getiRelatedTableParserHandler() {
        return iRelatedTableParserHandler;
    }

    public IRelatedTableLoadListener getiRelatedTableLoadListener() {
        return iRelatedTableLoadListener;
    }

    public IThreadPoolProvider getiThreadPoolProvider() {
        return iThreadPoolProvider;
    }

    public IDataSourceProvider getiDataSourceProvider() {
        return iDataSourceProvider;
    }
}
