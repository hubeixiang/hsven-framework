package org.framework.hsven.dataloader.api.impl;

import org.framework.hsven.dataloader.api.IRelatedTableParserHandler;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;

/**
 * 默认的模型分析类实现
 */
public class DefaultRelatedTableParserHandler implements IRelatedTableParserHandler {
    @Override
    public TableLoadDefine parser(Object tableModel) {
        if (tableModel instanceof TableLoadDefine) {
            return (TableLoadDefine) tableModel;
        } else if (tableModel != null) {
            return parserInternal(tableModel);
        } else {
            return null;
        }
    }

    private TableLoadDefine parserInternal(Object tableModel) {
        return new TableLoadDefine();
    }
}
