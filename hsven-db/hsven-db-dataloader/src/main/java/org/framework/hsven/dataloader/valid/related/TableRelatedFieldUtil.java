package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.utils.valid.ValidResult;

public class TableRelatedFieldUtil {
    public final static ValidResult isEnable(TableFieldSet tableFieldSet, TableRelatedField tableRelatedField) {
        ValidResult validResult = new ValidResult();
        if (StringUtils.isEmpty(tableRelatedField.getMainTableField())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_allchildtable_onerelated_maintable_main_field"));
        } else {
            if (tableFieldSet.getFieldByName(tableRelatedField.getMainTableField()) != null) {
                //子表关联主表时,关联主表的主表字段名不能在子表查询列表中存在
                validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_allchildtable_onerelated_maintable_main_field_same_queryfield", tableRelatedField, tableRelatedField.getMainTableField()));
            }
        }
        if (StringUtils.isEmpty(tableRelatedField.getChildTableField())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_allchildtable_onerelated_maintable_child_field"));
        }

        return validResult;
    }
}
