package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.ChildTables;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.dataloader.beans.related.TableRelatedFieldSet;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
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
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_define_type"));
        }
        //所有表的字段map
        //Map<fieldNameAlias,TableField>
        Map<String, TableField> allTableFieldMap = new HashMap<>();
        //所有表字段中重复字段
        Set<String> repeatFieldAlias = new HashSet<>();

        //验证分区字段是否在主表的查询字段列表中
        if (tableLoadDefine.needPartition()) {
            SimpleMainTable simpleMainTable = tableLoadDefine.getSimpleMainTable();
            if (simpleMainTable == null || !simpleMainTable.hasTableField() ||
                    (simpleMainTable.hasTableField() && simpleMainTable.getTableFieldSet().getFieldByName(tableLoadDefine.getPartitionFieldName()) == null)) {
                validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_partition_must_in_maintable", tableLoadDefine.getPartitionFieldName()));
            }
        }

        if (!tableLoadDefine.hasSimpleMainTable()) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_maintable_must_config"));
            return validResult;
        } else {
            ValidResult mainValidResult = SimpleMainTableUtil.isEnable(tableLoadDefine.getSimpleMainTable());
            if (!mainValidResult.isNormal()) {
                mainValidResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_maintable_error_config"));
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
                    //添加所有查询字段的字段类型
                    tableLoadDefine.putAllFieldNameTableFieldMaps(tableField);
                }
            }
        }

        ChildTables childTables = tableLoadDefine.getChildTables();
        ValidResult childTablesValidResult = ChildTablesUtil.isEnable(childTables);
        if (!childTablesValidResult.isNormal()) {
            childTablesValidResult.appendAllTipTypeByPosition(TipsMessageUsed.getMessage("tips.valid_childtable_error_config"));
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
                        //添加所有查询字段的字段类型
                        tableLoadDefine.putAllFieldNameTableFieldMaps(tableField);
                    }
                }
            }
            //验证每个子表的关联字段(关联中的主字段)是否在查询字段中存在
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
                        String error = TipsMessageUsed.getMessage("tips.valid_childtable_related_maintable_field", simpleChildTable.getTableAlias(), errorRelatedField);
                        if (errorRelatedFieldStringBuffer.length() == 0) {
                            errorRelatedFieldStringBuffer.append(error);
                        } else {
                            errorRelatedFieldStringBuffer.append("\n").append(error);
                        }
                    }
                }
            }
            if (errorRelatedFieldStringBuffer.length() > 0) {
                validResult.appendAllTipTypeByPosition(TipsMessageUsed.getMessage("tips.valid_allchildtable_related_maintable_error", errorRelatedFieldStringBuffer.toString()));
            }
        }

        //验证关联的字段是否已经配置完整
        if (repeatFieldAlias.size() > 0) {
            validResult.appendAllTipType(TipsMessageUsed.getMessage("tips.valid_table_field_reapeat", repeatFieldAlias));
        }

        //生成关联信息
        if (validResult.isNormal()) {
            if (childTables.hasSimpleChildTable()) {
                Iterator<Map.Entry<String, SimpleChildTable>> it = childTables.entryChildTables();
                while (it.hasNext()) {
                    SimpleChildTable simpleChildTable = it.next().getValue();
                    SimpleChildTableUtil.parserTableDefineRelatedFields(tableLoadDefine, simpleChildTable);
                }
            }
        }

        return validResult;
    }
}
