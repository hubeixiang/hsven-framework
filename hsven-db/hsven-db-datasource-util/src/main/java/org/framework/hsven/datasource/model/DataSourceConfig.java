package org.framework.hsven.datasource.model;

import org.framework.hsven.datasource.enums.DataSourceType;

public class DataSourceConfig {
    private String name;
    private String driver;
    private String url;
    private String username;
    private String password;
    //不用实际填写,可以利用url进行分析得到数据库类型
    private DataSourceType datasourceType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DataSourceType getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(DataSourceType datasourceType) {
        this.datasourceType = datasourceType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataSourceConfig other = (DataSourceConfig) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("DataSourceConfig[name=%s,driver=%s,url=%s,username=%s,password=%s]", name, driver, url, username, password);
    }
}
