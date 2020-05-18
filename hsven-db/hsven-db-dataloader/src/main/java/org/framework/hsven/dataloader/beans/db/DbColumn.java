package org.framework.hsven.dataloader.beans.db;

public class DbColumn {
    private String columnName = null;
    //字段的小写信息,主要是为了不同的数据字段的大小写区分.程序内部使用的关联都使用小写
    private String columnNameLowerCase = null;
    private EnumDbDataType type = null;
    //下面两个变量只在类型为数字类型时有作用,用与判断double/long/int
    private int precision = 0;
    private int scale = 0;
    //字段的最大长度,数据库限制的数据值最大长度
    private int length = 0;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.columnNameLowerCase = this.columnName == null ? null : this.columnName.toLowerCase();
    }

    public String getColumnNameLowerCase() {
        return columnNameLowerCase;
    }

    public EnumDbDataType getType() {
        return type;
    }

    public void setType(EnumDbDataType type) {
        this.type = type;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((columnNameLowerCase == null) ? 0 : columnNameLowerCase.hashCode());
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
        DbColumn other = (DbColumn) obj;
        if (columnNameLowerCase == null) {
            if (other.columnNameLowerCase != null)
                return false;
        } else if (!columnNameLowerCase.equals(other.columnNameLowerCase))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("DbColumn[columnName=%s,columnNameLowerCase=%s,type=%s,precision=%s,scale=%s,length=%s]", columnName, columnNameLowerCase, type, precision, scale, length);
    }
}
