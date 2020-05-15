package org.framework.hsven.dataloader.beans.dependency;

import org.apache.commons.lang3.StringUtils;

public class StructSql {
    private String wholeSql = "";

    public String getWholeSql() {
        return wholeSql;
    }

    public void setWholeSql(String wholeSql) {
        this.wholeSql = wholeSql;
    }

    public boolean hasSql() {
        return StringUtils.isNotEmpty(wholeSql);
    }

    @Override
    public String toString() {
        return String.format("StructSql [wholeSql=%s]", wholeSql);
    }
}
