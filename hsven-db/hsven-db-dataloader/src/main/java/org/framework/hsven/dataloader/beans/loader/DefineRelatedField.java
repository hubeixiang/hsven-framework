package org.framework.hsven.dataloader.beans.loader;

import org.framework.hsven.dataloader.beans.db.EnumDbDataType;

/**
 * 定义的字表关联主表的关联方式
 */
public class DefineRelatedField extends DefineLocalField {
    private EnumDbDataType dataType;
    private String relatedField;

    public EnumDbDataType getDataType() {
        return dataType;
    }

    public void setDataType(EnumDbDataType dataType) {
        this.dataType = dataType;
    }

    public String getRelatedField() {
        return relatedField;
    }

    public void setRelatedField(String relatedField) {
        this.relatedField = relatedField;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
        result = prime * result + ((relatedField == null) ? 0 : relatedField.hashCode());
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
        DefineRelatedField other = (DefineRelatedField) obj;
        if (dataType == null) {
            if (other.dataType != null)
                return false;
        } else if (!dataType.equals(other.dataType))
            return false;
        if (relatedField == null) {
            if (other.relatedField != null)
                return false;
        } else if (!relatedField.equals(other.relatedField))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("DefineRelatedField [dataType=%s,relatedField=%s,localField=%s]", dataType, relatedField, getLocalField());
    }
}
