package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.dataloader.beans.related.TableRelatedFieldSet;
import org.framework.hsven.utils.valid.ValidResult;

public class TableRelatedFieldSetUtil {
    public final static ValidResult isEnable(TableRelatedFieldSet tableRelatedFieldSet) {
        ValidResult validResult = new ValidResult();

        for (TableRelatedField tableRelatedField : tableRelatedFieldSet.getTableRelatedFieldList()) {
            ValidResult relatedFieldValidResult = TableRelatedFieldUtil.isEnable(tableRelatedField);
            if (!relatedFieldValidResult.isNormal()) {
                relatedFieldValidResult.appendAllTipTypeByPosition(String.format("%s配置错误:", tableRelatedField));
                validResult.mergeValidConfigResult(relatedFieldValidResult);
            }
        }
        return validResult;
    }
}
