package org.framework.hsven.dataloader.related.main;

import java.util.Set;

/**
 * 分区加载条件,只能是一个字段的分区,实际上就是主表的加载外部查询条件
 */
public class MainTableLoadPartitionContext {
    private String partitionFieldTableName;
    private Set<String> partitionFieldValueSet;

    public String getPartitionFieldTableName() {
        return partitionFieldTableName;
    }

    public void setPartitionFieldTableName(String partitionFieldTableName) {
        this.partitionFieldTableName = partitionFieldTableName;
    }

    public Set<String> getPartitionFieldValueSet() {
        return partitionFieldValueSet;
    }

    public void setPartitionFieldValueSet(Set<String> partitionFieldValueSet) {
        this.partitionFieldValueSet = partitionFieldValueSet;
    }
}
