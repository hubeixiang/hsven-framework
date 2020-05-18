package org.framework.hsven.dataloader.beans.loader;

import org.framework.hsven.dataloader.beans.DBColumnValue;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;

public class DefineRelatedValueUtil {
    public static DefineRelatedValue createDefineRelatedValueByRelatedField(DefineRelatedField defineRelatedField, DBTableRowInfo dbTableRowInfo) {
        DefineRelatedValue defineRelatedValue = new DefineRelatedValue(defineRelatedField);
        DBColumnValue dbColumnValue = dbTableRowInfo.getDataBaseColumnValue(defineRelatedField.getRelatedField());
        defineRelatedValue.setValue(dbColumnValue == null ? null : dbColumnValue.getColumnValue());
        return defineRelatedValue;
    }

    public static DefineRelatedValue createDefineRelatedValueByLoaclField(DefineRelatedField defineRelatedField, DBTableRowInfo dbTableRowInfo) {
        DefineRelatedValue defineRelatedValue = new DefineRelatedValue(defineRelatedField);
        DBColumnValue dbColumnValue = dbTableRowInfo.getDataBaseColumnValue(defineRelatedField.getLocalField());
        defineRelatedValue.setValue(dbColumnValue == null ? null : dbColumnValue.getColumnValue());
        return defineRelatedValue;
    }

    public static DefineRelatedValue createDefineRelatedValue(DefineRelatedField defineRelatedField, Object value) {
        DefineRelatedValue defineRelatedValue = new DefineRelatedValue(defineRelatedField);
        defineRelatedValue.setValue(value);
        return defineRelatedValue;
    }
}
