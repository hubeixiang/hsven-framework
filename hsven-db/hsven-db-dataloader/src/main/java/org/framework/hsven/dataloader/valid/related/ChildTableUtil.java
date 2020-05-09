package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.ChildTable;
import org.framework.hsven.utils.valid.ValidResult;

public class ChildTableUtil {
    public final static ValidResult isEnable(ChildTable childTable) {
        ValidResult validResult = new ValidResult();
        if (childTable.getTableDefine() == null) {
            validResult.appendAllTipType("子表的TableDefine必须配置");
            return validResult;
        } else {
            ValidResult tableDefineValidResult = TableDefineUtil.isEnable(childTable.getTableDefine());
            if (!tableDefineValidResult.isNormal()) {
                validResult.mergeValidConfigResult(tableDefineValidResult);
            }
        }

        if (childTable.getFieldSet() == null || childTable.getFieldSet().getFieldSet().size() == 0) {
            validResult.appendAllTipType("子表的查询字段必须配置");
        } else {
            ValidResult fieldSetValidResult = TableFieldSetUtil.isEnable(childTable.getTableDefine(), childTable.getFieldSet());
            if (!fieldSetValidResult.isNormal()) {
                validResult.mergeValidConfigResult(fieldSetValidResult);
            }
        }

        if (childTable.getTableRelatedFieldSet() == null || childTable.getTableRelatedFieldSet().getTableRelatedFieldList().size() == 0) {
            validResult.appendAllTipType("子表与主表的关联信息必须配置");
        } else {
            ValidResult relatedFieldValidResult = TableRelatedFieldSetUtil.isEnable(childTable.getTableRelatedFieldSet());
            if (!relatedFieldValidResult.isNormal()) {
                relatedFieldValidResult.appendAllTipTypeByPosition("主表与字表关联信息错误:");
                validResult.mergeValidConfigResult(relatedFieldValidResult);
            }
        }
        return validResult;
    }
}
