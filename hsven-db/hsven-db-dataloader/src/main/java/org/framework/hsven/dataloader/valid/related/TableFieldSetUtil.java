package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.TableDefine;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.utils.valid.ValidResult;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public final static String combineQueryColumns(DataSourceType dataSourceType, Collection<String> queryColumns) {
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(queryColumns)) {
            for (String selectField : queryColumns) {
                if (StringUtils.isNotEmpty(selectField)) {
                    if (sb.length() == 0) {
                        sb.append(selectField);
                    } else {
                        sb.append(",").append(selectField);
                    }
                }
            }
        }
        return sb.toString();
    }

    public final static Collection<String> toSimpleSql(DataSourceType dataSourceType, TableFieldSet tableFieldSet) {
        List<String> list = new ArrayList<>();
        for (TableField tableField : tableFieldSet.getFieldSet()) {
            TableFieldUtil.toSimpleSql(dataSourceType, tableField, list);
        }
        return list;
    }

    public final static Set<String> toWholeQuerySql(DataSourceType dataSourceType, TableFieldSet tableFieldSet) {
        Set<String> return_field_set = new HashSet<String>();
        for (TableField tableField : tableFieldSet.getFieldSet()) {
            TableFieldUtil.toWholeQuerySql(dataSourceType, tableField, return_field_set);
        }
        return return_field_set;
    }
}
