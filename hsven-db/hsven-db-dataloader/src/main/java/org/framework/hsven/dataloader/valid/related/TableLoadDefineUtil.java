package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.ChildTables;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.dataloader.beans.related.TableRelatedFieldSet;
import org.framework.hsven.utils.valid.ValidResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TableLoadDefineUtil {
    public final static ValidResult isEnableTableLoadDefine(TableLoadDefine tableLoadDefine) {
        ValidResult validResult = new ValidResult();
        if (StringUtils.isEmpty(tableLoadDefine.getDefineType())) {
            validResult.appendAllTipType("defineType必须配置");
        }
        //所有表的字段map
        //Map<fieldNameAlias,TableField>
        Map<String, TableField> allTableFieldMap = new HashMap<>();
        //所有表字段中重复字段
        Set<String> repeatFieldAlias = new HashSet<>();

        if (!tableLoadDefine.hasSimpleChildTable()) {
            validResult.appendAllTipType("主表必须配置");
            return validResult;
        } else {
            ValidResult mainValidResult = SimpleMainTableUtil.isEnable(tableLoadDefine.getSimpleMainTable());
            if (!mainValidResult.isNormal()) {
                mainValidResult.appendAllTipType("主表配置错误如下:");
                validResult.mergeValidConfigResult(mainValidResult);
            }
            SimpleMainTable simpleMainTable = tableLoadDefine.getSimpleMainTable();
            if (simpleMainTable.hasTableField()) {
                //对主表的所有字段进行分类
                for (TableField tableField : tableLoadDefine.getSimpleMainTable().getTableFieldSet().getFieldSet()) {
                    if (allTableFieldMap.containsKey(tableField.getFieldAlias())) {
                        repeatFieldAlias.add(tableField.getFieldAlias());
                    } else {
                        allTableFieldMap.put(tableField.getFieldAlias(), tableField);
                        allTableFieldMap.put(tableField.getFieldAlias(), tableField);
                    }
                }
            }
        }

        ChildTables childTables = tableLoadDefine.getChildTables();
        ValidResult childTablesValidResult = ChildTablesUtil.isEnable(childTables);
        if (!childTablesValidResult.isNormal()) {
            childTablesValidResult.appendAllTipTypeByPosition("子表配置错误如下:");
            validResult.mergeValidConfigResult(childTablesValidResult);
        }
        if (childTables != null && childTables.hasSimpleChildTable()) {
            //验证每个子表的查询字段是否重复
            Iterator<Map.Entry<String, SimpleChildTable>> it1 = childTables.entryChildTables();
            while (it1 != null && it1.hasNext()) {
                Map.Entry<String, SimpleChildTable> entry = it1.next();
                SimpleChildTable simpleChildTable = entry.getValue();
                if (simpleChildTable.hasTableField()) {
                    for (TableField tableField : simpleChildTable.getTableFieldSet().getFieldSet()) {
                        if (allTableFieldMap.containsKey(tableField.getFieldAlias())) {
                            repeatFieldAlias.add(tableField.getFieldAlias());
                        } else {
                            allTableFieldMap.put(tableField.getFieldAlias(), tableField);
                            allTableFieldMap.put(tableField.getFieldAlias(), tableField);
                        }
                    }
                }
            }
            //验证每个子表的关联字段是否在查询字段中存在
            Iterator<Map.Entry<String, SimpleChildTable>> it2 = childTables.entryChildTables();
            StringBuffer errorRelatedFieldStringBuffer = new StringBuffer();
            while (it2 != null && it2.hasNext()) {
                Map.Entry<String, SimpleChildTable> entry = it2.next();
                SimpleChildTable simpleChildTable = entry.getValue();

                TableRelatedFieldSet tableRelatedFieldSet = simpleChildTable.getTableRelatedFieldSet();

                if (simpleChildTable.hasTableRelatedField()) {
                    Set<String> errorRelatedField = new HashSet<>();
                    for (TableRelatedField tableRelatedField : tableRelatedFieldSet.getTableRelatedFieldList()) {
                        if (!allTableFieldMap.containsKey(tableRelatedField.getMainTableField())) {
                            errorRelatedField.add(tableRelatedField.getMainTableField());
                        }
                    }
                    if (errorRelatedField.size() > 0) {
                        String error = String.format("子表[%s]关联主表字段%s必须在主表查询列表中配置.", simpleChildTable.getTableAlias(), errorRelatedField);
                        if (errorRelatedFieldStringBuffer.length() == 0) {
                            errorRelatedFieldStringBuffer.append(error);
                        } else {
                            errorRelatedFieldStringBuffer.append("\n").append(error);
                        }
                    }
                }
            }
            if (errorRelatedFieldStringBuffer.length() > 0) {
                validResult.appendAllTipTypeByPosition(String.format("子表关联主表的关联错误信息如下:%s", errorRelatedFieldStringBuffer.toString()));
            }
        }

        //验证关联的字段是否已经配置完整
        if (repeatFieldAlias.size() > 0) {
            validResult.appendAllTipType(String.format("字段别名重复的字段有%s", repeatFieldAlias));
        }

        return validResult;
    }
}
