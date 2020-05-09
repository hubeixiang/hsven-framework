package org.framework.hsven.dataloader.beans.related;

public class TableRelatedField {
    //<relatedField childTableField="vendor_id" mainTableField="vendor_id"></relatedField>
    private String childTableField;
    private String mainTableField;

    public String getChildTableField() {
        return childTableField;
    }

    public void setChildTableField(String childTableField) {
        this.childTableField = childTableField;
    }

    public String getMainTableField() {
        return mainTableField;
    }

    public void setMainTableField(String mainTableField) {
        this.mainTableField = mainTableField;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((childTableField == null) ? 0 : childTableField.hashCode());
        result = prime * result + ((mainTableField == null) ? 0 : mainTableField.hashCode());
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
        TableRelatedField other = (TableRelatedField) obj;
        if (childTableField == null) {
            if (other.childTableField != null)
                return false;
        } else if (!childTableField.equals(other.childTableField))
            return false;
        if (mainTableField == null) {
            if (other.mainTableField != null)
                return false;
        } else if (!mainTableField.equals(other.mainTableField))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TableRelatedField [childTableField=" + childTableField + ", mainTableField=" + mainTableField + "]";
    }


}
