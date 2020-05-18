package org.framework.hsven.dataset.datatable;

import org.framework.hsven.dataset.datatable.api.ICellObject;
import org.framework.hsven.dataset.datatable.beans.DataTypeEnum;
import org.framework.hsven.dataset.datatable.cache.DataTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public final class DataSetUtil {

	/**
	 * double 的负数的最小值，因为Double.MIN_VALUE 是正数，所以单独设立
	 */
	public final static double DOUBLE_MIN_VALUE = 0d - Double.MAX_VALUE;

	/**
	 * float 的负数的最小值，因为Float.MIN_VALUE 是正数，所以单独设立
	 */
	public final static float FLOAT_MIN_VALUE = 0f - Float.MAX_VALUE;

	/**
	 * 无实际用途，主要用来提前激活本对象，以节省在调用本地函数首次创建对象时的时间
	 */
	public static void active() {
	}

	/**
	 * 禁止创建本类的实例
	 */
	private DataSetUtil() {
	}

	public static Class<?> getClassType(DataTypeEnum dt) {
		if (dt == DataTypeEnum.DtInt || dt == DataTypeEnum.DtDateSecInt) {
			return int.class;
		}

		if (dt == DataTypeEnum.DtString || dt == DataTypeEnum.DtXmlObj || dt == DataTypeEnum.DtJsonObj) {
			return String.class;
		}

		if (dt == DataTypeEnum.DtLong || dt == DataTypeEnum.DtDateMsecLong) {
			return long.class;
		}

		if (dt == DataTypeEnum.DtDouble) {
			return double.class;
		}

		if (dt == DataTypeEnum.DtFloat) {
			return float.class;
		}

		if (dt == DataTypeEnum.DtBool) {
			return boolean.class;
		}

		if (dt == DataTypeEnum.DtByte) {
			return byte.class;
		}

		if (dt == DataTypeEnum.DtShort) {
			return short.class;
		}

		if (dt == DataTypeEnum.DtDate) {
			return java.util.Date.class;
		}

		if (dt == DataTypeEnum.DtObject) {
			return ICellObject.class;
		}

		if (dt == DataTypeEnum.DtByteArr) {
			return byte[].class;
		}
		return null;
	}

	public static Class<?> getClassArrType(DataTypeEnum dt) {
		if (dt == DataTypeEnum.DtInt || dt == DataTypeEnum.DtDateSecInt) {
			return int[].class;
		}

		if (dt == DataTypeEnum.DtString || dt == DataTypeEnum.DtXmlObj || dt == DataTypeEnum.DtJsonObj) {
			return String[].class;
		}

		if (dt == DataTypeEnum.DtLong || dt == DataTypeEnum.DtDateMsecLong) {
			return long[].class;
		}

		if (dt == DataTypeEnum.DtDouble) {
			return double[].class;
		}

		if (dt == DataTypeEnum.DtFloat) {
			return float[].class;
		}

		if (dt == DataTypeEnum.DtBool) {
			return boolean[].class;
		}

		if (dt == DataTypeEnum.DtByte) {
			return byte[].class;
		}

		if (dt == DataTypeEnum.DtShort) {
			return short[].class;
		}

		if (dt == DataTypeEnum.DtDate) {
			return java.util.Date[].class;
		}

		if (dt == DataTypeEnum.DtObject) {
			return ICellObject[].class;
		}

		if (dt == DataTypeEnum.DtByteArr) {
			return byte[][].class;
		}
		return null;
	}

	/**
	 * 扩展当前数组对象到新的长度，旧的对象的内存会显示释放
	 * 
	 * @param abstractArray
	 *            抽象的数组对象
	 * @param newLength
	 *            新的长度
	 * @param dataType
	 *            表示的数据类型
	 * @return 新的数组对象
	 */
	public static Object extendAbstractArray(Object abstractArray, int newLength, DataTypeEnum dataType) {

		Object retObj = null;
		switch (dataType) {
		case DtInt: {
			retObj = Arrays.copyOf((int[]) abstractArray, newLength);
			break;
		}
		case DtString: {
			retObj = Arrays.copyOf((String[]) abstractArray, newLength);
			break;
		}
		case DtDouble: {
			retObj = Arrays.copyOf((double[]) abstractArray, newLength);
			break;
		}
		case DtDate: {
			retObj = Arrays.copyOf((java.util.Date[]) abstractArray, newLength);
			break;
		}
		case DtDateMsecLong: {
			retObj = Arrays.copyOf((long[]) abstractArray, newLength);
			break;
		}
		case DtDateSecInt: {
			retObj = Arrays.copyOf((int[]) abstractArray, newLength);
			break;
		}
		case DtObject: {
			retObj = Arrays.copyOf((ICellObject[]) abstractArray, newLength);
			break;
		}
		case DtByteArr: {
			retObj = Arrays.copyOf((byte[][]) abstractArray, newLength);
			break;
		}
		case DtLong: {
			retObj = Arrays.copyOf((long[]) abstractArray, newLength);
			break;
		}
		case DtFloat: {
			retObj = Arrays.copyOf((float[]) abstractArray, newLength);
			break;
		}
		case DtShort: {
			retObj = Arrays.copyOf((short[]) abstractArray, newLength);
			break;
		}
		case DtBool: {
			retObj = Arrays.copyOf((boolean[]) abstractArray, newLength);
			break;
		}
		case DtByte: {
			retObj = Arrays.copyOf((byte[]) abstractArray, newLength);
			break;
		}
		case DtXmlObj: {
			retObj = Arrays.copyOf((String[]) abstractArray, newLength);
			break;
		}
		case DtJsonObj: {
			retObj = Arrays.copyOf((String[]) abstractArray, newLength);
			break;
		}
		}
		;
		switch (dataType) {
		case DtString:
			freeArray((String[]) abstractArray);
			break;
		case DtDate:
			freeArray((java.util.Date[]) abstractArray);
			break;
		case DtObject:
			freeArray((ICellObject[]) abstractArray);
			break;
		case DtByteArr:
			freeArray((byte[][]) abstractArray);
			break;
		case DtXmlObj:
			freeArray((String[]) abstractArray);
			break;
		case DtJsonObj:
			freeArray((String[]) abstractArray);
			break;
		default:
			break;
		}
		// abstractArray=null;
		return retObj;
	}

	/**
	 * 清空当前对象并按照新的长度创建对象
	 * 
	 * @param abstractArray
	 *            抽象的数组类型
	 * @param newLength
	 *            新的长度
	 * @param dataType
	 *            数组元素类型
	 * @return 新的数组对象
	 */
	public static Object clearAndReInitAbstractArray(Object abstractArray, int newLength, DataTypeEnum dataType) {

		Object retObj = null;

		switch (dataType) {
		case DtInt: {
			retObj = new int[newLength];
			break;
		}
		case DtString: {
			retObj = new String[newLength];
			break;
		}
		case DtDouble: {
			retObj = new double[newLength];
			break;
		}
		case DtDate: {
			retObj = new java.util.Date[newLength];
			break;
		}
		case DtDateMsecLong: {
			retObj = new long[newLength];
			break;
		}
		case DtDateSecInt: {
			retObj = new int[newLength];
			break;
		}
		case DtObject: {
			retObj = new ICellObject[newLength];
			break;
		}
		case DtByteArr: {
			retObj = new byte[newLength][];
			break;
		}
		case DtLong: {
			retObj = new long[newLength];
			break;
		}
		case DtFloat: {
			retObj = new float[newLength];
			break;
		}
		case DtShort: {
			retObj = new short[newLength];
			break;
		}
		case DtBool: {
			retObj = new boolean[newLength];
			break;
		}
		case DtByte: {
			retObj = new byte[newLength];
			break;
		}
		case DtXmlObj: {
			retObj = new String[newLength];
			break;
		}
		case DtJsonObj: {
			retObj = new String[newLength];
			break;
		}
		}
		;
		switch (dataType) {
		case DtString:
			freeArray((String[]) abstractArray);
			break;
		case DtDate:
			freeArray((java.util.Date[]) abstractArray);
			break;
		case DtObject:
			freeArray((ICellObject[]) abstractArray);
			break;
		case DtByteArr:
			freeArray((byte[][]) abstractArray);
			break;
		case DtXmlObj:
			freeArray((String[]) abstractArray);
			break;
		case DtJsonObj:
			freeArray((String[]) abstractArray);
			break;
		default:
			break;
		}
		// abstractArray=null;
		return retObj;
	}

	/**
	 * 释放指定的数组及其元素
	 * 
	 * @param array
	 *            待释放的数组
	 */
	public static <T> void freeArray(T[] array) {
		if (array == null) {
			return;
		}
		int size = array.length;
		for (int jj = 0; jj < size; jj++) {
			array[jj] = null;
		}
	}

	/**
	 * 根据不同的数据类型得到Null对象值
	 * 
	 * @param dt
	 *            数据类型
	 * @return 空对象代表值
	 */
	public static Object getNullValue(DataTypeEnum dt) {
		if (dt == DataTypeEnum.DtInt || dt == DataTypeEnum.DtDateSecInt) {
			return Integer.MIN_VALUE;
		}

		if (dt == DataTypeEnum.DtString || dt == DataTypeEnum.DtXmlObj || dt == DataTypeEnum.DtJsonObj) {
			return null;
		}

		if (dt == DataTypeEnum.DtLong || dt == DataTypeEnum.DtDateMsecLong) {
			return Long.MIN_VALUE;
		}

		if (dt == DataTypeEnum.DtDouble) {
			return DataSetUtil.DOUBLE_MIN_VALUE;
		}

		if (dt == DataTypeEnum.DtFloat) {
			return DataSetUtil.FLOAT_MIN_VALUE;
		}

		if (dt == DataTypeEnum.DtBool) {
			return false;
		}

		if (dt == DataTypeEnum.DtByte) {
			return Byte.MIN_VALUE;
		}

		if (dt == DataTypeEnum.DtShort) {
			return Short.MIN_VALUE;
		}

		if (dt == DataTypeEnum.DtDate || dt == DataTypeEnum.DtObject || dt == DataTypeEnum.DtByteArr) {
			return null;
		}
		return null;
	}

	/**
	 * 各类Date对象到 java.util.Date 转换
	 * 
	 * @param dateObj
	 * @param dateFormat
	 * @return
	 */
	public static java.util.Date objectToDate(Object dateObj, SimpleDateFormat dateFormat) {
		if (dateObj == null) {
			return null;
		}
		if (dateObj instanceof java.util.Date) {
			return (java.util.Date) dateObj;
		}

		if (dateObj instanceof java.sql.Date || dateObj instanceof java.sql.Timestamp || dateObj instanceof java.sql.Time) {
			return (java.util.Date) dateObj;
		}

		if (dateObj instanceof Long) {
			return new java.util.Date(((Long) dateObj).longValue());
		}

		if (dateObj instanceof String) {
			if (dateFormat == null) {
				return (java.util.Date) (java.sql.Date.valueOf((String) dateObj));
			}
			try {
				return dateFormat.parse((String) dateObj);
			} catch (ParseException e) {
				return null;
			}
		}
		if (dateObj instanceof Integer) {
			return new java.util.Date(1000L * (long) (((Integer) dateObj).intValue()));
		}
		return null;
	}

	public static long objectToDateMsecLong(Object dateObj, SimpleDateFormat dateFormat) {
		if (dateObj == null) {
			return Long.MIN_VALUE;
		}

		if (dateObj instanceof Long) {
			return ((Long) dateObj).longValue();
		}

		if (dateObj instanceof Integer) {
			return 1000L * (long) ((Integer) dateObj).intValue();
		}

		java.util.Date obj = objectToDate(dateObj, dateFormat);
		if (obj == null) {
			return Long.MIN_VALUE;
		}
		return obj.getTime();
	}

	public static int objectToDateSecInt(Object dateObj, SimpleDateFormat dateFormat) {
		if (dateObj == null) {
			return Integer.MIN_VALUE;
		}

		if (dateObj instanceof Long) {
			return (int) (((Long) dateObj).longValue() / 1000);
		}

		if (dateObj instanceof Integer) {
			return ((Integer) dateObj).intValue();
		}

		java.util.Date obj = objectToDate(dateObj, dateFormat);
		if (obj == null) {
			return Integer.MIN_VALUE;
		}
		return (int) (obj.getTime() / 1000);
	}

	/**
	 * 从任意时间对象到字符串格式表示的时间对象
	 * 
	 * @param dateObj
	 *            任意时间对象
	 * @param dateFormat
	 *            时间对象的转换格式
	 * @return 时间对象的字符串表示，对象为空时为null
	 */
	public static String objectToDateString(Object dateObj, SimpleDateFormat dateFormat) {
		if (dateObj == null) {
			return null;
		}

		if (dateObj instanceof String) {
			return (String) dateObj;
		}
		java.util.Date obj = objectToDate(dateObj, dateFormat);
		if (obj == null) {
			return null;
		}

		return dateFormat.format(obj);
	}

	public static java.util.Date secIntToDate(int secInt) {
		if (secInt < 0) {
			return null;
		}
		return new java.util.Date(1000L * (long) (secInt));
	}

	public static java.util.Date msecLongToDate(long msecLong) {
		if (msecLong < 0) {
			return null;
		}
		return new java.util.Date(msecLong);
	}

	/**
	 * int second 到时间字符串的转换
	 * 
	 * @param secInt
	 *            时间的秒表示
	 * @param dateFormat
	 *            时间格式
	 * @return 时间<0时为null
	 */
	public static String secIntToDateString(int secInt, SimpleDateFormat dateFormat) {
		if (secInt < 0) {
			return null;
		}
		return dateFormat.format(secIntToDate(secInt));
	}

	/**
	 * ms 值到时间字符串转换
	 * 
	 * @param msecLong
	 *            时间ms表示
	 * @param dateFormat
	 *            时间格式
	 * @return 时间<0时为null
	 */
	public static String msecLongToDateString(long msecLong, SimpleDateFormat dateFormat) {
		if (msecLong < 0) {
			return null;
		}
		return dateFormat.format(msecLongToDate(msecLong));
	}

	public static DataTable recordSetToDataTable(java.sql.ResultSet rst) {
		return null;
	}

	/**
	 * 通过替换得到 like语句所需要的字符串表示
	 * 
	 * @param exprSource
	 * @return 可以直接使用 match的字符串
	 */
	public static String toLikeString(String exprSource) {
		String expr = exprSource;
		expr = expr.replace(".", "\\."); // "\\" is escaped to "\" (thanks, Alan
											// M)
		expr = expr.replace("(","\\(");
		expr = expr.replace(")","\\)");
		expr = expr.replace("{","\\{");
		expr = expr.replace("}","\\}");
		expr = expr.replace("[","\\[");
		expr = expr.replace("]","\\]");
		// ... escape any other potentially problematic characters here
		expr = expr.replace("?", ".");
		expr = expr.replace("%", ".*");
		return expr;
	}

	public static DataTable resultSetToDataTable(java.sql.ResultSet resultSet) {
		return null;
	}

	/**
	 * 得到异常信息的 StackTrace字符串
	 * 
	 * @param trace
	 *            StackTraceElement
	 * @param tabNum
	 *            输出每行的 tab符号个数，用于对齐
	 * @return 字符串表示
	 */
	public static String getExceptionStackTrace(StackTraceElement[] trace, int tabNum) {
		if (trace == null) {
			return null;
		}
		StringBuffer tabStr = new StringBuffer();
		for (int jj = 0; jj < tabNum; jj++) {
			tabStr.append("\t");
		}
		String tab = tabStr.toString();
		StringBuffer sb = new StringBuffer();
		for (int jj = 0; jj < trace.length; jj++) {
			sb.append(tab).append(trace[jj]).append("\r\n");
		}
		return sb.toString();
	}

}
