package org.framework.hsven.dataloader.api.impl;

import org.framework.hsven.dataloader.api.IRelatedTableLoadListener;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.TableLoadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultRelatedTableLoadListener implements IRelatedTableLoadListener {
    private static Logger logger = LoggerFactory.getLogger(DefaultRelatedTableLoadListener.class);

    @Override
    public String relatedListenerIdentification() {
        return "DefaultRelatedTableLoadListener";
    }

    @Override
    public void onMainTableLoadBegin(SimpleMainTable simpleMainTable) {
        logger.info(String.format("%s[onMainTableLoadBegin][%s]", relatedListenerIdentification(), simpleMainTable));
    }

    @Override
    public void onChildTableLoadBegin(boolean loadbyCache, SimpleChildTable simpleChildTable, Set<String> select_relatedFieldValueSet) {
        logger.info(String.format("%s[onChildTableLoadBegin][loadbyCache=%s,simpleChildTable=%s,select_relatedFieldValueSet=%s]", relatedListenerIdentification(), loadbyCache, simpleChildTable, select_relatedFieldValueSet));
    }

    @Override
    public void loadRow(int rowIndex, DBTableRowInfo dbTableRowInfo, Collection<TableField> fieldSet) {
        logger.info(String.format("%s[loadRow][rowIndex=%s,dbTableRowInfo=%s,fieldSet=%s]", relatedListenerIdentification(), rowIndex, dbTableRowInfo, fieldSet));
    }

    @Override
    public void updateRow(int rowIndex, Map<String, Object> fieldValue) {
        logger.info(String.format("%s[updateRow][rowIndex=%s,fieldValue=%s]", relatedListenerIdentification(), rowIndex, fieldValue));
    }

    @Override
    public void onEnd(QueryLoaderResultDesc mainTableQueryLoaderResultDesc, List<TableLoadResult> tableLoadResultList) {
        logger.info(String.format("%s[onEnd][%s]", relatedListenerIdentification(), mainTableQueryLoaderResultDesc));
    }

    @Override
    public Object getFieldValue(Integer rowIndex, String fieldName) {
        logger.info(String.format("%s[getFieldValue][rowIndex=%s,fieldName=%s]", relatedListenerIdentification(), rowIndex, fieldName));
        return null;
    }
}
