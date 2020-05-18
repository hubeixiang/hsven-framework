package org.framework.hsven.dataloader.api;

import org.framework.hsven.dataloader.beans.related.TableLoadDefine;

/**
 * 关联的主表+子表数据加载流程中,将客户使用的关联模型,转换为本加载流程中对应的模型,并验证模型是否正确
 */
public interface IRelatedTableParserHandler<T> {
    public TableLoadDefine parser(T tableModel);
}
