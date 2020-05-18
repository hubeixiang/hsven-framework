package org.framework.hsven.dataloader.beans.loader;

public class DefineRelatedValue {
    private final DefineRelatedField defineRelatedField;
    private Object value;

    public DefineRelatedValue(DefineRelatedField defineRelatedField) {
        this.defineRelatedField = defineRelatedField;
    }

    public DefineRelatedField getDefineRelatedField() {
        return defineRelatedField;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getValueForString() {
        return value == null ? null : String.valueOf(value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((defineRelatedField == null) ? 0 : defineRelatedField.hashCode());
        result = prime * result + ((value == null) ? 1 : value.hashCode());
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
        DefineRelatedValue other = (DefineRelatedValue) obj;
        if (defineRelatedField == null) {
            if (other.defineRelatedField != null)
                return false;
        } else if (!defineRelatedField.equals(other.defineRelatedField))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("DefineRelatedValue [defineRelatedField=%s,value=%s]", defineRelatedField, value);
    }
}
