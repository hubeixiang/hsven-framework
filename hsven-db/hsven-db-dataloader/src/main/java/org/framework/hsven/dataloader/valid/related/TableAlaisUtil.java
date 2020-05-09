package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;

public class TableAlaisUtil {
    public static boolean isEnable(String tableAlias) {
        if (tableAlias == null || StringUtils.isEmpty(tableAlias.trim())) {
            return false;
        }
        return true;
    }
}
