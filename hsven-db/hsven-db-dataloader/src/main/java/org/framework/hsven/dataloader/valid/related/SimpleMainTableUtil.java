package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.dependency.StructSql;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.utils.valid.ValidResult;

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

    public final static StructSql structQuerySql(DataSourceType dbType, SimpleMainTable simpleMainTable, Set<String> partitionFieldValueSet) {
        return null;
    }
}
