package org.framework.hsven.dataloader.beans.loader;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.dialect.DBDialectValueUtil;
import org.framework.hsven.datasource.enums.DataSourceType;

import java.util.Iterator;
import java.util.Map;

public class RelatedValuesAndRowIndexEntityUtil {

    //将关联的值拼接为可以查询的sql

    /**
     * //将关联的值拼接为可以查询的sql
     * 1. 如果关联的是一个字段关联的,拼接的方式为  field_name in (1,2,3)
     * 2. 如果关联的是多个字段的关联,拼接方式为  ((field_name_1=v1 and field_name_2=v2) and (field_name_1=vv1 and field_name_2=vv2) )
     *
     * @param relatedValuesAndRowIndexEntity
     * @return
     */
    public static String structToWhereSqlRelatedValues(DataSourceType dataSourceType, RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity) {
        if (relatedValuesAndRowIndexEntity == null || !relatedValuesAndRowIndexEntity.hasRelatedValues()) {
            return null;
        }
        Iterator<Map.Entry<String, RelatedValuesAndRowIndex>> it = relatedValuesAndRowIndexEntity.entryRelatedValuesAndRowIndex();
        StringBuffer sb = new StringBuffer();
        DefineRelatedField singleDefineRelatedField = null;
        while (it != null && it.hasNext()) {
            RelatedValuesAndRowIndex relatedValuesAndRowIndex = it.next().getValue();
            DefineRelatedValues defineRelatedValues = relatedValuesAndRowIndex.getDefineRelatedValues();
            if (defineRelatedValues.isValuesIsNull()) {
                continue;
            }
            if (defineRelatedValues.isSingle()) {
                for (DefineRelatedValue defineRelatedValue : defineRelatedValues.getDefineRelatedValueList()) {
                    singleDefineRelatedField = defineRelatedValue.getDefineRelatedField();
                    String value = DBDialectValueUtil.formatString2QuerySql(dataSourceType, defineRelatedValue.getDefineRelatedField().getDataType(), defineRelatedValue.getValueForString());
                    if (StringUtils.isNotEmpty(value)) {
                        if (sb.length() == 0) {
                            sb.append(value);
                        } else {
                            sb.append(",").append(value);
                        }
                    }
                }
            } else {
                StringBuffer vsb = new StringBuffer();
                for (DefineRelatedValue defineRelatedValue : defineRelatedValues.getDefineRelatedValueList()) {
                    String value = DBDialectValueUtil.formatString2QuerySql(dataSourceType, defineRelatedValue.getDefineRelatedField().getDataType(), defineRelatedValue.getValueForString());
                    String sqlValue = null;
                    String fieldName = defineRelatedValue.getDefineRelatedField().getLocalField();
                    if (value == null) {
                        sqlValue = String.format(" %s is null ", fieldName);
                    } else {
                        sqlValue = String.format(" %s = %s ", fieldName, value);
                    }
                    if (vsb.length() == 0) {
                        vsb.append(sqlValue);
                    } else {
                        vsb.append(" and ").append(sqlValue);
                    }
                }
                if (sb.length() == 0) {
                    sb.append(String.format("(%s)", vsb.toString()));
                } else {
                    sb.append(" or ").append(String.format("(%s)", vsb.toString()));
                }
            }
        }
        if (sb.length() > 0) {
            if (singleDefineRelatedField != null) {
                return String.format("%s in (%s)", singleDefineRelatedField.getLocalField(), sb.toString());
            } else {
                return String.format("(%s)", sb.toString());
            }
        } else {
            return null;
        }
    }
}
