package org.framework.hsven.dataset.datatable.calcfun;

import org.framework.hsven.dataset.datatable.DataSetUtil;
import org.framework.hsven.dataset.datatable.beans.OperatorTypeEnum;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

public final class LogicCalcFun {
    /**
     * 禁止创建本类的实例
     */
    private LogicCalcFun() {
    }

    /**
     * 无实际用途，主要用来提前激活本对象，以节省在调用本地函数首次创建对象时的时间
     */
    public static void active() {
    }

    //for int[]
    public static int logicCalcGtGeLtLe(int[] colValArr, Object value, boolean[] indexArr, OperatorTypeEnum opType) {
        double val = ((Number) value).doubleValue();
        int size = indexArr.length;
        int count = 0;
        switch (opType) {
            case OtGt:        //gt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] <= val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtGe:        //ge
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] < val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLt:        //lt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] >= val || colValArr[jj] == Integer.MIN_VALUE) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLe:        //le
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] > val || colValArr[jj] == Integer.MIN_VALUE) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
        } //end of switch

        return count;
    }

    //for long[]
    public static int logicCalcGtGeLtLe(long[] colValArr, Object value, boolean[] indexArr, OperatorTypeEnum opType) {
        double val = ((Number) value).doubleValue();
        int size = indexArr.length;
        int count = 0;
        switch (opType) {
            case OtGt:        //gt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] <= val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtGe:        //ge
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] < val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLt:        //lt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] >= val || colValArr[jj] == Long.MIN_VALUE) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLe:        //le
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] > val || colValArr[jj] == Long.MIN_VALUE) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
        } //end of switch

        return count;
    }


    //for double[]
    public static int logicCalcGtGeLtLe(double[] colValArr, Object value, boolean[] indexArr, OperatorTypeEnum opType) {
        double val = ((Number) value).doubleValue();
        int size = indexArr.length;
        int count = 0;
        switch (opType) {
            case OtGt: // gt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] <= val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtGe: // ge
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] < val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLt: // lt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] >= val || colValArr[jj] == DataSetUtil.DOUBLE_MIN_VALUE) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLe: // le
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] > val || colValArr[jj] == DataSetUtil.DOUBLE_MIN_VALUE) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
        } // end of switch
        return count;
    }


    //for float[]
    public static int logicCalcGtGeLtLe(float[] colValArr, Object value, boolean[] indexArr, OperatorTypeEnum opType) {
        double val = ((Number) value).doubleValue();
        int size = indexArr.length;
        int count = 0;
        switch (opType) {
            case OtGt: // gt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] <= val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtGe: // ge
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] < val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLt: // lt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] >= val || colValArr[jj] == DataSetUtil.FLOAT_MIN_VALUE) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLe: // le
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] > val || colValArr[jj] == DataSetUtil.FLOAT_MIN_VALUE) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
        } // end of switch
        return count;
    }

    //for byte[]
    public static int logicCalcGtGeLtLe(byte[] colValArr, Object value, boolean[] indexArr, OperatorTypeEnum opType) {
        double val = ((Number) value).doubleValue();
        int size = indexArr.length;
        int count = 0;
        switch (opType) {
            case OtGt: // gt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] <= val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtGe: // ge
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] < val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLt: // lt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] >= val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLe: // le
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] > val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
        } // end of switch
        return count;
    }

    //for short[]
    public static int logicCalcGtGeLtLe(short[] colValArr, Object value, boolean[] indexArr, OperatorTypeEnum opType) {
        double val = ((Number) value).doubleValue();
        int size = indexArr.length;
        int count = 0;
        switch (opType) {
            case OtGt: // gt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] <= val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtGe: // ge
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] < val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLt: // lt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] >= val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLe: // le
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] > val) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
        } // end of switch
        return count;
    }

    //for string[]
    public static int logicCalcGtGeLtLe(String[] colValArr, Object value, boolean[] indexArr, OperatorTypeEnum opType) {
        if (value == null) {
            throw new DataSetException("param can not be set to null.");
        }
        if (!(value instanceof String)) {
            throw new DataSetException("param must be String type.");
        }
        String val = (String) value;
        int size = indexArr.length;
        int count = 0;
        switch (opType) {
            case OtGt: // gt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] == null || val.compareTo(colValArr[jj]) <= 0) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtGe: // ge
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] == null || val.compareTo(colValArr[jj]) < 0) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLt: // lt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] == null || val.compareTo(colValArr[jj]) >= 0) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLe: // le
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] == null || val.compareTo(colValArr[jj]) > 0) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
        } // end of switch
        return count;
    }

    //for Date[]
    public static int logicCalcGtGeLtLe(java.util.Date[] colValArr, java.util.Date value, boolean[] indexArr, OperatorTypeEnum opType) {
        if (value == null) {
            throw new DataSetException("param can not be set to null.");
        }
        java.util.Date val = value;

        int size = indexArr.length;
        int count = 0;
        switch (opType) {
            case OtGt: // gt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] == null || colValArr[jj].compareTo(val) <= 0) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtGe: // ge
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] == null || colValArr[jj].compareTo(val) < 0) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLt: // lt
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] == null || colValArr[jj].compareTo(val) >= 0) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
            case OtLe: // le
            {
                for (int jj = 0; jj < size; jj++) {
                    if (indexArr[jj]) {
                        if (colValArr[jj] == null || colValArr[jj].compareTo(val) > 0) {
                            indexArr[jj] = false;
                            count++;
                        }
                    }
                }
                break;
            }
        } // end of switch
        return count;
    }

}
