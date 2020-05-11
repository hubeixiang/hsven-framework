package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.TableDefine;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.utils.valid.ValidResult;

public class TableFieldSetUtil {
    public final static ValidResult isEnable(TableDefine tableDefine, TableFieldSet tableFieldSet) {
        ValidResult validResult = new ValidResult();
        if (tableFieldSet.getFieldSet() == null || tableFieldSet.getFieldSet().size() == 0) {
            validResult.appendAllTipTypeByDefaultPosition("查询的字段必须配置");
        } else {
            String tableAlias = tableDefine.getTableAlias();
            for (TableField tableField : tableFieldSet.getFieldSet()) {
                ValidResult fieldValidResult = TableFieldUtil.isEnable(tableAlias, tableField);
                if (!fieldValidResult.isNormal()) {
                    fieldValidResult.appendAllTipTypeByPosition(String.format("%s错误如下:", tableField));
                    validResult.mergeValidConfigResult(fieldValidResult);
                }
            }
        }

        return validResult;
    }
}
