package org.framework.hsven.dataset.datatable.calcfun;


import org.framework.hsven.dataset.datatable.DataSetUtil;

public final class StringCalcFun {
    /**
     * 禁止创建本类的实例
     */
    private StringCalcFun() {
    }

    /**
     * 无实际用途，主要用来提前激活本对象，以节省在调用本地函数首次创建对象时的时间
     */
    public static void active() {
    }

    public static int like(String[] colValArr, String likeStr, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        String likeExpr = DataSetUtil.toLikeString(likeStr);
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] == null) {
                    indexArr[jj] = false;
                    count++;
                } else {
                    if (!(colValArr[jj].matches(likeExpr))) {
                        indexArr[jj] = false;
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int like(int[] colValArr, String likeStr, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        String likeExpr = DataSetUtil.toLikeString(likeStr);
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                String cloValTemp = null;
                if (colValArr[jj] == Integer.MIN_VALUE) {
                    cloValTemp = "";
                } else {
                    cloValTemp = colValArr[jj] + "";
                }

                if (cloValTemp.length() == 0) {
                    indexArr[jj] = false;
                    count++;
                } else {
                    if (!(cloValTemp.matches(likeExpr))) {
                        indexArr[jj] = false;
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int like(long[] colValArr, String likeStr, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        String likeExpr = DataSetUtil.toLikeString(likeStr);
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                String cloValTemp = null;
                if (colValArr[jj] == Long.MIN_VALUE) {
                    cloValTemp = "";
                } else {
                    cloValTemp = colValArr[jj] + "";
                }
                if (cloValTemp.length() == 0) {
                    indexArr[jj] = false;
                    count++;
                } else {
                    if (!(cloValTemp.matches(likeExpr))) {
                        indexArr[jj] = false;
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int like(short[] colValArr, String likeStr, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        String likeExpr = DataSetUtil.toLikeString(likeStr);
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                String cloValTemp = null;
                if (colValArr[jj] == Short.MIN_VALUE) {
                    cloValTemp = "";
                } else {
                    cloValTemp = colValArr[jj] + "";
                }
                if (cloValTemp.length() == 0) {
                    indexArr[jj] = false;
                    count++;
                } else {
                    if (!(cloValTemp.matches(likeExpr))) {
                        indexArr[jj] = false;
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int like(byte[] colValArr, String likeStr, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        String likeExpr = DataSetUtil.toLikeString(likeStr);
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                String cloValTemp = null;
                if (colValArr[jj] == Byte.MIN_VALUE) {
                    cloValTemp = "";
                } else {
                    cloValTemp = colValArr[jj] + "";
                }
                if (cloValTemp.length() == 0) {
                    indexArr[jj] = false;
                    count++;
                } else {
                    if (!(cloValTemp.matches(likeExpr))) {
                        indexArr[jj] = false;
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int like(float[] colValArr, String likeStr, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        String likeExpr = DataSetUtil.toLikeString(likeStr);
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                String cloValTemp = null;
                if (Math.abs(colValArr[jj] - DataSetUtil.FLOAT_MIN_VALUE) == 0) {
                    cloValTemp = "";
                } else {
                    cloValTemp = colValArr[jj] + "";
                }
                if (cloValTemp.length() == 0) {
                    indexArr[jj] = false;
                    count++;
                } else {
                    if (!(cloValTemp.matches(likeExpr))) {
                        indexArr[jj] = false;
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int like(double[] colValArr, String likeStr, boolean[] indexArr) {
        int count = 0;
        int size = indexArr.length;
        String likeExpr = DataSetUtil.toLikeString(likeStr);
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                String cloValTemp = null;
                if (Math.abs(colValArr[jj] - DataSetUtil.DOUBLE_MIN_VALUE) == 0) {
                    cloValTemp = "";
                } else {
                    cloValTemp = colValArr[jj] + "";
                }
                if (cloValTemp.length() == 0) {
                    indexArr[jj] = false;
                    count++;
                } else {
                    if (!(cloValTemp.matches(likeExpr))) {
                        indexArr[jj] = false;
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
