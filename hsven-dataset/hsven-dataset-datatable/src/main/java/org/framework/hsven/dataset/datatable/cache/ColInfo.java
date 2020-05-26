package org.framework.hsven.dataset.datatable.cache;

import org.framework.hsven.dataset.datatable.beans.DataTypeEnum;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ColInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String name = null;
    public String title = null;
    public DataTypeEnum type = null;
    public String format = null;
    public SimpleDateFormat dateFormat = null;
    public DecimalFormat decFormat = null;

    public ColInfo() {

    }

    /**
     * @param name   列名
     * @param title  列标题
     * @param type   列类型
     * @param format 列格式，对 DtDate/DtDateMsecLong/DtDateSecInt 为 SimpleDateFormat 需要格式
     *               对DtDouble/Float 为DecimalFormat 所需要格式
     */
    public ColInfo(String name, String title, DataTypeEnum type, String format) {
        verifyColumnInputParam(name, title, type, format);
        this.name = name;
        this.title = title;
        this.type = type;
        this.format = format;
        boolean isException = false;
        try {
            if (type == DataTypeEnum.DtDate || type == DataTypeEnum.DtDateMsecLong || type == DataTypeEnum.DtDateSecInt) {
                if (format == null) {
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                } else {
                    dateFormat = new SimpleDateFormat(format);
                    dateFormat.format(new java.util.Date());
                }
            }

            if (type == DataTypeEnum.DtDouble || type == DataTypeEnum.DtFloat) {
                if (format == null) {
                    decFormat = new DecimalFormat();
                    decFormat.applyPattern("#0.0000");
                } else {
                    decFormat = new DecimalFormat();
                    decFormat.applyPattern(format);
                    decFormat.format(-1234.456789d);
                }
            }

        } catch (Exception ee) {
            isException = true;
        }
        if (isException) {
            throw new DataSetException("Error format string for column type:" + type);
        }
    }

    /**
     * 校验输入格式是否正确
     *
     * @param name   列名
     * @param title  列名称
     * @param type   类型
     * @param format 格式
     */
    private void verifyColumnInputParam(String name, String title, DataTypeEnum type, String format) {
        if (name == null) {
            throw new DataSetException("name can not be set to null." + name);
        }
        if (name.trim().length() == 0) {
            throw new DataSetException("name can not be set to empty." + name);
        }
        if (title == null) {
            throw new DataSetException("title can not be set to null." + title);
        }
        if (title.trim().length() == 0) {
            throw new DataSetException("title can not be set to empty." + title);
        }
        if (format != null) {
            if (format.trim().length() == 0) {
                throw new DataSetException("format can not be set to empty." + format);
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        ColInfo other = (ColInfo) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ColInfo [name=").append(name);
        builder.append(", title=").append(title);
        builder.append(", type=").append(type);
        builder.append(", format=").append(format);
        builder.append(", dateFormat=").append(dateFormat);
        builder.append(", decFormat=").append(decFormat).append("]");
        return builder.toString();
    }

}