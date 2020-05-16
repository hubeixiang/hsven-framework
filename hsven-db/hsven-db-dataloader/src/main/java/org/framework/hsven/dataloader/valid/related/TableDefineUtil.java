package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.TableDefine;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.utils.valid.ValidResult;

public class TableDefineUtil {
    /**
     * 判断查询内容是表,还是查询sql
     *
     * @param tableDefine
     * @return
     */
    public final static boolean isTableName(TableDefine tableDefine) {
        return isTableName(tableDefine.getTableAlias());
    }

    public final static boolean isTableName(String queryContext) {
        String upperCase_tableName = queryContext.toUpperCase();
        if ((upperCase_tableName.contains("SELECT   ") || upperCase_tableName.contains("SELECT ")) && (upperCase_tableName.contains("FROM   ") || upperCase_tableName.contains("FROM "))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证指定的表是否可用
     *
     * @param tableDefine
     * @return
     */
    public final static ValidResult isEnable(TableDefine tableDefine) {
        ValidResult validResult = new ValidResult();
        if (tableDefine == null) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_define_must_config"));
            return validResult;
        }

        if (StringUtils.isEmpty(tableDefine.getDbName())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_define_db_must_config"));
        }
        if (!TableAlaisUtil.isEnable(tableDefine.getTableAlias())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_define_data_alias_must_config"));
        }
        if (StringUtils.isEmpty(tableDefine.getTableName())) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_define_query_sql_must_config"));
        }
        return validResult;
    }

    public final static String structQueryTable(DataSourceType dataSourceType, TableDefine tableDefine) {
        if (TableDefineUtil.isTableName(tableDefine)) {
            return String.format("%s %s", tableDefine.getTableName(), tableDefine.getTableAlias());
        } else {
            return String.format("(%s) %s", tableDefine.getTableName(), tableDefine.getTableAlias());
        }
    }

    public final static String structQueryTable(DataSourceType dataSourceType, String queryContext, String tableAlias) {
        if (TableDefineUtil.isTableName(queryContext)) {
            return String.format("%s %s", queryContext, tableAlias);
        } else {
            return String.format("(%s) %s", queryContext, tableAlias);
        }
    }
}
