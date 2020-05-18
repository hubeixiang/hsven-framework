package org.framework.hsven.dataset.datatable.calcfun;


import org.framework.hsven.dataset.datatable.DataSetUtil;
import org.framework.hsven.dataset.datatable.api.ICellObject;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

import java.text.SimpleDateFormat;


public final class EqualCalcFun {

    /**
     * 禁止创建本类的实例
     */
    private EqualCalcFun() {
    }

    /**
     * 无实际用途，主要用来提前激活本对象，以节省在调用本地函数首次创建对象时的时间
     */
    public static void active() {
    }

    /**
     * 判断对象是否为null
     *
     * @param value 待判断的Object对象
     * @return true:是null对象，false:非null对象
     */
    public static boolean isNullObject(Object value) {
        if (value == null) {
            return true;
        } else {
            if (value instanceof String) {
                if (((String) value).trim().equalsIgnoreCase("null")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 设置当前选择对象数组为全部选择
     *
     * @param indexArr 选中数组
     * @return 取消选择次数
     */
    public static int setAllUnSelected(boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                indexArr[jj] = false;
                count++;
            }
        }
        return count;
    }


    /**
     * 判断对象是否为 Number
     *
     * @param value 对象
     * @return true 是数字， false 不是数字
     */
    public static boolean isNumber(Object value) {
        try {
            Number num = ((Number) value);
            return true;
        } catch (Exception ee) {
            return false;
        }
    }

    /**
     * 是否为整数
     *
     * @param value
     * @return true 是， false 不是
     */
    public static boolean isIntNum(Object value) {
        Number num = (Number) value;
        return (num.longValue() == num.doubleValue());
    }


    //for int[]
    public static int eq(int[] colValArr, Object value, boolean[] indexArr) {
        int val = 0;
        int count = 0;
        if (isNullObject(value)) {
            val = Integer.MIN_VALUE;
        } else {
            if (!isNumber(value)) {
                throw new DataSetException("Param is not a number");
            }
            if (!isIntNum(value)) {
                return setAllUnSelected(indexArr);
            } else {
                val = ((Number) value).intValue();
            }
        }
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] != val) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    //for double[]
    public static int eq(double[] colValArr, Object value, boolean[] indexArr) {
        double val = 0;
        int count = 0;
        if (isNullObject(value)) {
            val = DataSetUtil.DOUBLE_MIN_VALUE;
        } else {
            if (!isNumber(value)) {
                throw new DataSetException("Param is not a number");
            }
            val = ((Number) value).doubleValue();
        }
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] != val) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    //for float[]
    public static int eq(float[] colValArr, Object value, boolean[] indexArr) {
        float val = 0;
        int count = 0;
        if (isNullObject(value)) {
            val = DataSetUtil.FLOAT_MIN_VALUE;
        } else {
            if (!isNumber(value)) {
                throw new DataSetException("Param is not a number");
            }
            val = ((Number) value).floatValue();
        }
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] != val) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    //for long[]
    public static int eq(long[] colValArr, Object value, boolean[] indexArr) {
        long val = 0;
        int count = 0;
        if (isNullObject(value)) {
            val = Long.MIN_VALUE;
        } else {
            if (!isNumber(value)) {
                throw new DataSetException("Param is not a number");
            }
            if (!isIntNum(value)) {
                return setAllUnSelected(indexArr);
            } else {
                val = ((Number) value).longValue();
            }
        }
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] != val) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    //for short[]
    public static int eq(short[] colValArr, Object value, boolean[] indexArr) {
        int val = 0;
        int count = 0;
        if (isNullObject(value)) {
            throw new DataSetException("Short equal,Param can not be null.");
        } else {
            if (!isNumber(value)) {
                throw new DataSetException("Param is not a number");
            }
            if (!isIntNum(value)) {
                return setAllUnSelected(indexArr);
            } else {
                val = ((Number) value).intValue();
            }
        }
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] != val) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    //for byte[]
    public static int eq(byte[] colValArr, Object value, boolean[] indexArr) {
        int val = 0;
        int count = 0;
        if (isNullObject(value)) {
            throw new DataSetException("Byte equal,Param can not be null.");
        } else {
            if (!isNumber(value)) {
                throw new DataSetException("Param is not a number");
            }
            if (!isIntNum(value)) {
                return setAllUnSelected(indexArr);
            } else {
                val = ((Number) value).intValue();
            }
        }
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] != val) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    //for boolean[]
    public static int eq(boolean[] colValArr, Object value, boolean[] indexArr) {
        boolean val = false;
        int count = 0;
        if (isNullObject(value)) {
            throw new DataSetException("boolean equal,Param can not be null.");
        } else {
            if (value instanceof Boolean) {
                val = ((Boolean) value).booleanValue();
            }
            if (value instanceof String) {
                val = ((String) value).trim().equalsIgnoreCase("true");
            }
        }
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] != val) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }


    //for String[]
    public static int eq(String[] colValArr, Object value, boolean[] indexArr) {
        String val = null;
        int count = 0;
        if (value != null) {
            if (!(value instanceof String)) {
                throw new DataSetException("String equal,error param data type");
            }
            val = (String) value;
        }
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] == null) {
                    if (val != null) {
                        indexArr[jj] = false;
                        count++;
                    }
                    ;
                } else {
                    if (val == null) {
                        indexArr[jj] = false;
                        count++;
                    } else {
                        if (!colValArr[jj].equals(val)) {
                            indexArr[jj] = false;
                            count++;
                        }
                        ;
                    }
                }
            }
        }
        return count;
    }

    //for Date[]
    public static int eq(java.util.Date[] colValArr, java.util.Date value, boolean[] indexArr) {
        java.util.Date val = value;
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] == null) {
                    if (val != null) {
                        indexArr[jj] = false;
                        count++;
                    }
                    ;
                } else {
                    if (val == null) {
                        indexArr[jj] = false;
                        count++;
                    } else {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String clovalStr = formatter.format(colValArr[jj]);
                        String valStr = formatter.format(val);
                        if (!clovalStr.equals(valStr)) {
                            indexArr[jj] = false;
                            count++;
                        }
//						if(!colValArr[jj].equals(val))
//						{ indexArr[jj]=false; count++; };	
                    }
                }
            }
        }
        return count;
    }

    //for ICellObject[]
    public static int eq(ICellObject[] colValArr, Object value, boolean[] indexArr) {
        Object val = value;
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] == null) {
                    if (val != null) {
                        indexArr[jj] = false;
                        count++;
                    }
                    ;
                } else {
                    if (val == null) {
                        indexArr[jj] = false;
                        count++;
                    } else {
                        if (colValArr[jj].compare(val) != 0) {
                            indexArr[jj] = false;
                            count++;
                        }
                        ;
                    }
                }
            }
        }
        return count;
    }


}
