package org.framework.hsven.dataloader.api.impl;

import org.framework.hsven.dataloader.api.IRelatedTableParserHandler;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;

/**
 * 默认的模型分析类实现
 */
public class DefaultRelatedTableParserHandler implements IRelatedTableParserHandler<TableLoadDefine> {
    @Override
    public TableLoadDefine parser(TableLoadDefine tableModel) {
        return tableModel;
    }
}
