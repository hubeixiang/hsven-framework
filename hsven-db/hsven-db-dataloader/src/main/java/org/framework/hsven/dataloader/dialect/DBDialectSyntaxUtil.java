package org.framework.hsven.dataloader.dialect;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.EnumDbDataType;
import org.framework.hsven.datasource.enums.DataSourceType;

import java.util.Collection;

public class DBDialectSyntaxUtil {
    public final static String structCaseWhenSql(DataSourceType dbType, String tableAlias, String tableFieldName, String secondTableFieldName, String fieldNameAlias) {
        return String.format("case when %s.%s is null then %s.%s else %s.%s end as %s",
                tableAlias, tableFieldName, tableAlias, secondTableFieldName, tableAlias, tableFieldName, fieldNameAlias);
    }

    public final static String structInSql(DataSourceType dbType, String fieldName, EnumDbDataType enumDbDataType, Collection<String> values) {
        if (values == null || values.size() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (String value : values) {
            String sqlValue = DBDialectValueUtil.formatString2QuerySql(dbType, enumDbDataType, value);
            if (StringUtils.isNotEmpty(sqlValue)) {
                if (sb.length() == 0) {
                    sb.append(sqlValue);
                } else {
                    sb.append(",").append(sqlValue);
                }
            }
        }
        if (sb.length() == 0) {
            return null;
        } else {
            return String.format("%s in (%s)", fieldName, sb.toString());
        }
    }
}
