package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.utils.valid.ValidResult;

public class TableFieldUtil {
    public final static ValidResult isEnable(String tableAlias, TableField tableField) {
        ValidResult validResult = new ValidResult();
        if (StringUtils.isEmpty(tableField.getFieldAlias())) {
            validResult.appendAllTipTypeByDefaultPosition("查询出来的字段别名必须配置");
        }
        if (tableField.getFieldAliasDisplayEnumDbDataType() == null) {
            validResult.appendAllTipTypeByDefaultPosition("查询出来的字段别名代表的数据类型必须配置");
        }
        if (StringUtils.isEmpty(tableField.getTableAlias())) {
            validResult.appendAllTipTypeByDefaultPosition("字段来源的表别名必须配置");
        } else if (!tableAlias.equals(tableField.getTableAlias())) {
            validResult.appendAllTipTypeByDefaultPosition(String.format("字段来源的表别名必须一致[%s][%s]", tableAlias, tableField.getTableAlias()));
        }
        if (StringUtils.isEmpty(tableField.getTableFieldName())) {
            validResult.appendAllTipTypeByDefaultPosition("字段来源的字段必须配置");
        }
        return validResult;
    }
}
