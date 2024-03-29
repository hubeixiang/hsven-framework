package org.framework.hsven.dataloader.beans.related;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TableLoadDefine {
    //定义加载完成后的所有字段的对应类型,不用外部填写
    //在验证此配置对象是否正确时,填写对应的值
    private final Map<String, TableField> allFieldNameTableFieldMaps = new HashMap<>();
    private String defineType;
    private boolean isAutoGeneration;
    //分区字段
    private String partitionFieldName;
    //主表只能是一个查询sql(如果主表也是多个查询语句的结果在内存拼接很复杂,此处不做考虑)
    private SimpleMainTable simpleMainTable;
    private ChildTables childTables = new ChildTables();

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

    public boolean needPartition() {
        return StringUtils.isNotEmpty(partitionFieldName);
    }

    public String getPartitionFieldName() {
        return partitionFieldName;
    }

    public void setPartitionFieldName(String partitionFieldName) {
        this.partitionFieldName = partitionFieldName;
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

    public boolean hasSimpleMainTable() {
        return simpleMainTable != null;
    }

    public boolean hasSimpleChildTable() {
        return childTables != null && childTables.hasSimpleChildTable();
    }

    public boolean addSimpleChildTable(SimpleChildTable simpleChildTable) {
        return this.childTables.addChildTable(simpleChildTable);
    }

    public void putAllFieldNameTableFieldMaps(TableField tableField) {
        allFieldNameTableFieldMaps.put(tableField.getFieldAlias(), tableField);
    }

    public TableField getAllFieldNameTableFieldMaps(String fieldNameAlias) {
        return allFieldNameTableFieldMaps.get(fieldNameAlias);
    }

    public TableField getPartitionFieldDataType() {
        return getAllFieldNameTableFieldMaps(partitionFieldName);
    }

    @Override
    public String toString() {
        return String.format("TableLoadDefine [defineType=%s,isAutoGeneration=%s,simpleMainTable=%s,childTables=%s]", defineType, isAutoGeneration, simpleMainTable, childTables);
    }
}
