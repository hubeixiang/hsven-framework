package org.framework.hsven.dataloader.beans.related;

import java.util.ArrayList;
import java.util.List;

public class TableRelatedFieldSet {
    private List<TableRelatedField> tableRelatedFieldList = new ArrayList<>();

    public void addTableRelatedField(TableRelatedField tableRelatedField) {
        tableRelatedFieldList.add(tableRelatedField);
    }

    public List<TableRelatedField> getTableRelatedFieldList() {
        return tableRelatedFieldList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tableRelatedFieldList == null) ? 0 : tableRelatedFieldList.hashCode());
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
        TableRelatedFieldSet other = (TableRelatedFieldSet) obj;
        if (tableRelatedFieldList == null) {
            if (other.tableRelatedFieldList != null)
                return false;
        } else if (!tableRelatedFieldList.equals(other.tableRelatedFieldList))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TableRelatedFieldSet [tableRelatedFieldList=" + tableRelatedFieldList + "]";
    }


}
