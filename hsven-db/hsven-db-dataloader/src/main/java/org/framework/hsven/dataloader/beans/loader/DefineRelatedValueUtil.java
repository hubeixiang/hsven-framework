package org.framework.hsven.dataloader.beans.loader;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.beans.db.DBColumnValue;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;

public class DefineRelatedValueUtil {
    /**
     * 使用关联中的主表字段获取查询行结果中的主键值
     *
     * @param tableLoadDefine
     * @param defineRelatedField
     * @param dbTableRowInfo
     * @return
     */
    public static DefineRelatedValue createDefineRelatedValueByRelatedField(TableLoadDefine tableLoadDefine, DefineRelatedField defineRelatedField, DBTableRowInfo dbTableRowInfo) {
        Object target = null;
        TableField tableField = tableLoadDefine.getAllFieldNameTableFieldMaps(defineRelatedField.getRelatedField());
        DBColumnValue dbColumnValue = dbTableRowInfo.getDataBaseColumnValue(tableField.getTableFieldName());
        if (dbColumnValue == null || dbColumnValue.getColumnValue() == null) {
            if (tableField != null && StringUtils.isNotEmpty(tableField.getSecondTableFieldName())) {
                dbColumnValue = dbTableRowInfo.getDataBaseColumnValue(tableField.getSecondTableFieldName());
                target = dbColumnValue == null ? null : dbColumnValue.getColumnValue();
            }
        } else {
            target = dbColumnValue.getColumnValue();
        }
        return createDefineRelatedValue(defineRelatedField, target);
    }

    /**
     * 使用关联中的子表字段获取查询行结果中的主键值
     *
     * @param defineRelatedField
     * @param dbTableRowInfo
     * @return
     */
    public static DefineRelatedValue createDefineRelatedValueByLoaclField(DefineRelatedField defineRelatedField, DBTableRowInfo dbTableRowInfo) {
        DBColumnValue dbColumnValue = dbTableRowInfo.getDataBaseColumnValue(defineRelatedField.getLocalField());
        return createDefineRelatedValue(defineRelatedField, dbColumnValue == null ? null : dbColumnValue.getColumnValue());
    }

    public static DefineRelatedValue createDefineRelatedValue(DefineRelatedField defineRelatedField, Object value) {
        DefineRelatedValue defineRelatedValue = new DefineRelatedValue(defineRelatedField);
        defineRelatedValue.setValue(value);
        return defineRelatedValue;
    }
}
