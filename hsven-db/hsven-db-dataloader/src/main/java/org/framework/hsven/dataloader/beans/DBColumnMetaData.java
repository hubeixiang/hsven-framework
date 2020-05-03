package org.framework.hsven.dataloader.beans;


/**
 * sql查询出来的ResultSetMetaData中字段的定义
 */
public class DBColumnMetaData extends DbColumn {
    //强制从0开始,以与List中的位置顺序相符合
    private int columnIndex;
    //column在MetaData中的顺序号从1开始,可用于ResultSet.getObject(int columnMetDataIndex);
    private int columnMetDataIndex;
    private int colunmSqlType;
    private String columnClassName;
    private String columnTypeName;
    private String columnTableName;
    private String columnCatalogName;
    private String columnSchema;

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getColumnMetDataIndex() {
        return columnMetDataIndex;
    }

    public void setColumnMetDataIndex(int columnMetDataIndex) {
        this.columnMetDataIndex = columnMetDataIndex;
    }

    public int getColunmSqlType() {
        return colunmSqlType;
    }

    public void setColunmSqlType(int colunmSqlType) {
        this.colunmSqlType = colunmSqlType;
    }

    public String getColumnClassName() {
        return columnClassName;
    }

    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    public String getColumnTableName() {
        return columnTableName;
    }

    public void setColumnTableName(String columnTableName) {
        this.columnTableName = columnTableName;
    }

    public String getColumnCatalogName() {
        return columnCatalogName;
    }

    public void setColumnCatalogName(String columnCatalogName) {
        this.columnCatalogName = columnCatalogName;
    }

    public String getColumnSchema() {
        return columnSchema;
    }

    public void setColumnSchema(String columnSchema) {
        this.columnSchema = columnSchema;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + columnIndex;
        result = prime * result + ((getColumnNameLowerCase() == null) ? 0 : getColumnNameLowerCase().hashCode());
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
        DBColumnMetaData other = (DBColumnMetaData) obj;
        if (columnIndex != other.columnIndex)
            return false;
        if (getColumnNameLowerCase() == null) {
            if (other.getColumnNameLowerCase() != null)
                return false;
        } else if (!getColumnNameLowerCase().equals(other.getColumnNameLowerCase()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("DBColumnMetaData[columnIndex=%s,%s,columnClassName=%s,columnTypeName=%s,colunmSqlType=%s,columnTableName=%s,columnCatalogName=%s,columnSchema=%s]"
                , columnIndex, super.toString(), columnClassName, columnTypeName, colunmSqlType, columnTableName, columnCatalogName, columnSchema);
    }

}
