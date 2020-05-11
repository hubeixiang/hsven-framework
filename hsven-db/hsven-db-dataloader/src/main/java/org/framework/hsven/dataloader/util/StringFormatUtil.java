package org.framework.hsven.dataloader.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Collection;

public final class StringFormatUtil {
    public final static String formatNullOrTrim(String string) {
        return string == null ? "" : string.trim();
    }

    public final static String formatUpperCase(String string) {
        return string == null ? "" : string.trim().toUpperCase();
    }

    public final static String formatLowerCase(String string) {
        return string == null ? "" : string.trim().toLowerCase();
    }

    public final static String formatQueryColumn(String tableAlias, String fieldName, String fieldNameAlias) {
        StringBuffer sb = new StringBuffer();
        Assert.isTrue(fieldName != null, "查询的字段不能为空");
        if (StringUtils.isNotEmpty(tableAlias)) {
            sb.append(tableAlias).append(".");
        }
        sb.append(fieldName);
        if (StringUtils.isNotEmpty(fieldNameAlias)) {
            sb.append(" as ").append(fieldNameAlias);
        }
        return sb.toString();
    }

    public final static String formatQueryColumns(Collection<String> queryColumn) {
        StringBuffer sb = new StringBuffer();
        for (String column : queryColumn) {
            if (StringUtils.isNotEmpty(column)) {
                if (sb.length() == 0) {
                    sb.append(column);
                } else {
                    sb.append(",").append(column);
                }
            }
        }
        return sb.toString();
    }

    public final static String formatQueryWhere(Collection<String> queryWheres) {
        StringBuffer sb = new StringBuffer();
        for (String where : queryWheres) {
            if (StringUtils.isNotEmpty(where)) {
                if (sb.length() == 0) {
                    sb.append(where);
                } else {
                    sb.append(" AND ").append(where);
                }
            }
        }
        return sb.length() == 0 ? "" : String.format("(%s)", sb.toString());
    }
}
