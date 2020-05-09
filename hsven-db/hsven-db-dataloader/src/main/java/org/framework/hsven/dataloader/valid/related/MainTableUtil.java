package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.MainTable;
import org.framework.hsven.utils.valid.ValidResult;

public class MainTableUtil {
    public final static ValidResult isEnable(MainTable mainTable) {
        ValidResult validResult = new ValidResult();
        if (mainTable.getTableDefine() == null) {
            validResult.appendAllTipType("主表的TableDefine必须配置");
            return validResult;
        } else {
            ValidResult tableDefineValidResult = TableDefineUtil.isEnable(mainTable.getTableDefine());
            if (!tableDefineValidResult.isNormal()) {
                validResult.mergeValidConfigResult(tableDefineValidResult);
            }
        }

        if (mainTable.getFieldSet() == null || mainTable.getFieldSet().getFieldSet().size() == 0) {
            validResult.appendAllTipType("主表的查询字段必须配置");
        } else {
            ValidResult fieldSetValidResult = TableFieldSetUtil.isEnable(mainTable.getTableDefine(), mainTable.getFieldSet());
            if (!fieldSetValidResult.isNormal()) {
                validResult.mergeValidConfigResult(fieldSetValidResult);
            }
        }
        return validResult;
    }
}
