package org.framework.hsven.dataloader.beans.related;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TableLoadDefine {
    private String defineType;
    private boolean isAutoGeneration;
    private MainTable mainTable;
    //Map<childTableAlias,ChildTable>
    private Map<String, ChildTable> childTableMap;

    public String getDefineType() {
        return defineType;
    }

    public void setDefineType(String defineType) {
        this.defineType = defineType;
    }

    public boolean isAutoGeneration() {
        return isAutoGeneration;
    }

    public void setAutoGeneration(boolean autoGeneration) {
        isAutoGeneration = autoGeneration;
    }

    public MainTable getMainTable() {
        return mainTable;
    }

    public void setMainTable(MainTable mainTable) {
        this.mainTable = mainTable;
    }

    public boolean addChildTable(ChildTable childTable) {
        if (childTable == null) {
            return false;
        }
        if (childTableMap == null) {
            childTableMap = new HashMap<>();
        }

        if (childTableMap.containsKey(childTable.getTableAlias())) {
            return false;
        }

        childTableMap.put(childTable.getTableAlias(), childTable);
        return true;
    }

    public Iterator<Map.Entry<String, ChildTable>> entryChildTables() {
        return childTableMap == null ? null : childTableMap.entrySet().iterator();
    }

    public ChildTable getChildTable(String childTableAlias) {
        return childTableMap == null ? null : childTableMap.get(childTableAlias);
    }
}
