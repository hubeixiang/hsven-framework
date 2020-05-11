package org.framework.hsven.dataloader.beans.related;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TableFieldSet {

    private final Map<String, TableField> tableFieldSet = new HashMap<String, TableField>();
    private final Collection<TableField> tableFields = new ArrayList<>();

    public static String getFieldSelectStr(Set<String> field_set) {
        String return_selectStr = "";

        for (String selectField : field_set) {
            if (return_selectStr != null && return_selectStr.length() > 0) {
                return_selectStr += "," + selectField;
            } else {
                return_selectStr = selectField;
            }

        }
        return return_selectStr;
    }

    public boolean hasTableField() {
        return tableFields != null && tableFields.size() > 0;
    }

    public void addField(TableField tableField) {
        String fiedAlias = tableField.getFieldAlias();
        if (!this.tableFieldSet.containsKey(fiedAlias)) {
            this.tableFieldSet.put(fiedAlias, tableField);
            tableFields.add(tableField);
        }
    }

    public TableField getFieldByName(String fieldName) {
        return tableFieldSet.get(fieldName);
    }

    // public TableField getField(String tableAlias, String fieldName) {
    // String fieldAliasStr = TableField.getFieldAliasStr(tableAlias,
    // fieldName);
    // return this.tableFieldSet.get(fieldAliasStr);
    // }

    public Collection<TableField> getFieldSet() {
        return tableFields;

    }

    public Set<String> getTableFieldSelectStr() {

        Set<String> return_field_set = new HashSet<String>();
        Iterator<Entry<String, TableField>> it = this.tableFieldSet.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, TableField> entry = it.next();

            TableField tableField = entry.getValue();

            String fieldSelectStr = tableField.getTableFieldName();
            if (fieldSelectStr != null && fieldSelectStr.length() > 0) {
                return_field_set.add(fieldSelectStr);
            }
            String secondFieldSelectStr = tableField.getSecondTableFieldName();
            if (secondFieldSelectStr != null && secondFieldSelectStr.length() > 0) {

                return_field_set.add(secondFieldSelectStr);
            }
        }

        return return_field_set;
    }
}
