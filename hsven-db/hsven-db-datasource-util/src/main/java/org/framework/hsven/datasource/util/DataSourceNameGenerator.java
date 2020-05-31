package org.framework.hsven.datasource.util;

import javax.sql.DataSource;

/**
 * @author sven
 * @date 2019/12/2 14:12
 * 自定义的多数据源加载时,与数据源相关的bean名字生成器
 */
public class DataSourceNameGenerator {
    /**
     * 生成默认的指定数据库名的数据源bean名称
     *
     * @param dbName
     * @return
     */
    public static String generatorDataSourceRelevantBeanName(String dbName) {
        return generatorDataSourceRelevantBeanName(dbName, DataSource.class);
    }

    /**
     * 生成数据源名称相关的class的bean名称
     *
     * @param dbName
     * @param classzz
     * @param <T>
     * @return
     */
    public static <T> String generatorDataSourceRelevantBeanName(String dbName, Class<T> classzz) {
        String shortClassName = classzz.getSimpleName();
        return String.format("%s%s", formatDbName(dbName), shortClassName);
    }

    /**
     * 不指定数据源名称,生成对应的class的bean名称
     *
     * @param classzz
     * @param <T>
     * @return
     */
    public static <T> String generatorDataSourceRelevantBeanName(Class<T> classzz) {
        String shortClassName = classzz.getSimpleName();
        return toLowerCaseFirstOne(shortClassName);
    }

    public static String formatDbName(String dbName) {
        //格式化dbName,因为dbName不能随便设置,在bean name中是有格式要求的
        dbName = dbName.replaceAll("-", "");
        dbName = dbName.replaceAll("_", "");
        dbName = dbName.replaceAll("\\.", "");
        return toLowerCaseFirstOne(dbName);
    }

    public static String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0)))
            return str;
        else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        }
    }

    public static String toUpperCaseFirstOne(String str) {
        if (Character.isUpperCase(str.charAt(0)))
            return str;
        else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
    }
}
