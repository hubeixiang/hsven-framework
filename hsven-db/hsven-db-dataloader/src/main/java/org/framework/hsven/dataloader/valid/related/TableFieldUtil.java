package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.dialect.DBDialectSyntaxUtil;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.utils.valid.ValidResult;

import java.util.List;
import java.util.Set;

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

    public final static void toSimpleSql(DataSourceType dataSourceType, TableField tableField, List<String> queryFieldNameList) {
        String tableFieldName = tableField.getTableFieldName();
        if (StringUtils.isNotEmpty(tableFieldName)) {
            String queryColumn = String.format("%s.%s", tableField.getTableAlias(), tableFieldName);
            if (!queryFieldNameList.contains(queryColumn)) {
                queryFieldNameList.add(queryColumn);
            }
        }
        String secondTableFieldName = tableField.getSecondTableFieldName();
        if (StringUtils.isNotEmpty(secondTableFieldName)) {
            String queryColumn = String.format("%s.%s", tableField.getTableAlias(), secondTableFieldName);
            if (!queryFieldNameList.contains(queryColumn)) {
                queryFieldNameList.add(queryColumn);
            }
        }
    }

    public final static void toWholeQuerySql(DataSourceType dataSourceType, TableField tableField, Set<String> queryFieldNames) {
        String tableAlias = tableField.getTableAlias();
        String fieldNameAlias = tableField.getFieldAlias();
        String tableFieldName = tableField.getTableFieldName();
        String secondTableFieldName = tableField.getSecondTableFieldName();
        if (StringUtils.isNotEmpty(tableFieldName) && StringUtils.isEmpty(secondTableFieldName)) {
            queryFieldNames.add(String.format("%s.%s as %s", tableField.getTableAlias(), tableFieldName, fieldNameAlias));
        } else if (StringUtils.isNotEmpty(tableFieldName) && StringUtils.isEmpty(secondTableFieldName)) {
            queryFieldNames.add(DBDialectSyntaxUtil.structCaseNullWhenSql(dataSourceType, tableAlias, tableFieldName, secondTableFieldName, fieldNameAlias));
        }
    }
}
