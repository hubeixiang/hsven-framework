package org.framework.hsven.datasource.connection;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.datasource.constants.Constants;
import org.framework.hsven.datasource.enums.JdbcPoolTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;
import java.util.Set;

@ConfigurationProperties(prefix = Constants.DATA_SOURCE_NAMES_PREFIX)
public class DatasourceLoaderNames {
    private String names;
    //数据源适配类型
    private JdbcPoolTypeEnum poolType;

    public static String primaryDbName(DatasourceLoaderNames datasourceLoaderNames) {
        String dbName = null;
        if (StringUtils.isNotEmpty(datasourceLoaderNames.names)) {
            String[] namestr = datasourceLoaderNames.names.split(",");
            for (String name : namestr) {
                return name;
            }
        }
        return dbName;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public JdbcPoolTypeEnum getPoolType() {
        return poolType;
    }

    public void setPoolType(JdbcPoolTypeEnum poolType) {
        this.poolType = poolType;
    }

    public Set<String> dbNames() {
        Set<String> dbNames = new LinkedHashSet<>();
        if (StringUtils.isNotEmpty(names)) {
            String[] namestr = names.split(",");
            for (String name : namestr) {
                if (!dbNames.contains(name)) {
                    dbNames.add(name);
                }
            }
        }
        return dbNames;
    }
}
