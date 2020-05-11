package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.ChildTables;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
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
            validResult.appendAllTipTypeByDefaultPosition("defineType必须配置");
        }
        //所有表的字段map
        //Map<fieldNameAlias,TableField>
        Map<String, TableField> allTableFieldMap = new HashMap<>();
        //所有表字段中重复字段
        Set<String> repeatFieldAlias = new HashSet<>();

        if (tableLoadDefine.getSimpleMainTable() == null) {
            validResult.appendAllTipTypeByDefaultPosition("主表必须配置");
            return validResult;
        } else {
            ValidResult mainValidResult = MainTableUtil.isEnable(tableLoadDefine.getSimpleMainTable());
            if (!mainValidResult.isNormal()) {
                mainValidResult.appendAllTipTypeByDefaultPosition("主表配置错误如下:");
                validResult.mergeValidConfigResult(mainValidResult);
            }
            if (tableLoadDefine.getSimpleMainTable().getTableFieldSet() != null
                    && tableLoadDefine.getSimpleMainTable().getTableFieldSet().getFieldSet() != null) {
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
        if (childTables != null && childTables.hasSimpleChildTable()) {
            Iterator<Map.Entry<String, SimpleChildTable>> it = childTables.entryChildTables();
            while (it != null && it.hasNext()) {
                Map.Entry<String, SimpleChildTable> entry = it.next();
                SimpleChildTable simpleChildTable = entry.getValue();
                ValidResult childValidResult = ChildTableUtil.isEnable(simpleChildTable);
                if (!childValidResult.isNormal()) {
                    childValidResult.appendAllTipTypeByPosition(String.format("子表[%s]配置错误如下:", entry.getKey()));
                    validResult.mergeValidConfigResult(childValidResult);
                }
                if (simpleChildTable.getTableFieldSet() != null
                        && simpleChildTable.getTableFieldSet().getFieldSet() != null) {
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
        }

        //验证关联的字段是否已经配置完整
        if (repeatFieldAlias.size() > 0) {
            validResult.appendAllTipTypeByDefaultPosition(String.format("%s字段别名重复", repeatFieldAlias));
        }

        return validResult;
    }
}
