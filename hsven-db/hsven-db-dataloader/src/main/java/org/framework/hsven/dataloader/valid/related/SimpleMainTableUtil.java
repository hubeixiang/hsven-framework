package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.dependency.StructSql;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.beans.related.TableDefine;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.dialect.DBDialectSyntaxUtil;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.utils.valid.ValidResult;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;

public class SimpleMainTableUtil {
    public final static ValidResult isEnable(SimpleMainTable simpleMainTable) {
        ValidResult validResult = new ValidResult();
        if (simpleMainTable.getTableDefine() == null) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_maintable_tabledefine_must_config"));
            return validResult;
        } else {
            ValidResult tableDefineValidResult = TableDefineUtil.isEnable(simpleMainTable.getTableDefine());
            if (!tableDefineValidResult.isNormal()) {
                validResult.mergeValidConfigResult(tableDefineValidResult);
            }
        }

        if (!simpleMainTable.hasTableField()) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_maintable_field_must_config"));
        } else {
            ValidResult fieldSetValidResult = TableFieldSetUtil.isEnable(simpleMainTable.getTableDefine(), simpleMainTable.getTableFieldSet());
            if (!fieldSetValidResult.isNormal()) {
                validResult.mergeValidConfigResult(fieldSetValidResult);
            }
        }
        return validResult;
    }

    /**
     * 获取单表查询的简单sql
     *
     * @param dbType                 数据库类型
     * @param tableLoadDefine        关联定义
     * @param simpleMainTable        主表定义
     * @param partitionFieldValueSet 可能存在的分区条件
     * @return
     */
    public final static StructSql structSimpleSql(DataSourceType dbType, TableLoadDefine tableLoadDefine, SimpleMainTable simpleMainTable, Set<String> partitionFieldValueSet) {
        StructSql structSql = new StructSql();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ");
        if (simpleMainTable.isDistinct()) {
            stringBuilder.append(" distinct ");
        }
        String selectFieldSet = getSelectFieldForSimpleSql(dbType, simpleMainTable.getTableFieldSet());
        stringBuilder.append(selectFieldSet).append("\r\n");

        String tableStr = TableDefineUtil.structQueryTable(dbType, simpleMainTable.getTableDefine());

        stringBuilder.append(" from ").append(tableStr).append("\r\n");

        String whereStr = structQueryTablePartitionWhere(dbType, tableLoadDefine, simpleMainTable.getTableDefine(), partitionFieldValueSet);
        if (StringUtils.isNotEmpty(whereStr)) {
            stringBuilder.append(" where ").append(whereStr).append("\r\n");
        }

        structSql.setWholeSql(stringBuilder.toString());
        return structSql;
    }

    private static String getSelectFieldForSimpleSql(DataSourceType dbType, TableFieldSet tableFieldSet) {
        Collection<String> selectFieldSet = TableFieldSetUtil.toSimpleSql(dbType, tableFieldSet);
        String selectFieldSetStr = TableFieldSetUtil.combineQueryColumns(dbType, selectFieldSet);
        return selectFieldSetStr;
    }

    private final static String structQueryTablePartitionWhere(DataSourceType dataSourceType, TableLoadDefine tableLoadDefine, TableDefine tableDefine, Set<String> partitionFieldValueSet) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(tableDefine.getWhere())) {
            sb.append("(").append(tableDefine.getWhere()).append(")");
        }
        if (!CollectionUtils.isEmpty(partitionFieldValueSet)) {
            String partitionFieldNameAlais = String.format("%s.%s", tableDefine.getTableAlias(), tableLoadDefine.getPartitionFieldName());
            TableField tableField = tableLoadDefine.getPartitionFieldDataType();
            String insql = DBDialectSyntaxUtil.structInSql(dataSourceType, partitionFieldNameAlais, tableField.getFieldAliasDisplayEnumDbDataType(), partitionFieldValueSet);
            if (StringUtils.isNotEmpty(insql)) {
                if (sb.length() == 0) {
                    sb.append(insql);
                } else {
                    sb.append(" and (").append(insql).append(")");
                }
            }
        }
        return sb.toString();
    }

}
