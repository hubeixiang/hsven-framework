package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.utils.valid.ValidResult;

public class SimpleChildTableUtil {
    public final static ValidResult isEnable(SimpleChildTable simpleChildTable) {
        ValidResult validResult = new ValidResult();
        if (simpleChildTable.getTableDefine() == null) {
            validResult.appendAllTipType("子表的TableDefine必须配置");
            return validResult;
        } else {
            ValidResult tableDefineValidResult = TableDefineUtil.isEnable(simpleChildTable.getTableDefine());
            if (!tableDefineValidResult.isNormal()) {
                validResult.mergeValidConfigResult(tableDefineValidResult);
            }
        }

        if (!simpleChildTable.hasTableField()) {
            validResult.appendAllTipType("子表的查询字段必须配置");
        } else {
            ValidResult fieldSetValidResult = TableFieldSetUtil.isEnable(simpleChildTable.getTableDefine(), simpleChildTable.getTableFieldSet());
            if (!fieldSetValidResult.isNormal()) {
                validResult.mergeValidConfigResult(fieldSetValidResult);
            }
        }

        if (!simpleChildTable.hasTableRelatedField()) {
            validResult.appendAllTipType("子表与主表的关联信息必须配置");
        } else {
            ValidResult relatedFieldValidResult = TableRelatedFieldSetUtil.isEnable(simpleChildTable.getTableRelatedFieldSet());
            if (!relatedFieldValidResult.isNormal()) {
                relatedFieldValidResult.appendAllTipTypeByPosition("主表与字表关联信息错误:");
                validResult.mergeValidConfigResult(relatedFieldValidResult);
            }
        }
        return validResult;
    }
}
