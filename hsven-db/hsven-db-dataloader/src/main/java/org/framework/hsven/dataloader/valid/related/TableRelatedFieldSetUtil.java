package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.dataloader.beans.related.TableRelatedFieldSet;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.utils.valid.ValidResult;

import java.util.Collection;

public class TableRelatedFieldSetUtil {
    public final static ValidResult isEnable(TableFieldSet tableFieldSet, TableRelatedFieldSet tableRelatedFieldSet) {
        ValidResult validResult = new ValidResult();

        for (TableRelatedField tableRelatedField : tableRelatedFieldSet.getTableRelatedFieldList()) {
            ValidResult relatedFieldValidResult = TableRelatedFieldUtil.isEnable(tableFieldSet, tableRelatedField);
            if (!relatedFieldValidResult.isNormal()) {
                relatedFieldValidResult.appendAllTipTypeByPosition(TipsMessageUsed.getMessage("tips.valid_allchildtable_onerelated_maintable_error", tableRelatedField));
                validResult.mergeValidConfigResult(relatedFieldValidResult);
            }
        }
        return validResult;
    }

    public final static void toSimpleSql(DataSourceType dataSourceType, String tableAlias, TableRelatedFieldSet tableRelatedFieldSet, Collection<String> collection) {
        if (tableRelatedFieldSet != null && tableRelatedFieldSet.hasTableRelatedField()) {
            for (TableRelatedField tableRelatedField : tableRelatedFieldSet.getTableRelatedFieldList()) {
                String queryColumn = String.format("%s.%s", tableAlias, tableRelatedField.getChildTableField());
                if (!collection.contains(queryColumn)) {
                    collection.add(queryColumn);
                }
            }
        }
    }
}
