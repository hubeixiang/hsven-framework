package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.utils.valid.ValidResult;

public class TableRelatedFieldUtil {
    public final static ValidResult isEnable(TableRelatedField tableRelatedField) {
        ValidResult validResult = new ValidResult();
        if (StringUtils.isEmpty(tableRelatedField.getMainTableField())) {
            validResult.appendAllTipType("关联主表字段必须配置");
        }
        if (StringUtils.isEmpty(tableRelatedField.getChildTableField())) {
            validResult.appendAllTipType("关联子表字段必须配置");
        }

        return validResult;
    }
}
