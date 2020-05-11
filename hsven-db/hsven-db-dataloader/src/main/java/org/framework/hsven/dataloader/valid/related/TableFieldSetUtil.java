package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.TableDefine;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.utils.valid.ValidResult;

public class TableFieldSetUtil {
    public final static ValidResult isEnable(TableDefine tableDefine, TableFieldSet tableFieldSet) {
        ValidResult validResult = new ValidResult();
        if (!tableFieldSet.hasTableField()) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_field_must_config"));
        } else {
            String tableAlias = tableDefine.getTableAlias();
            for (TableField tableField : tableFieldSet.getFieldSet()) {
                ValidResult fieldValidResult = TableFieldUtil.isEnable(tableAlias, tableField);
                if (!fieldValidResult.isNormal()) {
                    fieldValidResult.appendAllTipTypeByPosition(TipsMessageUsed.getMessage("tips.valid_table_fields_query_error", tableField));
                    validResult.mergeValidConfigResult(fieldValidResult);
                }
            }
        }

        return validResult;
    }
}
