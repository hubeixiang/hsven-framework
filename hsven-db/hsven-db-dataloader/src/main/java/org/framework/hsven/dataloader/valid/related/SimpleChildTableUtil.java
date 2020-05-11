package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.utils.valid.ValidResult;

public class SimpleChildTableUtil {
    public final static ValidResult isEnable(SimpleChildTable simpleChildTable) {
        ValidResult validResult = new ValidResult();
        if (simpleChildTable.getTableDefine() == null) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_childtable_tabledefine_must_config"));
            return validResult;
        } else {
            ValidResult tableDefineValidResult = TableDefineUtil.isEnable(simpleChildTable.getTableDefine());
            if (!tableDefineValidResult.isNormal()) {
                validResult.mergeValidConfigResult(tableDefineValidResult);
            }
        }

        if (!simpleChildTable.hasTableField()) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_childtable_table_field_must_config"));
        } else {
            ValidResult fieldSetValidResult = TableFieldSetUtil.isEnable(simpleChildTable.getTableDefine(), simpleChildTable.getTableFieldSet());
            if (!fieldSetValidResult.isNormal()) {
                validResult.mergeValidConfigResult(fieldSetValidResult);
            }
        }

        if (!simpleChildTable.hasTableRelatedField()) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_childtable_related_maintable_must_config"));
        } else {
            ValidResult relatedFieldValidResult = TableRelatedFieldSetUtil.isEnable(simpleChildTable.getTableRelatedFieldSet());
            if (!relatedFieldValidResult.isNormal()) {
                relatedFieldValidResult.appendAllTipTypeByPosition(TipsMessageUsed.getMessage("tips.valid_childtable_related_maintable_error", simpleChildTable.getTableAlias()));
                validResult.mergeValidConfigResult(relatedFieldValidResult);
            }
        }
        return validResult;
    }
}
