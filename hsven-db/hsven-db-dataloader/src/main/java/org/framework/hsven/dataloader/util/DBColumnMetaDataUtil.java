package org.framework.hsven.dataloader.util;

import org.framework.hsven.dataloader.beans.db.DBColumnMetaData;
import org.framework.hsven.dataloader.beans.db.EnumDbDataType;
import org.framework.hsven.datasource.enums.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DBColumnMetaDataUtil {
    private static final String JAVA_LANG_SHORT = "java.lang.Short";
    private static final String JAVA_MATH_BIG_DECIMAL = "java.math.BigDecimal";
    private static final String JAVA_LANG_LONG = "java.lang.Long";
    private static final String JAVA_LANG_DOUBLE = "java.lang.Double";
    private static final String JAVA_LANG_FLOAT = "java.lang.Float";
    private static final String JAVA_LANG_INTEGER = "java.lang.Integer";
    private static Logger logger = LoggerFactory.getLogger(DBColumnMetaDataUtil.class);

    public static Object getColumnOracleValue(DBColumnMetaData dbColumnMetaData, ResultSet rs) throws SQLException {
        int columnMetDataIndex = dbColumnMetaData.getColumnMetDataIndex();
        EnumDbDataType columnType = dbColumnMetaData.getType();
        if (columnType == null) {
            return null;
        }
        Object ret = null;
        switch (columnType) {
            case Boolean:
                ret = rs.getObject(columnMetDataIndex, java.lang.Boolean.class);
                break;
            case Short:
                ret = rs.getObject(columnMetDataIndex, java.lang.Short.class);
                break;
            case Integer:
                ret = rs.getObject(columnMetDataIndex, java.lang.Integer.class);
                break;
            case Long:
                ret = rs.getObject(columnMetDataIndex, java.lang.Long.class);
                break;
            case Float:
                ret = rs.getObject(columnMetDataIndex, java.lang.Float.class);
                break;
            case Double:
                ret = rs.getObject(columnMetDataIndex, java.lang.Double.class);
                break;
            case Date:
                ret = rs.getDate(columnMetDataIndex);
                break;
            case String:
                ret = rs.getObject(columnMetDataIndex, java.lang.String.class);
                break;
            case Byte:
                ret = rs.getObject(columnMetDataIndex, java.lang.Byte.class);
                break;
            default:
                logger.error(String.format("%s type=%s unrecognized", dbColumnMetaData, columnType));
                ret = null;
                break;
        }
        return ret;
    }

    public static Object getColumnValue(DBColumnMetaData dbColumnMetaData, Object columnValue) {
        String columnName = null;
        Object return_value = null;
        EnumDbDataType columnType = null;
        try {
            if (columnValue == null || dbColumnMetaData == null) {
                return null;
            }
            columnType = dbColumnMetaData.getType();
            if (columnType == null) {
                return null;
            }

            columnName = dbColumnMetaData.getColumnName();

            Class clazz = columnValue.getClass();
            String name = clazz.getName();
            switch (columnType) {
                case Boolean:
                    if (columnValue instanceof Boolean) {
                        return_value = columnValue;
                    } else {
                        logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s,class=%s],can't convert to dataType=%s", columnName, columnValue, name, columnType));
                    }
                    break;
                case Short:
                    if (columnValue instanceof BigDecimal) {
                        return_value = ((BigDecimal) columnValue).shortValue();
                        break;
                    }
                    if (columnValue instanceof Short) {
                        return_value = columnValue;
                    } else if (columnValue instanceof Integer && (Integer) columnValue <= Short.MAX_VALUE
                            && (Integer) columnValue >= Short.MIN_VALUE) {
                        return_value = columnValue;
                    } else if (columnValue instanceof Long && (Long) columnValue <= Short.MAX_VALUE
                            && (Long) columnValue >= Short.MIN_VALUE) {

                        return_value = columnValue;
                    } else {
                        logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s,class=%s],can't convert to dataType=%s", columnName, columnValue, name, columnType));
                    }
                    break;
                case Double:
                    if (columnValue instanceof BigDecimal) {
                        return_value = ((BigDecimal) columnValue).doubleValue();
                        break;
                    }
                    if (columnValue instanceof Double) {
                        return_value = (double) columnValue;
                    } else {
                        logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s,class=%s],can't convert to dataType=%s", columnName, columnValue, name, columnType));
                    }
                    break;
                case Float:
                    if (columnValue instanceof BigDecimal) {
                        return_value = ((BigDecimal) columnValue).floatValue();
                        break;
                    }
                    if (columnValue instanceof Float) {
                        return_value = columnValue;
                    } else {
                        logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s,class=%s],can't convert to dataType=%s", columnName, columnValue, name, columnType));
                    }
                    break;
                case Integer:
                    if (columnValue instanceof BigDecimal) {
                        return_value = ((BigDecimal) columnValue).intValue();
                        break;
                    }
                    if (columnValue instanceof Integer) {
                        return_value = columnValue;
                    } else {
                        logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s,class=%s],can't convert to dataType=%s", columnName, columnValue, name, columnType));
                    }
                    break;

                case Long:
                    if (columnValue instanceof BigDecimal) {
                        Long long_return_value = ((BigDecimal) columnValue).longValue();
                        if (long_return_value > Integer.MIN_VALUE && long_return_value < Integer.MAX_VALUE) {
                            return_value = long_return_value.intValue();
                        } else {
                            return_value = long_return_value;
                        }
                        break;
                    } else if (columnValue instanceof Long) {
                        Long long_return_value = (Long) columnValue;
                        if (long_return_value > Integer.MIN_VALUE && long_return_value < Integer.MAX_VALUE) {
                            return_value = long_return_value.intValue();
                        } else {
                            return_value = long_return_value;
                        }
                        break;
                    } else {
                        logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s,class=%s],can't convert to dataType=%s", columnName, columnValue, name, columnType));
                    }
                    break;

                case String:
                    if (columnValue instanceof java.sql.Clob) {
                        String result = null;
                        Reader inStream = null;
                        try {
                            inStream = ((Clob) columnValue).getCharacterStream();
                            char[] c = new char[(int) ((Clob) columnValue).length()];
                            inStream.read(c);
                            result = new String(c);
                            inStream.close();
                        } catch (Exception e) {
                            logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s,class=%s],can't convert to dataType=%s", columnName, columnValue, name, columnType));
                        } finally {
                            try {
                                if (inStream != null)
                                    inStream.close();
                            } catch (Exception ex) {
                            }
                        }
                        return_value = result;
                    } else {
                        return_value = columnValue.toString();
                    }
                    break;
                case Date:
                    if (columnValue instanceof java.sql.Date) {
                        return_value = columnValue;
                    } else if (columnValue instanceof java.util.Date) {
                        return_value = columnValue;
                    } else if (columnValue instanceof java.sql.Timestamp) {
                        return_value = ((java.util.Date) columnValue);
                    } else {
                        logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s,class=%s],can't convert to dataType=%s", columnName, columnValue, name, columnType));
                    }
                    break;
                default:
                    logger.error(String.format("%s type=%s unrecognized", dbColumnMetaData, columnType));
                    break;
            }
            return return_value;
        } catch (Exception e) {
            logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s],can't convert to dataType=%s,Exception:%s", columnName, columnValue, columnType, e.getMessage()), e);
        } catch (Throwable e) {
            logger.error(String.format("DBColumnMetaDataUtil.getColumnValue columnName=%s,[columnValue=%s],can't convert to dataType=%s,Exception:%s", columnName, columnValue, columnType, e.getMessage()), e);
        }
        return null;
    }

    public static EnumDbDataType getEnumDbDataType(DataSourceType dataSourceType, String columnClassName, int colunmType, int scale, int precision,
                                                   String columnTypeName) {
        EnumDbDataType enumDbDataType = null;

        if (Types.ARRAY == colunmType) {

        } else if (Types.BIGINT == colunmType) {
            // 8.3.7 BIGINT
            //
            // JDBC 类型 BIGINT 代表一个 64 位的有符号整数，其值在 -9223372036854775808 和
            // 9223372036854775807 之间。

            enumDbDataType = EnumDbDataType.Long;
        } else if (Types.BINARY == colunmType) {

            // 8.3.2 BINARY、VARBINARY 和 LONGVARBINARY
            //
            // JDBC 类型 BINARY、VARBINARY 和 LONGVARBINARY 密切相关。BINARY
            // 表示固定长度的小二进制值，
            // VARBINARY 表示长度可变化的小二进制值，而 LONGVARBINARY 表示长度可变化的大二进制值
            enumDbDataType = EnumDbDataType.ByteArray;

        } else if (Types.BIT == colunmType) {
            // 8.3.3 BIT
            //
            // JDBC 类型 BIT 代表一个位值，可为 0 或 1
            enumDbDataType = EnumDbDataType.Boolean;
        } else if (Types.BLOB == colunmType) {
            enumDbDataType = EnumDbDataType.ByteArray;
        } else if (Types.BOOLEAN == colunmType) {
            enumDbDataType = EnumDbDataType.Boolean;
        } else if (Types.CHAR == colunmType) {
            // 8.3.1 CHAR、 VARCHAR 和 LONGVARCHAR
            //
            // JDBC 类型 CHAR、VARCHAR 和 LONGVARCHAR 密切相关。CHAR 表示固定长度的小字符串，VARCHAR
            // 表示长度可变的小字符串，而 LONGVARCHAR 表示长度可变的大字符串。
            enumDbDataType = EnumDbDataType.String;
        } else if (Types.CLOB == colunmType) {
            enumDbDataType = EnumDbDataType.String;
        } else if (Types.DATALINK == colunmType) {
            enumDbDataType = EnumDbDataType.Date;
        } else if (Types.DATE == colunmType) {
            // 8.3.12 DATE、TIME 和 TIMESTAMP
            //
            // 有三种 JDBC 类型与时间有关：
            enumDbDataType = EnumDbDataType.Date;
        } else if (Types.DECIMAL == colunmType) {
            // 8.3.11 DECIMAL 和 NUMERIC
            //
            // JDBC 类型 DECIMAL 和 NUMERIC 两者非常相似。它们都表示固定精度的十进制值。
            // DB2 下定义的 DECIMAL 有可能是Double类型：DECIMAL(8) 与DECIMAL(8,3)
            if (scale > 0) {
                enumDbDataType = EnumDbDataType.Double;
            } else {
                enumDbDataType = EnumDbDataType.Long;
            }
        } else if (Types.DOUBLE == colunmType) {
            // 8.3.9 DOUBLE
            //
            // JDBC 类型 DOUBLE 代表一个有 15 位尾数的“双精度”浮点数。
            enumDbDataType = EnumDbDataType.Double;
        } else if (Types.FLOAT == colunmType) {
            // 8.3.10 FLOAT
            //
            // JDBC 类型 FLOAT 基本上与 JDBC 类型 DOUBLE 相同。我们同时提供了 FLOAT 和
            // DOUBLE，其目的是与以前的
            // API 实现一致。但这却有可能产生误导。FLOAT 代表一个有 15 位尾数的“双精度”浮点数。
            enumDbDataType = EnumDbDataType.Float;
        } else if (Types.INTEGER == colunmType) {
            // 8.3.6 INTEGER
            //
            // JDBC 类型 INTEGER 代表一个 32 位的有符号整数，其值在 - 2147483648 和 2147483647 之间。
            enumDbDataType = EnumDbDataType.Integer;
        } else if (Types.JAVA_OBJECT == colunmType) {

        } else if (Types.LONGNVARCHAR == colunmType) {
            // 8.3.1 CHAR、 VARCHAR 和 LONGVARCHAR
            //
            // JDBC 类型 CHAR、VARCHAR 和 LONGVARCHAR 密切相关。CHAR 表示固定长度的小字符串，VARCHAR
            // 表示长度可变的小字符串，而 LONGVARCHAR 表示长度可变的大字符串。
            enumDbDataType = EnumDbDataType.String;
        } else if (Types.LONGVARBINARY == colunmType) {
            // 8.3.2 BINARY、VARBINARY 和 LONGVARBINARY
            //
            // JDBC 类型 BINARY、VARBINARY 和 LONGVARBINARY 密切相关。BINARY
            // 表示固定长度的小二进制值，
            // VARBINARY 表示长度可变化的小二进制值，而 LONGVARBINARY 表示长度可变化的大二进制值
            enumDbDataType = EnumDbDataType.ByteArray;
        } else if (Types.LONGVARCHAR == colunmType) {
            enumDbDataType = EnumDbDataType.String;
        } else if (Types.NCHAR == colunmType) {
            enumDbDataType = EnumDbDataType.String;
        } else if (Types.NCLOB == colunmType) {
            enumDbDataType = EnumDbDataType.String;
        } else if (Types.NULL == colunmType) {

        } else if (Types.NUMERIC == colunmType) {
            // 8.3.11 DECIMAL 和 NUMERIC
            //
            // JDBC 类型 DECIMAL 和 NUMERIC 两者非常相似。它们都表示固定精度的十进制值。
            // Oracle中数字都是NUMERIC
            if (columnClassName.equals(JAVA_LANG_SHORT)) {
                enumDbDataType = EnumDbDataType.Short;
            } else if (columnClassName.equals(JAVA_LANG_INTEGER)) {
                enumDbDataType = EnumDbDataType.Integer;
            } else if (columnClassName.equals(JAVA_LANG_FLOAT)) {
                enumDbDataType = EnumDbDataType.Float;
            } else if (columnClassName.equals(JAVA_LANG_DOUBLE)) {
                enumDbDataType = EnumDbDataType.Double;
            } else if (columnClassName.equals(JAVA_LANG_LONG)) {
                enumDbDataType = EnumDbDataType.Long;
            } else if (columnClassName.equals(JAVA_MATH_BIG_DECIMAL)) {
                if (scale > 0) {
                    enumDbDataType = EnumDbDataType.Double;
                } else if (scale == 0 && precision < 10) {
                    // 防止创建表结构时未指定具体精度时不能正确识别精度
                    // int java.lang.Integer.MAX_VALUE : 2147483647 [0x7fffffff]
                    enumDbDataType = EnumDbDataType.Integer;
                } else {
                    enumDbDataType = EnumDbDataType.Long;
                }
            }
        } else if (Types.NVARCHAR == colunmType) {
            enumDbDataType = EnumDbDataType.String;
        } else if (Types.OTHER == colunmType) {
            switch (dataSourceType) {
                case DB2:
                    enumDbDataType = getDb2OtherColumnType(columnClassName, colunmType, scale, precision,
                            columnTypeName);
                    break;
            }
        } else if (Types.REAL == colunmType) {
            // 8.3.8 REAL
            //
            // JDBC 类型 REAL 代表一个有 7 位尾数的“单精度”浮点数
            enumDbDataType = EnumDbDataType.Float;
        } else if (Types.REF == colunmType) {

        } else if (Types.ROWID == colunmType) {

        } else if (Types.SMALLINT == colunmType) {
            // 8.3.5 SMALLINT
            //
            // JDBC 类型 SMALLINT 代表一个 16 位的有符号整数，其值在 -32768 和 32767 之间。
            enumDbDataType = EnumDbDataType.Short;
        } else if (Types.SQLXML == colunmType) {

        } else if (Types.STRUCT == colunmType) {

        } else if (Types.TIME == colunmType) {
            // 8.3.12 DATE、TIME 和 TIMESTAMP
            //
            // 有三种 JDBC 类型与时间有关：
            enumDbDataType = EnumDbDataType.Date;
        } else if (Types.TIMESTAMP == colunmType) {
            // 8.3.12 DATE、TIME 和 TIMESTAMP
            //
            // 有三种 JDBC 类型与时间有关：
            enumDbDataType = EnumDbDataType.Date;
        } else if (Types.TINYINT == colunmType) {

            // 8.3.4 TINYINT
            //
            // JDBC 类型 TINYINT 代表一个 8 位无符号整数，其值在 0 到 255 之间。
            enumDbDataType = EnumDbDataType.Short;
        } else if (Types.VARBINARY == colunmType) {
            // 8.3.2 BINARY、VARBINARY 和 LONGVARBINARY
            //
            // JDBC 类型 BINARY、VARBINARY 和 LONGVARBINARY 密切相关。BINARY
            // 表示固定长度的小二进制值，
            // VARBINARY 表示长度可变化的小二进制值，而 LONGVARBINARY 表示长度可变化的大二进制值
            enumDbDataType = EnumDbDataType.ByteArray;
        } else if (Types.VARCHAR == colunmType) {
            // 8.3.1 CHAR、 VARCHAR 和 LONGVARCHAR
            //
            // JDBC 类型 CHAR、VARCHAR 和 LONGVARCHAR 密切相关。CHAR 表示固定长度的小字符串，VARCHAR
            // 表示长度可变的小字符串，而 LONGVARCHAR 表示长度可变的大字符串。
            enumDbDataType = EnumDbDataType.String;
        } else {
            logger.error("unknow EnumDbDataType.colunmType=" + colunmType);
        }

        if (enumDbDataType == null) {
            logger.error("EnumDbDataType is null.colunmType=" + colunmType);
            enumDbDataType = enumDbDataType.String;
        }
        return enumDbDataType;
    }

    private static EnumDbDataType getDb2OtherColumnType(String columnClassName, int colunmType, int scale, int precision,
                                                        String columnTypeName) {
        EnumDbDataType enumDbDataType = null;
        // db2特有的数据类型,分16和32
        if (columnTypeName.equalsIgnoreCase("DECFLOAT")) {
            enumDbDataType = EnumDbDataType.Double;
        }
        return enumDbDataType;
    }
}
