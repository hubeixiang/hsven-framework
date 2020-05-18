package org.framework.hsven.dataset.datatable.funmanager;


import org.framework.hsven.dataset.datatable.calcfun.SetCalcFun;
import org.framework.hsven.dataset.datatable.calcfun.StaticCalcFun;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FunctionRegistery {
	private static final String LIKE = "like";
	private static Map<String, FunctionRegObj> funMap = null;

	public static void register() {
		if (funMap != null) {
			return;
		}
		funMap = new ConcurrentHashMap<String, FunctionRegObj>();
		String nameForNonStatic = "qr";
		String nameForSetCalc = SetCalcFun.class.getName();
		String nameForStaticCalc = StaticCalcFun.class.getName();

		// 非静态函数调用
		funMap.put("eq", new FunctionRegObj("eq", nameForNonStatic, false));
		funMap.put("ne", new FunctionRegObj("ne", nameForNonStatic, false));
		funMap.put("in", new FunctionRegObj("in", nameForNonStatic, false));
		funMap.put("bw", new FunctionRegObj("bw", nameForNonStatic, false));
		funMap.put("bwt", new FunctionRegObj("bwt", nameForNonStatic, false));
		funMap.put(LIKE, new FunctionRegObj(LIKE, nameForNonStatic, false));
		funMap.put("gt", new FunctionRegObj("gt", nameForNonStatic, false));
		funMap.put("ge", new FunctionRegObj("ge", nameForNonStatic, false));
		funMap.put("lt", new FunctionRegObj("lt", nameForNonStatic, false));
		funMap.put("le", new FunctionRegObj("le", nameForNonStatic, false));

		// 集合函数运算
		funMap.put("and", new FunctionRegObj("and", nameForSetCalc + ".and", true));
		funMap.put("or", new FunctionRegObj("or", nameForSetCalc + ".or", true));
		funMap.put("not", new FunctionRegObj("not", nameForSetCalc + ".not", true));
		funMap.put("andm", new FunctionRegObj("andm", nameForSetCalc + ".andm", true));
		funMap.put("orm", new FunctionRegObj("orm", nameForSetCalc + ".orm", true));

		// 纯静态函数
		// 时间处理函数
		funMap.put("now", new FunctionRegObj("now", nameForStaticCalc + ".now", true, false));
		funMap.put("date", new FunctionRegObj("date", nameForStaticCalc + ".date", true, false));
		funMap.put("to_date", new FunctionRegObj("to_date", nameForStaticCalc + ".to_date", true, true));
		funMap.put("date_add", new FunctionRegObj("date_add", nameForStaticCalc + ".date_add", true, true));

		// json 处理函数
//		funMap.put("json", new FunctionRegObj("json", nameForStaticCalc + ".json", true, true));
	}

	public static FunctionRegObj getFunction(String funName) {
		if (funName == null) {
			return null;
		}
		String fun = funName.trim().toLowerCase();
		if (fun.length() == 0)
			return null;
		if (funMap == null) {
			register();
		}
		return funMap.get(fun);
	}

	/**
	 * 是否为标识符字符
	 * 
	 * @param ch
	 *            字符
	 * @return true为是，false 为不是标识符
	 */
	private static boolean isAnInditifierChar(char ch) {
		if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == '_' || ch == '$' || (ch >= 19968 && ch <= 40869)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为空白字符
	 * 
	 * @param ch
	 *            字符
	 * @return true为是，false 为不是空白字符
	 */
	private static boolean isABlankChar(char ch) {
		if (ch == ' ' || ch == '\r' || ch == '\n' || ch == '\t') {
			return true;
		}
		return false;
	}

	/**
	 * 根据原始字符串找到需要替换的函数名称，即函数名称为非全路径名称的情况下
	 * 
	 * @param expStr
	 *            原始的字符串
	 * @return 待替换的字符串的列表标志
	 */
	private static List<FunObj> findFunction(String expStr) {
		List<FunObj> funList = new ArrayList<FunObj>();
		String temp = expStr;
		if (temp == null)
			return funList;
		int fromIndex = 0;
		while (fromIndex < temp.length()) {
			int endIndex = temp.indexOf('(', fromIndex); // 从当前开始位置寻找'('
			if (endIndex < fromIndex) {
				break;
			} // 当无法找到时，将返回-1，说明已经到末尾
			if (endIndex == fromIndex) {
				fromIndex = endIndex + 1;
				continue;
			} // 当位于第0个 index位置时，从下一个位置重新开始搜索
			if (temp.charAt(endIndex - 1) == '\\') {
				fromIndex = endIndex + 1;
				continue;
			} // 当(为\(代表(为转移不是字符串内容，当普通字符串处理

			int end = endIndex;
			char curCh = 0;
			while (end > fromIndex) // 往前回溯，忽略空白类字符
			{
				curCh = temp.charAt(end - 1);
				if (isABlankChar(curCh)) {
					end--;
				} else {
					break;
				} // 找到非空白字符串
			}
			if (end <= fromIndex) {
				fromIndex = endIndex + 1;
				continue;
			} // 从起始位置得到的全是空白字符

			int startIndex = end;
			for (int i = end - 1; i >= fromIndex; i--) // 按照标识符规则找到标识符的开始位置
			{
				curCh = temp.charAt(i);
				if (!isAnInditifierChar(curCh)) {
					break;
				}
				startIndex = i;
			}
			String name = temp.substring(startIndex, end);
			if (name == null || name.length() == 0) {
				fromIndex = endIndex + 1;
				continue;
			}
			int startPlace = startIndex - 1;
			boolean isDotFun = false;
			while (startPlace >= 0) {
				if (isABlankChar(temp.charAt(startPlace))) {
					startPlace--;
					continue;
				}
				if (temp.charAt(startPlace) == '.') {
					isDotFun = true;
					break;
				} else {
					break;
				}
			}
			int index = temp.indexOf(name);
			boolean needFilter = isNeedFilter(temp, index);
			if (needFilter) {
				fromIndex = endIndex + 1;
				continue;
			}
			if (!isDotFun) {
				FunObj obj = new FunObj();
				obj.start = startIndex;
				obj.end = end - 1;
				obj.name = name;
				funList.add(obj);
			}
			fromIndex = endIndex + 1;
		}
		return funList;
	}

	private static boolean isNeedFilter(String temp, int index) {
		boolean isNeedFilter = true;
		if (index > 0) {

			if (temp.charAt(index) == 'g') {
				isNeedFilter = false;
			}
			if (temp.charAt(index) == 'l') {
				isNeedFilter = false;
			}
			if (temp.charAt(index) == 'e') {
				isNeedFilter = false;
			}
			if (temp.charAt(index) == 'n') {
				isNeedFilter = false;
			}
			if (temp.charAt(index) == 'b') {
				isNeedFilter = false;
			}
			if (temp.charAt(index) == 'i') {
				isNeedFilter = false;
			}
			if (temp.charAt(index) == 'o') {
				isNeedFilter = false;
			}
			if (temp.charAt(index) == 'a') {
				isNeedFilter = false;
			}
			if (temp.charAt(index) == 'n') {
				isNeedFilter = false;
			}
		} else {
			isNeedFilter = false;
		}
		return isNeedFilter;
	}

	/**
	 * 替换原始的字符串表达式为可执行的字符串表达式
	 * 
	 * @param orgiExpStr
	 *            原始的字符串表达式
	 * @return 替换后的字符串表达式
	 */
	public static FunctionExecuteExpResult getExecutableExpression(String orgiExpStr) {
		FunctionExecuteExpResult result = new FunctionExecuteExpResult();
		result.rawExpString = orgiExpStr;
		if (orgiExpStr == null) {
			return result;
		}
		List<FunObj> replaceList = findFunction(orgiExpStr);
		String tmpStr = orgiExpStr;
		int start = 0;
		int end = 0;
		String name = null;
		int objCount = 0;
		FunctionRegObj funRegObj = null;
		String finalName = null;
		boolean isIdem = true;
		int lastStartIndex = -1;
		for (int jj = replaceList.size() - 1; jj >= 0; jj--) {
			start = replaceList.get(jj).start;
			end = replaceList.get(jj).end;
			name = replaceList.get(jj).name;
			funRegObj = getFunction(name);
			if (funRegObj == null) {
				throw new DataSetException("unknown function:" + name);
			}
			finalName = funRegObj.classPathName;
			if (!funRegObj.isIdempotent) {
				isIdem = false;
			}
			if (!funRegObj.isStatic) {
				objCount++;
				finalName = finalName + objCount;
				result.newQueryObjNameList.add(finalName);
				finalName = finalName + "." + funRegObj.name;
			}

			String left = tmpStr.substring(0, start);
			String right = tmpStr.substring(end + 1);
			String rightTemp = null;
			StringBuilder beforeReplace = new StringBuilder();
			if (lastStartIndex >= 0) {
				rightTemp = tmpStr.substring(start, lastStartIndex);
				int index = rightTemp.indexOf(",");
				if (index > 0) {
					rightTemp = rightTemp.substring(index);
					beforeReplace.append(rightTemp);
					rightTemp = replaceAll(rightTemp,name);
				}
			} else {
				rightTemp = tmpStr.substring(start);
				int index = rightTemp.indexOf(",");
				if (index > 0) {
					rightTemp = rightTemp.substring(index);
					beforeReplace.append(rightTemp);
					rightTemp = replaceAll(rightTemp,name);
				}
			}

			tmpStr = left + finalName + right;
			if (beforeReplace.length() > 0) {
				tmpStr = tmpStr.replace(beforeReplace.toString(), rightTemp);
			}
			lastStartIndex = start;
		}

		// 双引号被用作字符串里边的字符，在调用Bshell 时需要使用 \" 格式
		tmpStr = tmpStr.replaceAll("\"", "\\\\\"");
		result.executableExpString = tmpStr.replaceAll("\'", "\"");
		// System.out.println(result.executableExpString);
		result.isIdempotent = isIdem;
		return result;
	}

	private static String replaceAll(String exp,String funName) {
		StringBuilder builder = new StringBuilder();
		String splitStr = containSubExpression(exp);
		if (splitStr == null) {
			String strTemp = replace(exp,funName);
			builder.append(strTemp);
		} else {
			String[] strArr = exp.split(splitStr);
			for (int i = 0; i < strArr.length; i++) {
				String splitStr_temp = containSubExpression(strArr[i]);
				String str = null;
				if (splitStr_temp == null) {
					str = replace(strArr[i],splitStr);
					if (i > 0) {
						builder.append(splitStr.replace("\\", ""));
					}
				} else {
					if (i > 0) {
						builder.append(splitStr.replace("\\", ""));
					}
					str = replaceAll(strArr[i],splitStr_temp);
				}

				builder.append(str);
			}
		}
		return builder.toString();
	}

	private static String replace(String exp,String funName) {
		int index = exp.indexOf("'");
		int lastIndex = exp.lastIndexOf("'");
		String head = null;
		if (index > 0) {
			head = exp.substring(0, index);
		} else {
			head = "";
		}
		String tail = null;
		if (lastIndex > 0) {
			tail = exp.substring(lastIndex);
		} else {
			tail = "";
		}

		if (index < 0 && lastIndex < 0) {
			return exp;
		}
		String str = exp.substring(index, lastIndex);
//		if(funName != null && funName.contains(LIKE)){
//			str = replaceKeyWord("\\(", str);
//			str = replaceKeyWord("\\)", str);
//			str = replaceKeyWord("\\{", str);
//			str = replaceKeyWord("\\}", str);
//			str = replaceKeyWord("\\[", str);
//			str = replaceKeyWord("\\]", str);
//		}
		str = head + str + tail;
		return str;
	}

	private static String containSubExpression(String str) {
		if (str.contains("ge(")) {
			return "ge\\(";
		}
		if (str.contains("gt(")) {
			return "gt\\(";
		}
		if (str.contains("le(")) {
			return "le\\(";
		}
		if (str.contains("lt(")) {
			return "lt\\(";
		}
		if (str.contains("eq(")) {
			return "eq\\(";
		}
		if (str.contains("ne(")) {
			return "ne\\(";
		}
		if (str.contains("like(")) {
			return "like\\(";
		}
		if (str.contains("bw(")) {
			return "bw\\(";
		}
		if (str.contains("bwt(")) {
			return "bwt\\(";
		}
		if (str.contains("in(")) {
			return "in\\(";
		}
		return null;

	}

	private static String replaceKeyWord(String key, String sourceStr) {
		StringBuilder builder = new StringBuilder();
		String[] strArr = sourceStr.split(key);
		if (strArr.length <= 1) {
			return sourceStr;
		}
		for (int i = 0; i < strArr.length; i++) {
			builder.append(strArr[i]);
			if (i < strArr.length - 1) {
				builder.append("\\\\").append(key.substring(1));
			}
		}
		return builder.toString();
	}
}

class FunObj {
	public int start = 0;
	public int end = 0;
	public String name = null;
}
