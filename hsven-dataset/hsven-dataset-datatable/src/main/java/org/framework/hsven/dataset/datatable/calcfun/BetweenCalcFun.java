package org.framework.hsven.dataset.datatable.calcfun;


import org.framework.hsven.dataset.datatable.exception.DataSetException;

public final class BetweenCalcFun {
    /**
     * 禁止创建本类的实例
     */
    private BetweenCalcFun() {
    }

    /**
     * 无实际用途，主要用来提前激活本对象，以节省在调用本地函数首次创建对象时的时间
     */
    public static void active() {
    }

    public static int bw(double[] colValArr, double minVal, double maxVal, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (!(colValArr[jj] >= minVal && colValArr[jj] < maxVal)) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    public static int bw(int[] colValArr, double minVal, double maxVal, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (!(colValArr[jj] >= minVal && colValArr[jj] < maxVal)) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    public static int bw(short[] colValArr, double minVal, double maxVal, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (!(colValArr[jj] >= minVal && colValArr[jj] < maxVal)) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    public static int bw(float[] colValArr, double minVal, double maxVal, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (!(colValArr[jj] >= minVal && colValArr[jj] < maxVal)) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    public static int bw(long[] colValArr, double minVal, double maxVal, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (!(colValArr[jj] >= minVal && colValArr[jj] < maxVal)) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    public static int bw(byte[] colValArr, double minVal, double maxVal, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (!(colValArr[jj] >= minVal && colValArr[jj] < maxVal)) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    public static int bwDate(java.util.Date[] colValArr, java.util.Date minVal, java.util.Date maxVal, boolean[] indexArr) {
        if (minVal == null || maxVal == null) {
            throw new DataSetException("between date param can not be null");
        }

        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] == null) {
                    indexArr[jj] = false;
                    count++;
                    continue;
                }
                if (!(colValArr[jj].compareTo(minVal) >= 0 && colValArr[jj].compareTo(maxVal) < 0)) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    public static int bw(String[] colValArr, String minVal, String maxVal, boolean[] indexArr) {
        if (minVal == null || maxVal == null) {
            throw new DataSetException("between date param can not be null");
        }

        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] == null) {
                    indexArr[jj] = false;
                    count++;
                    continue;
                }
                if (!(colValArr[jj].compareTo(minVal) >= 0 && colValArr[jj].compareTo(maxVal) < 0)) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

}
