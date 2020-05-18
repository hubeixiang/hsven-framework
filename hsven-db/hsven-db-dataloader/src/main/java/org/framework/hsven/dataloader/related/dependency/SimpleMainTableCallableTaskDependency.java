package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.dependency.EnumTableType;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.related.TableLoadResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SimpleMainTableCallableTaskDependency extends AbstractCallableRelatedTaskDependency<SimpleMainTable, TableLoadResult> {
    private String mainTableAlias;
    private SimpleMainTable simpleMainTable;
    private List<SimpleChildTable> prefetchChildTableList = new ArrayList<>();

    public SimpleMainTableCallableTaskDependency() {
        super(EnumTableType.Main);
    }

    public String getMainTableAlias() {
        return mainTableAlias;
    }

    public Iterator<Map.Entry<String, SimpleMainTable>> entryTABLEIterator() {
        return null;
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

    public List<SimpleChildTable> getPrefetchChildTableList() {
        return prefetchChildTableList;
    }

    @Override
    public void destory(String defineType) {
        if (prefetchChildTableList != null) {
            prefetchChildTableList.clear();
        }
        prefetchChildTableList = null;
        super.destory(defineType);
    }
}
