package org.framework.hsven.dataloader.beans.loader;

import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;

public class DefineRelatedValuesUtil {
    public static DefineRelatedValues createTableDefineRelatedValues(DefineRelatedFields defineRelatedFields, DBTableRowInfo dbTableRowInfo) {
        if (!defineRelatedFields.hasTableRelatedFieldDefine()) {
            return null;
        }

        DefineRelatedValues defineRelatedValues = new DefineRelatedValues();
        for (DefineRelatedField defineRelatedField : defineRelatedFields.getDefineRelatedFieldList()) {
            DefineRelatedValue defineRelatedValue = DefineRelatedValueUtil.createDefineRelatedValueByRelatedField(defineRelatedField, dbTableRowInfo);
            defineRelatedValues.addTableRelatedFieldDefine(defineRelatedValue);
        }
        return defineRelatedValues;
    }
}
