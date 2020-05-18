package org.framework.hsven.dataset.datatable.cache;

import org.framework.hsven.dataset.datatable.DataSetUtil;
import org.framework.hsven.dataset.datatable.api.ICellObject;
import org.framework.hsven.dataset.datatable.beans.DataTypeEnum;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * 用于处理dataTable中的多行数据
 */
public class Rows implements Serializable {
    private static final long serialVersionUID = 1L;
    private ColInfo[] colsInfo = null; // 列类型，指向dataTable的所有列类型
    private Object[] cols = null; // 列数据, 指向dataTable的列数据
    private int colSize = 0; // 列的数量

    private int[] rawRowNos = null; // 原始的列的长度
    private int curRowIndex = -1; // 当前行的index，从0开始

    private DataTable curDt = null; // 指向当前dataTable对象

    /**
     * @param dt        关联的dataTable
     * @param rawRowNos 外部创建的指向原始datatable的行号
     */
    public Rows(DataTable dt, int[] rawRowNos) {
        curDt = dt;
        this.colsInfo = dt.getColsInfo();
        this.cols = dt.getCols();
        this.colSize = dt.getColSize();
        this.rawRowNos = rawRowNos;
    }

    /**
     * 取得指定列名的 列号，从1开始顺序编号
     *
     * @param colName 列名的字符串表示，不区分大小写
     * @return 对应参数的列号，从1开始顺序编号，当不存在时抛出异常，当有多个同名列时返回第1个匹配列;
     * @throws DataSetException 当参数错误或列名错误时抛出异常
     */
    public int getColIndex(String colName) throws DataSetException {
        int colIndex = this.curDt.getColIndex(colName);
        if (colIndex < 0) {
            throw new DataSetException("Error column name,colName:" + colName);
        }
        return colIndex + 1;
    }

    public String getColName(int colIndex) {
        if (colIndex < 0) {
            throw new DataSetException("Error column name");
        }
        ColInfo colInfo = this.curDt.getColInfo(colIndex);
        return colInfo.name;
    }

    /**
     * 移动游标到第1行
     *
     * @return false 表示无数据行，无法移动，true表示正确
     */
    public boolean moveFirst() {
        if (rawRowNos.length == 0) {
            return false;
        }
        curRowIndex = 0;
        return true;
    }

    /**
     * 移动到最后一行
     *
     * @return false 表示无数据行，无法移动，true表示正确
     */
    public boolean moveLast() {
        if (rawRowNos.length == 0) {
            return false;
        }
        curRowIndex = rawRowNos.length - 1;
        return true;
    }

    /**
     * 移动游标到指定的行
     *
     * @param rowIndex 指定的行号，从0开始编号，最大值为[总行数-1]
     * @return false 表示无法移动到指定行,上一次游标将保持不变，true表示正确
     */
    public boolean moveTo(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rawRowNos.length) {
            return false;
        }
        curRowIndex = rowIndex;
        return true;
    }

    /**
     * 下移一行，当不运行moveFirst或moveLast时，第1次运行将指向首行
     *
     * @return true 表示移动正确，false表示无数据或已经移到最后一行
     */
    public boolean next() {
        curRowIndex++;
        if (curRowIndex >= rawRowNos.length || curRowIndex < 0) {
            return false;
        }
        return true;
    }

    /**
     * 上移一行，当不运行moveFirst或moveLast时，第1次运行将返回false
     *
     * @return true 表示移动正确，false表示无数据或已经移到第一行
     */
    public boolean previous() {
        if (curRowIndex < 0) {
            return false;
        }
        curRowIndex--;
        if (curRowIndex >= rawRowNos.length || curRowIndex < 0) {
            return false;
        }
        return true;
    }

    /**
     * 得到目前行对象中的总行数
     *
     * @return 行对象的总行数
     */
    public int getRowCount() {
        return rawRowNos.length;
    }

    /**
     * 判断当前列是否为空，存在效率转换问题，如确定不存在null，可以不调用判断或取数据后调用
     *
     * @param colIndex 列号，从1开始编号
     * @return true表示为空 false 表示不是不为空
     * @throws Exception 数组越界或类型不匹配时产生
     */
    public boolean isNull(int colIndex) throws Exception {
        return (getObject(colIndex) == null);
    }

    /**
     * 得到整数值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值，为 Integer.MIN_VALUE时表示为null
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public int getInt(int colIndex) throws Exception {
        return ((int[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到double值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值，为 (0-Double.MAX_VALUE)时表示为null
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public double getDouble(int colIndex) throws Exception {
        return ((double[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到float值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值，为 (0-Float.MAX_VALUE)时表示为null
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public float getFloat(int colIndex) throws Exception {
        return ((float[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到short值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值，相应字段不能为null
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public short getShort(int colIndex) throws Exception {
        return ((short[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到byte值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值，相应字段不能为null
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public byte getByte(int colIndex) throws Exception {
        return ((byte[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到boolean值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值，相应字段不能为null
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public boolean getBool(int colIndex) throws Exception {
        return ((boolean[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到long值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值，为Long.MIN_VALUE时表示为null
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public long getLong(int colIndex) throws Exception {
        return ((long[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到String值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public String getTypeString(int colIndex) throws Exception {
        return ((String[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到ICellObject值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public ICellObject getCellObject(int colIndex) throws Exception {
        return ((ICellObject[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到byte[]值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public byte[] getByteArr(int colIndex) throws Exception {
        return ((byte[][]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到java.util.Date值
     *
     * @param colIndex 列号，从1开始编号
     * @return 相应单元格的值
     * @throws Exception 当单元格类型与获取类型不匹配，colIndex不正确时将抛出异常
     */
    public java.util.Date getDate(int colIndex) throws Exception {
        return ((java.util.Date[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
    }

    /**
     * 得到Date，不考虑 是 int ,long还是date的时间类型表示
     *
     * @param colIndex 列号
     * @return Date对象
     * @throws Exception 数组越限或类型转换出错
     */
    public java.util.Date getDateIgnoreType(int colIndex) throws Exception {
        DataTypeEnum type = colsInfo[colIndex - 1].type;
        java.util.Date retDate = null;
        switch (type) {
            case DtDateSecInt:
                retDate = DataSetUtil.secIntToDate(((int[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]]);
                break;
            case DtDateMsecLong:
                retDate = DataSetUtil.msecLongToDate(((long[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]]);
                break;
            case DtDate:
                retDate = ((java.util.Date[]) (cols[colIndex - 1]))[rawRowNos[curRowIndex]];
                break;
            default:
                throw new DataSetException("current row is not Date,DateSecInt or DateMsecLong type");
        }
        return retDate;
    }

    /**
     * 得到当前行的字符串表示
     *
     * @param colIndex 指定的列，从1开始编号
     * @return 单元格的字符串, 对double, float, date, dateSecInt, DateMsecLong将根据format转换，如果
     * format==null,则按照相应值的toString转换
     * @throws Exception 数组越限或类型转换出错
     */
    public String getString(int colIndex) throws Exception {
        Object retObj = getObject(colIndex);
        if (retObj == null) {
            return null;
        }
        DataTypeEnum type = colsInfo[colIndex - 1].type;
        String format = colsInfo[colIndex - 1].format;
        SimpleDateFormat dateFormat = colsInfo[colIndex - 1].dateFormat;
        DecimalFormat decFormat = colsInfo[colIndex - 1].decFormat;
        switch (type) {
            case DtDouble:
                if (format != null) {
                    return decFormat.format(((Double) retObj).doubleValue());
                } else {
                    return retObj.toString();
                }
            case DtFloat:
                if (format != null) {
                    return decFormat.format(((Float) retObj).floatValue());
                } else {
                    return retObj.toString();
                }
            case DtDateSecInt:
                if (format != null) {
                    return DataSetUtil.secIntToDateString(((Number) retObj).intValue(), dateFormat);
                } else {
                    return retObj.toString();
                }
            case DtDateMsecLong:
                if (format != null) {
                    return DataSetUtil.msecLongToDateString(((Number) retObj).longValue(), dateFormat);
                } else {
                    return retObj.toString();
                }
            case DtDate:
                if (format != null) {
                    return dateFormat.format((java.util.Date) retObj);
                } else {
                    return retObj.toString();
                }
            case DtObject:
                return ((ICellObject) retObj).toEncodingString();

            default:
                return retObj.toString();
        }
    }

    public Object getObject(int rowIndex, int colIndex) throws Exception {
        this.curRowIndex = rowIndex;
        return this.getObject(colIndex);
    }

    /**
     * 根据当前行的对象值
     *
     * @param colIndex 列号，从1开始
     * @return 列对象
     * @throws Exception 出现运行异常，如数组越限，类型转换错误等
     */
    public Object getObject(int colIndex) throws Exception {
        DataTypeEnum type = colsInfo[colIndex - 1].type;
        switch (type) {
            case DtInt:
                int intVal = this.getInt(colIndex);
                if (intVal == Integer.MIN_VALUE) {
                    return null;
                }
                return intVal;
            case DtDouble:
                double doubleVal = this.getDouble(colIndex);
                if (doubleVal == DataSetUtil.DOUBLE_MIN_VALUE) {
                    return null;
                }
                return doubleVal;
            case DtString:
            case DtXmlObj:
            case DtJsonObj:
                return this.getTypeString(colIndex);
            case DtByte:
                return this.getByte(colIndex);
            case DtShort:
                short shortValue = this.getShort(colIndex);
                if (shortValue == Short.MIN_VALUE) {
                    return null;
                }
                return shortValue;
            case DtBool:
                return this.getBool(colIndex);
            case DtByteArr:
                return this.getByteArr(colIndex);
            case DtFloat:
                float floatVal = this.getFloat(colIndex);
                if (floatVal == DataSetUtil.FLOAT_MIN_VALUE) {
                    return null;
                }
                return floatVal;
            case DtLong:
                long longVal = this.getLong(colIndex);
                if (longVal == Long.MIN_VALUE) {
                    return null;
                }
                return longVal;
            case DtDateSecInt:
                int intSecVal = this.getInt(colIndex);
                if (intSecVal == Integer.MIN_VALUE) {
                    return null;
                }
                return intSecVal;
            case DtDateMsecLong:
                long longMsVal = this.getLong(colIndex);
                if (longMsVal == Long.MIN_VALUE) {
                    return null;
                }
                return longMsVal;
            case DtDate:
                return this.getDate(colIndex);
            case DtObject:
                return this.getCellObject(colIndex);
        }
        throw new DataSetException("Error data Type");
    }

    /**
     * 得到当前行的全部对象数组
     *
     * @return 当前行对象数组
     * @throws Exception 出现运行异常，如数组越限，类型转换错误等
     */
    public Object[] getObjects() throws Exception {
        Object[] retObjArr = new Object[this.colSize];
        for (int jj = 0; jj < this.colSize; jj++) {
            retObjArr[jj] = getObject(jj + 1);
        }
        return retObjArr;
    }

    /**
     * 得到当前行的全部对象的字符串表示
     *
     * @return 当前行字符串数组
     * @throws Exception 出现运行异常，如数组越限，类型转换错误等
     */
    public String[] getStrings() throws Exception {
        String[] retObjArr = new String[this.colSize];
        for (int jj = 0; jj < this.colSize; jj++) {
            retObjArr[jj] = getString(jj + 1);
        }
        return retObjArr;
    }

    public int getColSize() {
        return colSize;
    }

    public ColInfo getColInfo(int colIndex) {
        if (colIndex < 0 || colIndex > colsInfo.length) {
            return null;
        }
        return this.colsInfo[colIndex];
    }

    public int getCurRowIndex() {
        return curRowIndex;
    }

    public int getDataTableRowIndex(int rowIndex) {
        return rawRowNos[rowIndex];
    }

}
