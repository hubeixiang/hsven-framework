package org.framework.hsven.dataloader.beans.related;

public class TableLoadDefine {
    private String defineType;
    private boolean isAutoGeneration;
    //主表只能是一个查询sql(如果主表也是多个查询语句的结果在内存拼接很复杂,此处不做考虑)
    private SimpleMainTable simpleMainTable;
    private ChildTables childTables;

    public TableLoadDefine() {
        this.childTables = new ChildTables();
    }

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

    public SimpleMainTable getSimpleMainTable() {
        return simpleMainTable;
    }

    public void setSimpleMainTable(SimpleMainTable simpleMainTable) {
        this.simpleMainTable = simpleMainTable;
    }

    public ChildTables getChildTables() {
        return childTables;
    }

    public boolean hasSimpleChildTable() {
        return childTables != null && childTables.hasSimpleChildTable();
    }

    public boolean addSimpleChildTable(SimpleChildTable simpleChildTable) {
        return this.childTables.addChildTable(simpleChildTable);
    }
}
