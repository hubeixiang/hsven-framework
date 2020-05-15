package org.framework.hsven.dataloader.beans.loader;

import org.framework.hsven.dataloader.beans.DBColumnValue;
import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;

public class DefineRelatedValuesUtil {
    public static DefineRelatedValues createTableDefineRelatedValues(DefineRelatedFields defineRelatedFields, DBTableRowInfo dbTableRowInfo) {
        if (!defineRelatedFields.hasTableRelatedFieldDefine()) {
            return null;
        }

        DefineRelatedValues defineRelatedValues = new DefineRelatedValues();
        for (DefineRelatedField defineRelatedField : defineRelatedFields.getDefineRelatedFieldList()) {
            DefineRelatedValue defineRelatedValue = new DefineRelatedValue(defineRelatedField);
            DBColumnValue dbColumnValue = dbTableRowInfo.getDataBaseColumnValue(defineRelatedField.getRelatedField());
            defineRelatedValue.setValue(dbColumnValue == null ? null : dbColumnValue.getColumnValue());
            defineRelatedValues.addTableRelatedFieldDefine(defineRelatedValue);
        }
        return defineRelatedValues;
    }
}
