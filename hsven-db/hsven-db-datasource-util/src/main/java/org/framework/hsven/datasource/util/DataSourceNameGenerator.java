package org.framework.hsven.datasource.util;

/**
 * @author sven
 * @date 2019/12/2 14:12
 * 自定义的多数据源加载时,与数据源相关的bean名字生成器
 */
public class DataSourceNameGenerator {
    public static String getDataSourceBeanName(String dbName) {
        return String.format("%sDataSource", dbName);
    }

    /**
     * 创建SqlSessionFactoryBean对应的名称,但是SqlSessionFactoryBean对象是工厂对象(FactoryBean),
     * 所以获取出来的实际对象是SqlSessionFactory的实现
     *
     * @param dbName
     * @return
     */
    public static String getSqlSessionFactoryBeanName(String dbName) {
        return String.format("%sSqlSessionFactoryBean", dbName);
    }

    public static String getSpringJdbcTemplate(String dbName) {
        return String.format("%sJdbcTemplate", dbName);
    }

    public static String getMapperScannerConfigurerBeanName(String dbName) {
        return String.format("%sMapperScannerConfigurer", dbName);
    }

    public static String getSqlSessionTemplate(String dbName) {
        return String.format("%sSqlSessionTemplate", dbName);
    }

    public static <T> String getMybatisMapperBeanName(String dbName, Class<T> classzz) {
        String shortClassName = classzz.getSimpleName();
        return String.format("%s%s", dbName, shortClassName);
    }
}
