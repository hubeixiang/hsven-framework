package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.EnumDbDataType;
import org.framework.hsven.dataloader.beans.dependency.StructSql;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedField;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndexEntity;
import org.framework.hsven.dataloader.beans.loader.RelatedValuesAndRowIndexEntityUtil;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.TableDefine;
import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.dataloader.beans.related.TableRelatedFieldSet;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.utils.valid.ValidResult;

import java.util.Collection;

public class SimpleChildTableUtil {
    public final static ValidResult isEnable(SimpleChildTable simpleChildTable) {
        ValidResult validResult = new ValidResult();
        if (simpleChildTable.getTableDefine() == null) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_childtable_tabledefine_must_config"));
            return validResult;
        } else {
            ValidResult tableDefineValidResult = TableDefineUtil.isEnable(simpleChildTable.getTableDefine());
            if (!tableDefineValidResult.isNormal()) {
                validResult.mergeValidConfigResult(tableDefineValidResult);
            }
        }

        if (!simpleChildTable.hasTableField()) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_childtable_table_field_must_config"));
        } else {
            ValidResult fieldSetValidResult = TableFieldSetUtil.isEnable(simpleChildTable.getTableDefine(), simpleChildTable.getTableFieldSet());
            if (!fieldSetValidResult.isNormal()) {
                validResult.mergeValidConfigResult(fieldSetValidResult);
            }
        }

        if (!simpleChildTable.hasTableRelatedField()) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_childtable_related_maintable_must_config"));
        } else {
            ValidResult relatedFieldValidResult = TableRelatedFieldSetUtil.isEnable(simpleChildTable.getTableFieldSet(), simpleChildTable.getTableRelatedFieldSet());
            if (!relatedFieldValidResult.isNormal()) {
                relatedFieldValidResult.appendAllTipTypeByPosition(TipsMessageUsed.getMessage("tips.valid_childtable_related_maintable_error", simpleChildTable.getTableAlias()));
                validResult.mergeValidConfigResult(relatedFieldValidResult);
            }
        }

        return validResult;
    }

    public static void parserTableDefineRelatedFields(TableLoadDefine tableLoadDefine, SimpleChildTable simpleChildTable) {
        if (simpleChildTable != null && simpleChildTable.hasTableRelatedField()) {
            TableRelatedFieldSet tableRelatedFieldSet = simpleChildTable.getTableRelatedFieldSet();
            if (!simpleChildTable.getDefineRelatedFields().isComplete()) {
                //没有分析DefineRelatedFields时才做分析
                for (TableRelatedField tableRelatedField : tableRelatedFieldSet.getTableRelatedFieldList()) {
                    DefineRelatedField defineRelatedField = new DefineRelatedField();
                    EnumDbDataType dataType = tableLoadDefine.getAllFieldNameAliasDataType(tableRelatedField.getMainTableField());
                    defineRelatedField.setRelatedField(tableRelatedField.getMainTableField());
                    defineRelatedField.setLocalField(tableRelatedField.getChildTableField());
                    defineRelatedField.setDataType(dataType);
                    simpleChildTable.getDefineRelatedFields().addTableRelatedFieldDefine(defineRelatedField);
                }
                simpleChildTable.getDefineRelatedFields().setComplete(true);
            }
        }
    }

    /**
     * 拼接按照单个子表的定义,加载子表所有的数据时,需要执行的sql
     *
     * @param dataSourceType   数据库类型
     * @param tableLoadDefine  关联定义
     * @param simpleChildTable 单个要查询的子表的定义
     * @return
     */
    public static StructSql toCacheAllDataSql(DataSourceType dataSourceType, TableLoadDefine tableLoadDefine, SimpleChildTable simpleChildTable) {
        StructSql structSql = new StructSql();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select distinct ");
        String selectFieldSet = getSelectFieldForSimpleSql(dataSourceType, simpleChildTable.getTableAlias(), simpleChildTable.getTableFieldSet(), simpleChildTable.getTableRelatedFieldSet());
        stringBuilder.append(selectFieldSet).append("\r\n");

        String tableStr = TableDefineUtil.structQueryTable(dataSourceType, simpleChildTable.getTableDefine());

        stringBuilder.append(" from ").append(tableStr).append("\r\n");

        String whereStr = structQueryTableRelatedValues(dataSourceType, tableLoadDefine, simpleChildTable.getTableDefine(), null);
        if (StringUtils.isNotEmpty(whereStr)) {
            stringBuilder.append(" where ").append(whereStr).append("\r\n");
        }

        structSql.setWholeSql(stringBuilder.toString());
        return structSql;
    }

    /**
     * 拼接按照单个子表的定义,加载子表指定条件的数据时,需要执行的sql
     *
     * @param dataSourceType                 数据库类型
     * @param tableLoadDefine                关联定义
     * @param simpleChildTable               单个要查询的子表的定义
     * @param relatedValuesAndRowIndexEntity 子表查询的条件,没有条件不做查询sql拼接
     * @return
     */
    public static StructSql structSimpleSql(DataSourceType dataSourceType, TableLoadDefine tableLoadDefine, SimpleChildTable simpleChildTable, RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity) {
        StructSql structSql = new StructSql();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select distinct ");
        String selectFieldSet = getSelectFieldForSimpleSql(dataSourceType, simpleChildTable.getTableAlias(), simpleChildTable.getTableFieldSet(), simpleChildTable.getTableRelatedFieldSet());
        stringBuilder.append(selectFieldSet).append("\r\n");

        String tableStr = TableDefineUtil.structQueryTable(dataSourceType, simpleChildTable.getTableDefine());

        stringBuilder.append(" from ").append(tableStr).append("\r\n");

        String whereStr = structQueryTableRelatedValues(dataSourceType, tableLoadDefine, simpleChildTable.getTableDefine(), relatedValuesAndRowIndexEntity);
        if (StringUtils.isNotEmpty(whereStr)) {
            stringBuilder.append(" where ").append(whereStr).append("\r\n");
        }

        structSql.setWholeSql(stringBuilder.toString());
        return structSql;
    }

    private static String getSelectFieldForSimpleSql(DataSourceType dbType, String tableAlias, TableFieldSet tableFieldSet, TableRelatedFieldSet tableRelatedFieldSet) {
        Collection<String> selectFieldSet = TableFieldSetUtil.toSimpleSql(dbType, tableFieldSet);
        TableRelatedFieldSetUtil.toSimpleSql(dbType, tableAlias, tableRelatedFieldSet, selectFieldSet);
        String selectFieldSetStr = TableFieldSetUtil.combineQueryColumns(dbType, selectFieldSet);
        return selectFieldSetStr;
    }

    private final static String structQueryTableRelatedValues(DataSourceType dataSourceType, TableLoadDefine tableLoadDefine, TableDefine tableDefine, RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(tableDefine.getWhere())) {
            sb.append("(").append(tableDefine.getWhere()).append(")");
        }
        String relatedValueWhereSql = RelatedValuesAndRowIndexEntityUtil.structToWhereSqlRelatedValues(dataSourceType, relatedValuesAndRowIndexEntity);
        if (StringUtils.isNotEmpty(relatedValueWhereSql)) {
            if (sb.length() == 0) {
                sb.append(relatedValueWhereSql);
            } else {
                sb.append(" and ").append(relatedValueWhereSql);
            }
        }
        return sb.toString();
    }
}
