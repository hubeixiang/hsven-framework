package org.framework.hsven.dataloader.beans.related;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChildTables {
    //Map<childTableAlias,ChildTable>
    private Map<String, SimpleChildTable> childTableMap;

    public boolean hasSimpleChildTable() {
        return childTableMap != null && childTableMap.size() > 0;
    }

    public boolean addChildTable(SimpleChildTable simpleChildTable) {
        if (simpleChildTable == null) {
            return false;
        }
        if (childTableMap == null) {
            childTableMap = new HashMap<>();
        }

        if (childTableMap.containsKey(simpleChildTable.getTableAlias())) {
            return false;
        }

        childTableMap.put(simpleChildTable.getTableAlias(), simpleChildTable);
        return true;
    }

    public Iterator<Map.Entry<String, SimpleChildTable>> entryChildTables() {
        return childTableMap == null ? null : childTableMap.entrySet().iterator();
    }

    public SimpleChildTable getChildTable(String childTableAlias) {
        return childTableMap == null ? null : childTableMap.get(childTableAlias);
    }
}
