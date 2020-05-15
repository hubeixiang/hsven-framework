package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.dependency.EnumTableType;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;

public class SimpleMainTableCallableTaskDependency extends AbstractCallableRelatedTaskDependency<SimpleMainTable, QueryLoaderResultDesc> {
    private String mainTableAlias;
    private SimpleMainTable simpleMainTable;

    public SimpleMainTableCallableTaskDependency() {
        super(EnumTableType.Main);
    }

    public String getMainTableAlias() {
        return mainTableAlias;
    }

    @Override
    public boolean hasCurrentTable() {
        return false;
    }

    @Override
    public SimpleMainTable getCurrentTable(String key) {
        return simpleMainTable;
    }

    @Override
    public void addCurrentTable(SimpleMainTable simpleMainTable) {
        mainTableAlias = simpleMainTable.getTableAlias();
        getCurrentTableAlias().add(simpleMainTable.getTableAlias());
        this.simpleMainTable = simpleMainTable;
    }
}
