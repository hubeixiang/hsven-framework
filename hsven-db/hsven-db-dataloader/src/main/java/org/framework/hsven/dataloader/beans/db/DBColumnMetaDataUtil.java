package org.framework.hsven.dataloader.beans.db;

public class DBColumnMetaDataUtil {
    public static void matchEnumDbDataType(DBColumnMetaData dbColumnMetaData, EnumDbDataType enumDbDataType) {
        if (dbColumnMetaData.getColumnClassName().equals("java.math.BigDecimal")) {
            if (enumDbDataType.equals(EnumDbDataType.Integer) || enumDbDataType.equals(EnumDbDataType.Long) || enumDbDataType.equals(EnumDbDataType.Double) || enumDbDataType.equals(EnumDbDataType.Float) || enumDbDataType.equals(EnumDbDataType.Short)) {
            } else {
                throw new RuntimeException(String.format("DBColumnMetaData.type=[%s] can't match appoint type [%s]", dbColumnMetaData.getType(), enumDbDataType));
            }
        } else if (dbColumnMetaData.getColumnClassName().equals("java.lang.String")) {
            if (enumDbDataType.equals(enumDbDataType.String)) {
            } else {
                throw new RuntimeException(String.format("DBColumnMetaData.type=[%s] can't match appoint type [%s]", dbColumnMetaData.getType(), enumDbDataType));
            }
        } else if (dbColumnMetaData.getColumnClassName().equals("java.sql.Timestamp")) {
            if (!enumDbDataType.equals(enumDbDataType.Date)) {
                throw new RuntimeException(String.format("DBColumnMetaData.type=[%s] can't match appoint type [%s]", dbColumnMetaData.getType(), enumDbDataType));
            }
        }
    }
}
