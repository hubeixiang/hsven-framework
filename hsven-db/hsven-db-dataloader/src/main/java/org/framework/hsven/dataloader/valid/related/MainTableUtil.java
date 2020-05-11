package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.utils.valid.ValidResult;

public class MainTableUtil {
    public final static ValidResult isEnable(SimpleMainTable simpleMainTable) {
        ValidResult validResult = new ValidResult();
        if (simpleMainTable.getTableDefine() == null) {
            validResult.appendAllTipTypeByDefaultPosition("主表的TableDefine必须配置");
            return validResult;
        } else {
            ValidResult tableDefineValidResult = TableDefineUtil.isEnable(simpleMainTable.getTableDefine());
            if (!tableDefineValidResult.isNormal()) {
                validResult.mergeValidConfigResult(tableDefineValidResult);
            }
        }

        if (simpleMainTable.getTableFieldSet() == null || simpleMainTable.getTableFieldSet().getFieldSet().size() == 0) {
            validResult.appendAllTipTypeByDefaultPosition("主表的查询字段必须配置");
        } else {
            ValidResult fieldSetValidResult = TableFieldSetUtil.isEnable(simpleMainTable.getTableDefine(), simpleMainTable.getTableFieldSet());
            if (!fieldSetValidResult.isNormal()) {
                validResult.mergeValidConfigResult(fieldSetValidResult);
            }
        }
        return validResult;
    }
}
