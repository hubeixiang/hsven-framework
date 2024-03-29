package org.framework.hsven.datasource.util;

import org.framework.hsven.datasource.enums.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author sven
 * @date 2019/12/2 14:17
 */
public class DataSourceUtil {
    private static Logger logger = LoggerFactory.getLogger(DataSourceUtil.class);

    /**
     * 使用驱动判断数据源类型
     *
     * @param driverClass
     * @return
     */
    public static DataSourceType initDataSourceType(String driverClass) {
        if (isOracle(driverClass)) {
            return DataSourceType.ORACLE;
        } else if (isSybase(driverClass)) {
            return DataSourceType.SYSBASE;
        } else if (isDb2(driverClass)) {
            return DataSourceType.DB2;
        } else if (isInformix(driverClass)) {
            return DataSourceType.INFOMIX;
        } else if (isMysql(driverClass)) {
            return DataSourceType.MYSQL;
        }
        return DataSourceType.UNKNOW;
    }

    private static boolean isMysql(String driverClass) {
        String lowerCase_driverClass = driverClass.toLowerCase();
        if (lowerCase_driverClass != null) {
            return lowerCase_driverClass.contains("mysql");
        }
        return false;
    }

    private static boolean isInformix(String driverClass) {
        String lowerCase_driverClass = driverClass.toLowerCase();
        if (lowerCase_driverClass != null) {
            return lowerCase_driverClass.contains("informix");
        }
        return false;
    }


    private static boolean isSybase(String driverClass) {
        String lowerCase_driverClass = driverClass.toLowerCase();
        return lowerCase_driverClass.contains("jtds") || lowerCase_driverClass.contains("sybase");
    }


    private static boolean isOracle(String driverClass) {
        String lowerCase_driverClass = driverClass.toLowerCase();
        return lowerCase_driverClass.contains("oracle");
    }

    private static boolean isDb2(String driverClass) {
        String lowerCase_driverClass = driverClass.toLowerCase();
        return lowerCase_driverClass.contains("db2");
    }

    public static <T> T binderOfProperties(Binder binder, Class<T> classzz) {
        ConfigurationProperties configurationProperties = AnnotationUtils.findAnnotation(classzz, ConfigurationProperties.class);
        String prefix = "";
        if (configurationProperties != null) {
            prefix = configurationProperties.prefix();
        }
        try {
            T object = binder.bind(prefix, Bindable.of(classzz)).get();
            return object;
        } catch (Exception e) {
            logger.warn(
                    String.format(String.format("properties prefix=%s binder %s Exception:%s", prefix, classzz.getName(), e.getMessage())));
            return null;
        }
    }
}
