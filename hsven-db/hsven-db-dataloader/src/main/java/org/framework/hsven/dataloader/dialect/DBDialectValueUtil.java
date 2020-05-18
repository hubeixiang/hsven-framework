package org.framework.hsven.dataloader.dialect;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.db.EnumDbDataType;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.framework.hsven.utils.DateCommonsUtils;
import org.framework.hsven.utils.DateJodaUtils;

import java.util.Date;

/**
 * 不同数据库值转换的方言工具
 */
public class DBDialectValueUtil {
    public final static Integer formatBoolean2QuerySql(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        } else {
            try {
                Integer integer = Integer.valueOf(value);
                if (integer == null) {
                    return null;
                } else if (integer == 1) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("是")) {
                    return 1;
                } else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("否")) {
                    return 0;
                } else {
                    return null;
                }
            }
        }
    }

    public final static String formatNumber2QuerySql(String value) {
        return value;
    }

    public final static String formatString2QuerySql(DataSourceType dbType, String value) {
        if (value == null) {
            return null;
        }
        switch (dbType) {
            case DB2:
            case MYSQL:
            case ORACLE:
            case INFOMIX:
            case SYSBASE:
                return String.format("'%s'", value);
            default:
                return String.format("'%s'", value);
        }
    }

    /**
     * @param dbType
     * @param value  yyyy-mm-dd hh24:mi:ss
     * @return
     */
    public final static String formatDate2QuerySql(DataSourceType dbType, String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        Date date = DateCommonsUtils.parseDate(value, null);
        return formatDate2QuerySql(dbType, date);
    }

    public final static String formatDate2QuerySql(DataSourceType dbType, Date value) {
        if (value == null) {
            return null;
        }
        String valueString = DateJodaUtils.format(value, DateJodaUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        switch (dbType) {
            case ORACLE:
                return String.format("to_date('%s','yyyy-mm-dd hh24:mi:ss')", valueString);
            case MYSQL:
                return String.format("date_format('%s','%Y-%m-%d %T')", valueString);
            case DB2:
                return String.format("TIMESTAMP('%s')", valueString);
//                return String.format("to_date('%s','yyyy-mm-dd hh24:mi:ss')", value);
            case INFOMIX:
            case SYSBASE:
            default:
                return String.format("to_date('%s','yyyy-mm-dd hh24:mi:ss')", valueString);
        }
    }

    public final static String formatString2QuerySql(DataSourceType dbType, EnumDbDataType targetType, String value) {
        if (value == null) {
            return null;
        }
        switch (targetType) {
            case Boolean:
                Integer integer = formatBoolean2QuerySql(value);
                if (integer == null) {
                    return null;
                } else {
                    return String.valueOf(integer);
                }
            case Short:
            case Integer:
            case Long:
            case Float:
            case Double:
                return formatNumber2QuerySql(value);
            case Date:
                return formatDate2QuerySql(dbType, value);
            case String:
                return formatString2QuerySql(dbType, value);
            default:
                return formatString2QuerySql(dbType, value);
        }
    }
}
