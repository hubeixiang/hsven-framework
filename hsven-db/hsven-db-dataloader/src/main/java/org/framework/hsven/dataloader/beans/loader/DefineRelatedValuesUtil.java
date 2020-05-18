package org.framework.hsven.dataloader.beans.loader;

import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;

public class DefineRelatedValuesUtil {
    /**
     * 查询的主表数据,使用related_field字段生成关联键值
     *
     * @param defineRelatedFields
     * @param dbTableRowInfo
     * @return
     */
    public static DefineRelatedValues createTableDefineRelatedValuesByRelatedField(TableLoadDefine tableLoadDefine, DefineRelatedFields defineRelatedFields, DBTableRowInfo dbTableRowInfo) {
        if (!defineRelatedFields.hasTableRelatedFieldDefine()) {
            return null;
        }

        DefineRelatedValues defineRelatedValues = new DefineRelatedValues();
        for (DefineRelatedField defineRelatedField : defineRelatedFields.getDefineRelatedFieldList()) {
            DefineRelatedValue defineRelatedValue = DefineRelatedValueUtil.createDefineRelatedValueByRelatedField(tableLoadDefine,defineRelatedField, dbTableRowInfo);
            defineRelatedValues.addTableRelatedFieldDefine(defineRelatedValue);
        }
        return defineRelatedValues;
    }

    /**
     * 查询的子表数据,使用子表的local_field字段生成关联键值
     *
     * @param defineRelatedFields
     * @param dbTableRowInfo
     * @return
     */
    public static DefineRelatedValues createTableDefineRelatedValuesByLocalField(DefineRelatedFields defineRelatedFields, DBTableRowInfo dbTableRowInfo) {
        if (!defineRelatedFields.hasTableRelatedFieldDefine()) {
            return null;
        }

        DefineRelatedValues defineRelatedValues = new DefineRelatedValues();
        for (DefineRelatedField defineRelatedField : defineRelatedFields.getDefineRelatedFieldList()) {
            DefineRelatedValue defineRelatedValue = DefineRelatedValueUtil.createDefineRelatedValueByLoaclField(defineRelatedField, dbTableRowInfo);
            defineRelatedValues.addTableRelatedFieldDefine(defineRelatedValue);
        }
        return defineRelatedValues;
    }
}
