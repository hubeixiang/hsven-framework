package org.framework.hsven.dataloader.util;

import org.framework.hsven.datasource.enums.DataSourceType;

/**
 * @author sven
 * @date 2019/7/23 10:37
 */
public class DataSourceTypeUtil {
    /**
     * 判断此数据库是否有自增主键
     *
     * @param dataSourceType
     * @return false : 没有自增主键, true:有自增主键
     */
    public static boolean hasAutoIncrement(DataSourceType dataSourceType) {
        if (dataSourceType == null) {
            return false;
        }
        switch (dataSourceType) {
            case MYSQL:
                return true;
            case ORACLE:
            case DB2:
            case SYSBASE:
                return false;
            default:
                return false;
        }
    }
}
