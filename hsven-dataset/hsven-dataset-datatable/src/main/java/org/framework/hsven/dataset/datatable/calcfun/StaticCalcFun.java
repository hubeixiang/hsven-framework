package org.framework.hsven.dataset.datatable.calcfun;


import org.framework.hsven.dataset.datatable.DataSetUtil;

import java.text.SimpleDateFormat;

public class StaticCalcFun {

    /**
     * 得到当前最新时间，非幂等性函数
     *
     * @return 当前时间
     */
    public static java.util.Date now() {
        return new java.util.Date();
    }


    /**
     * 计算当前时间的相对时间 ，非幂等性函数
     *
     * @param day 相对时间的日表示  1表示一天，  -1表示减去1天， 1天表示加1天， 1/24 表示1个小时
     *            语法同oracle语法
     * @return 当前时间相对于当前时间的运算表示表示
     */
    public static java.util.Date date(double day) {
        long msVal = (long) (day * 1000L * 24 * 3600L);
        long curMs = System.currentTimeMillis() + msVal;
        return new java.util.Date(curMs);
    }


    /**
     * 得到当前时间对象java.util.Date 对象，等同于oracle 的 to_date函数
     *
     * @param dateObj
     * @param format
     * @return 当前时间对象java.util.Date 对象
     */
    public static java.util.Date to_date(Object dateObj, String format) {
        String tmpFmt = format;
        if (tmpFmt == null) tmpFmt = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(tmpFmt);
        return DataSetUtil.objectToDate(dateObj, dateFormat);
    }


    /**
     * 在当前时间上加上 day天
     *
     * @param dateObj 当前时间对象，可以是 java.util.Date，也可以是字符串 "yyyy-MM-dd HH:mm:ss" 格式
     * @param day     相对时间的日表示  1表示一天，  -1表示减去1天， 1天表示加1天， 1d/24 表示1个小时
     * @return 计算后的时间
     */
    public static java.util.Date date_add(Object dateObj, double day) {
        java.util.Date retDate = to_date(dateObj, null);
        if (retDate == null) {
            return null;
        }
        long msVal = (long) (day * 1000L * 24 * 3600L);
        long curMs = retDate.getTime() + msVal;
        return new java.util.Date(curMs);
    }
}
