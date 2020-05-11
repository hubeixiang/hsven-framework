package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.utils.valid.ValidResult;

public class TableFieldUtil {
    public final static ValidResult isEnable(String tableAlias, TableField tableField) {
        ValidResult validResult = new ValidResult();
        if (StringUtils.isEmpty(tableField.getFieldAlias())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_query_field_alias_must_config"));
        }
        if (tableField.getFieldAliasDisplayEnumDbDataType() == null) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_query_field_type_must_config"));
        }
        if (StringUtils.isEmpty(tableField.getTableAlias())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_query_field_tablealias_must_config"));
        } else if (!tableAlias.equals(tableField.getTableAlias())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_query_field_tablealias_must_same", tableAlias, tableField.getTableAlias()));
        }
        if (StringUtils.isEmpty(tableField.getTableFieldName())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_query_field_source_must_config"));
        }
        return validResult;
    }
}
