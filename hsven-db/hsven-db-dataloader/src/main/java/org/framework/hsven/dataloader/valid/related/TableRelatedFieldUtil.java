package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.utils.valid.ValidResult;

public class TableRelatedFieldUtil {
    public final static ValidResult isEnable(TableRelatedField tableRelatedField) {
        ValidResult validResult = new ValidResult();
        if (StringUtils.isEmpty(tableRelatedField.getMainTableField())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_allchildtable_onerelated_maintable_main_field"));
        }
        if (StringUtils.isEmpty(tableRelatedField.getChildTableField())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_allchildtable_onerelated_maintable_child_field"));
        }

        return validResult;
    }
}
