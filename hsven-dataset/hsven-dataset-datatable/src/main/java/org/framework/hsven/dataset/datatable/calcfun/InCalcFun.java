package org.framework.hsven.dataset.datatable.calcfun;


import org.framework.hsven.dataset.datatable.DataSetUtil;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class InCalcFun {
    /**
     * 禁止创建本类的实例
     */
    private InCalcFun() {
    }

    /**
     * 无实际用途，主要用来提前激活本对象，以节省在调用本地函数首次创建对象时的时间
     */
    public static void active() {
    }

    private static String[] stringToArr(String lineStr) {
        if (lineStr == null) {
            return null;
        }
        String tmpStr = lineStr.trim();
        if (tmpStr.length() == 0) {
            return null;
        }
        return tmpStr.split(",");
    }

    public static <T> void printArr(T[] array) {
        StringBuffer sb = new StringBuffer();
        sb.append("Length=" + array.length + " value=");
        for (int jj = 0; jj < array.length; jj++) {
            sb.append(array[jj]).append(",");
        }
        System.out.println(sb);

    }

    public static void printArr(int[] array) {
        StringBuffer sb = new StringBuffer();
        sb.append("Length=" + array.length + " value=");
        for (int jj = 0; jj < array.length; jj++) {
            sb.append(array[jj]).append(",");
        }
        System.out.println(sb);

    }

    public static void printArr(double[] array) {
        StringBuffer sb = new StringBuffer();
        sb.append("Length=" + array.length + " value=");
        for (int jj = 0; jj < array.length; jj++) {
            sb.append(array[jj]).append(",");
        }
        System.out.println(sb);

    }

    /**
     * 字符串转化成排序后的int数组
     *
     * @param lineStr 逗号分隔字符串
     * @return 排序后的int数组，错误或长度为0时均返回null
     */
    public static int[] stringToIntArr(String lineStr) {
        String[] tmpArr = stringToArr(lineStr);
        if (tmpArr == null) {
            return null;
        }
        int[] valArr = new int[tmpArr.length];
        int count = 0;
        String tmpStr = null;
        for (int jj = 0; jj < tmpArr.length; jj++) {
            tmpStr = tmpArr[jj].trim();
            if (tmpStr.length() == 0) {
                continue;
            }
            if (tmpStr.equalsIgnoreCase("null")) {
                valArr[count] = Integer.MIN_VALUE;
            } else {
                try {
                    valArr[count] = Integer.parseInt(tmpStr);
                } catch (Exception ee) {
                    return null;
                }
            }
            count++;
        }
        if (count == 0) {
            return null;
        }
        if (count == valArr.length) {
            Arrays.sort(valArr);
            return valArr;
        }
        int[] retArr = Arrays.copyOf(valArr, count);
        Arrays.sort(retArr);
        return retArr;
    }

    /**
     * 字符串转化成排序后的double数组
     *
     * @param lineStr 逗号分隔字符串
     * @return 排序后的double数组，错误或长度为0时均返回null
     */
    public static double[] stringToDoubleArr(String lineStr) {
        String[] tmpArr = stringToArr(lineStr);
        if (tmpArr == null) {
            return null;
        }
        double[] valArr = new double[tmpArr.length];
        int count = 0;
        String tmpStr = null;
        for (int jj = 0; jj < tmpArr.length; jj++) {
            tmpStr = tmpArr[jj].trim();
            if (tmpStr.length() == 0) {
                continue;
            }
            if (tmpStr.equalsIgnoreCase("null")) {
                valArr[count] = DataSetUtil.DOUBLE_MIN_VALUE;
            } else {
                try {
                    valArr[count] = Double.parseDouble(tmpStr);
                } catch (Exception ee) {
                    return null;
                }
            }
            count++;
        }
        if (count == 0) {
            return null;
        }
        if (count == valArr.length) {
            Arrays.sort(valArr);
            return valArr;
        }
        double[] retArr = Arrays.copyOf(valArr, count);
        Arrays.sort(retArr);
        return retArr;
    }

    /**
     * 字符串转化成排序后的double数组
     *
     * @param lineStr 逗号分隔字符串
     * @return 排序后的double数组，错误或长度为0时均返回null
     */
    public static String[] stringToStringArr(String lineStr) {
        String[] tmpArr = stringToArr(lineStr);
        if (tmpArr == null) {
            return null;
        }
        String[] valArr = new String[tmpArr.length];
        String tmpStr = null;
        for (int jj = 0; jj < tmpArr.length; jj++) {
            tmpStr = tmpArr[jj].trim();
            if (tmpStr.equalsIgnoreCase("null")) {
                valArr[jj] = "null";
            } else {
                valArr[jj] = tmpStr;
            }
        }
        Arrays.sort(valArr);
        return valArr;
    }

    /**
     * 字符串转化成排序后的float数组
     *
     * @param lineStr 逗号分隔字符串
     * @return 排序后的float数组，错误或长度为0时均返回null
     */
    public static float[] stringToFloatArr(String lineStr) {
        String[] tmpArr = stringToArr(lineStr);
        if (tmpArr == null) {
            return null;
        }
        float[] valArr = new float[tmpArr.length];
        int count = 0;
        String tmpStr = null;
        for (int jj = 0; jj < tmpArr.length; jj++) {
            tmpStr = tmpArr[jj].trim();
            if (tmpStr.length() == 0) {
                continue;
            }
            if (tmpStr.equalsIgnoreCase("null")) {
                valArr[count] = DataSetUtil.FLOAT_MIN_VALUE;
            } else {
                try {
                    valArr[count] = Float.parseFloat(tmpStr);
                } catch (Exception ee) {
                    return null;
                }
            }
            count++;
        }
        if (count == 0) {
            return null;
        }
        if (count == valArr.length) {
            Arrays.sort(valArr);
            return valArr;
        }
        float[] retArr = Arrays.copyOf(valArr, count);
        Arrays.sort(retArr);
        return retArr;
    }

    /**
     * 字符串转化成排序后的short数组
     *
     * @param lineStr 逗号分隔字符串
     * @return 排序后的short数组，错误或长度为0时均返回null
     */
    public static short[] stringToShortArr(String lineStr) {
        String[] tmpArr = stringToArr(lineStr);
        if (tmpArr == null) {
            return null;
        }
        short[] valArr = new short[tmpArr.length];
        int count = 0;
        String tmpStr = null;
        for (int jj = 0; jj < tmpArr.length; jj++) {
            tmpStr = tmpArr[jj].trim();
            if (tmpStr.length() == 0) {
                continue;
            }
            if (tmpStr.equalsIgnoreCase("null")) {
                valArr[count] = Short.MIN_VALUE;
            } else {
                try {
                    valArr[count] = Short.parseShort(tmpStr);
                } catch (Exception ee) {
                    return null;
                }
            }
            count++;
        }
        if (count == 0) {
            return null;
        }
        if (count == valArr.length) {
            Arrays.sort(valArr);
            return valArr;
        }
        short[] retArr = Arrays.copyOf(valArr, count);
        Arrays.sort(retArr);
        return retArr;
    }

    /**
     * 字符串转化成排序后的byte数组
     *
     * @param lineStr 逗号分隔字符串
     * @return 排序后的byte数组，错误或长度为0时均返回null
     */
    public static byte[] stringToByteArr(String lineStr) {
        String[] tmpArr = stringToArr(lineStr);
        if (tmpArr == null) {
            return null;
        }
        byte[] valArr = new byte[tmpArr.length];
        int count = 0;
        String tmpStr = null;
        for (int jj = 0; jj < tmpArr.length; jj++) {
            tmpStr = tmpArr[jj].trim();
            if (tmpStr.length() == 0) {
                continue;
            }
            if (tmpStr.equalsIgnoreCase("null")) {
                valArr[count] = Byte.MIN_VALUE;
            } else {
                try {
                    valArr[count] = Byte.parseByte(tmpStr);
                } catch (Exception ee) {
                    return null;
                }
            }
            count++;
        }
        if (count == 0) {
            return null;
        }
        if (count == valArr.length) {
            Arrays.sort(valArr);
            return valArr;
        }
        byte[] retArr = Arrays.copyOf(valArr, count);
        Arrays.sort(retArr);
        return retArr;
    }

    /**
     * 字符串转化成排序后的long数组
     *
     * @param lineStr 逗号分隔字符串
     * @return 排序后的long数组，错误或长度为0时均返回null
     */
    public static long[] stringToLongArr(String lineStr) {
        String[] tmpArr = stringToArr(lineStr);
        if (tmpArr == null) {
            return null;
        }
        long[] valArr = new long[tmpArr.length];
        int count = 0;
        String tmpStr = null;
        for (int jj = 0; jj < tmpArr.length; jj++) {
            tmpStr = tmpArr[jj].trim();
            if (tmpStr.length() == 0) {
                continue;
            }
            if (tmpStr.equalsIgnoreCase("null")) {
                valArr[count] = Short.MIN_VALUE;
            } else {
                try {
                    valArr[count] = Long.parseLong(tmpStr);
                } catch (Exception ee) {
                    return null;
                }
            }
            count++;
        }
        if (count == 0) {
            return null;
        }
        if (count == valArr.length) {
            Arrays.sort(valArr);
            return valArr;
        }
        long[] retArr = Arrays.copyOf(valArr, count);
        Arrays.sort(retArr);
        return retArr;
    }

    /**
     * 转化可能的时间格式字符串到long 的串
     *
     * @param lineStr 逗号分隔字符串
     * @return long数组的字符串表示，错误或长度为0时均返回null
     */
    public static String dateStringToNumberStr(String lineStr, SimpleDateFormat dateFormat, boolean intOrLong) {
        String[] tmpArr = stringToArr(lineStr);
        if (tmpArr == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        int count = 0;
        String tmpStr = null;
        for (int jj = 0; jj < tmpArr.length; jj++) {
            tmpStr = tmpArr[jj].trim();
            if (tmpStr.length() == 0) {
                continue;
            }
            String item = toDateNumberString(tmpStr, dateFormat, intOrLong);
            if (item == null) {
                continue;
            }
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(item);
            count++;
        }
        if (count == 0) {
            return null;
        }
        return sb.toString();
    }

    /**
     * 由字符串格式得到 long 的字符串表示
     *
     * @param item       字符串表示，可以为 12345,12345L,'2013-12-13 12:00:00' 等格式
     * @param dateFormat 时间表示
     * @return null,"null", "12345","12345","13021212121"
     */
    public static String toDateNumberString(String item, SimpleDateFormat dateFormat, boolean intOrLong) {
        String theItem = item.toLowerCase().trim();
        if (theItem.equals("null")) {
            return "null";
        }
        if (theItem.endsWith("l")) {
            theItem = theItem.substring(0, theItem.length() - 1);
            try {
                long val = Long.parseLong(theItem);
                if (intOrLong) {
                    val = val / 1000;
                }
                return "" + val;
            } catch (Exception ee) {
                return null;
            }
        }

        // 判断是否为整数
        try {
            int val = Integer.parseInt(theItem);
            long valLong = val * 1000L;
            if (intOrLong) {
                return "" + val;
            } else {
                return "" + valLong;
            }
        } catch (Exception ee) {
        }
        // 判断是否为时间格式
        long val = DataSetUtil.objectToDateMsecLong(item, dateFormat);
        if (val == Long.MIN_VALUE) {
            return null;
        }
        if (intOrLong) {
            val = val / 1000;
        }
        return "" + val;
    }

    /**
     * 字符串转化成排序后的Date数组
     *
     * @param lineStr 逗号分隔字符串
     * @return 排序后的Date数组，错误或长度为0时均返回null
     */
    public static Date[] stringToDateArr(String lineStr, SimpleDateFormat dateFormat) {
        String[] tmpArr = stringToArr(lineStr);
        if (tmpArr == null) {
            return null;
        }
        Date[] valArr = new Date[tmpArr.length];
        int count = 0;
        String tmpStr = null;
        for (int jj = 0; jj < tmpArr.length; jj++) {
            tmpStr = tmpArr[jj].trim();
            if (tmpStr.length() == 0) {
                continue;
            }
            try {
                valArr[count] = DataSetUtil.objectToDate(tmpStr, dateFormat);
            } catch (Exception ee) {
                return null;
            }
            count++;
        }
        if (count == 0) {
            return null;
        }
        if (count == valArr.length) {
            Arrays.sort(valArr);
            return valArr;
        }
        Date[] retArr = Arrays.copyOf(valArr, count);
        Arrays.sort(retArr);
        return retArr;
    }

    // for int[]
    public static int in(int[] colValArr, String value, boolean[] indexArr) {
        int[] valArr;
        if (value == null || value.length() == 0) {
            valArr = new int[1];
            valArr[0] = Integer.MIN_VALUE;
        } else {
            valArr = stringToIntArr(value);
        }
        if (valArr == null) {
            throw new DataSetException("Param is error or be set to null");
        }
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (Arrays.binarySearch(valArr, colValArr[jj]) < 0) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    // for double[]
    public static int in(double[] colValArr, String value, boolean[] indexArr) {
        double[] valArr;
        if (value == null || value.length() == 0) {
            valArr = new double[1];
            valArr[0] = DataSetUtil.DOUBLE_MIN_VALUE;
        } else {
            valArr = stringToDoubleArr(value);
        }
        if (valArr == null) {
            throw new DataSetException("Param is error or be set to null");
        }
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (Arrays.binarySearch(valArr, colValArr[jj]) < 0) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    // for string[]
    public static int in(String[] colValArr, String value, boolean[] indexArr) {

        String[] valArr;
        if (value == null || value.length() == 0) {
            valArr = new String[1];
            valArr[0] = "null";
        } else {
            valArr = stringToStringArr(value);
        }
        if (valArr == null) {
            throw new DataSetException("Param is error or be set to null");
        }
        int count = 0;
        int size = indexArr.length;
        String tmpStr = null;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                tmpStr = colValArr[jj];
                if (tmpStr == null) {
                    tmpStr = "null";
                }
                if (Arrays.binarySearch(valArr, tmpStr) < 0) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    // for float[]
    public static int in(float[] colValArr, String value, boolean[] indexArr) {
        float[] valArr;
        if (value == null || value.length() == 0) {
            valArr = new float[1];
            valArr[0] = DataSetUtil.FLOAT_MIN_VALUE;
        } else {
            valArr = stringToFloatArr(value);
        }
        if (valArr == null) {
            throw new DataSetException("Param is error or be set to null");
        }
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (Arrays.binarySearch(valArr, colValArr[jj]) < 0) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    // for short[]
    public static int in(short[] colValArr, String value, boolean[] indexArr) {
        short[] valArr;
        if (value == null || value.length() == 0) {
            valArr = new short[1];
            valArr[0] = Short.MIN_VALUE;
        } else {
            valArr = stringToShortArr(value);
        }

        if (valArr == null) {
            throw new DataSetException("Param is error or be set to null");
        }
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (Arrays.binarySearch(valArr, colValArr[jj]) < 0) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    // for byte[]
    public static int in(byte[] colValArr, String value, boolean[] indexArr) {
        byte[] valArr;
        if (value == null || value.length() == 0) {
            valArr = new byte[1];
            valArr[0] = Byte.MIN_VALUE;
        } else {
            valArr = stringToByteArr(value);
        }
        if (valArr == null) {
            throw new DataSetException("Param is error or be set to null");
        }
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (Arrays.binarySearch(valArr, colValArr[jj]) < 0) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    // for long[]
    public static int in(long[] colValArr, String value, boolean[] indexArr) {

        long[] valArr;
        if (value == null || value.length() == 0) {
            valArr = new long[1];
            valArr[0] = Long.MIN_VALUE;
        } else {
            valArr = stringToLongArr(value);
        }
        if (valArr == null) {
            throw new DataSetException("Param is error or be set to null");
        }
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (Arrays.binarySearch(valArr, colValArr[jj]) < 0) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    // for date[]
    public static int in(Date[] colValArr, String value, boolean[] indexArr, SimpleDateFormat dateFormat) {

        Date[] valArr;
        if (value == null || value.length() == 0) {
            valArr = new Date[1];
            valArr[0] = null;
        } else {
            valArr = stringToDateArr(value, dateFormat);
        }
        if (valArr == null) {
            throw new DataSetException("Param is error or be set to null");
        }
        int count = 0;
        int size = indexArr.length;
        for (int jj = 0; jj < size; jj++) {
            if (indexArr[jj]) {
                if (colValArr[jj] == null || Arrays.binarySearch(valArr, colValArr[jj]) < 0) {
                    indexArr[jj] = false;
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        String line = "198,2221,  12,  ,null,  22121  \t\t\n \t ,  212  ";

        String lineStr = "198,2221,  但是12,null,  22121  \t\t\n \t ,  212  ";

        int[] dd = stringToIntArr(line);
        double[] df = stringToDoubleArr(line);
        String[] ds = stringToStringArr(lineStr);
        printArr(dd);
        printArr(df);
        printArr(ds);
    }

}
