package org.framework.hsven.dataset.datatable.cache;

import bsh.EvalError;
import bsh.Interpreter;
import org.framework.hsven.dataset.datatable.DataSetUtil;
import org.framework.hsven.dataset.datatable.MD5Util;
import org.framework.hsven.dataset.datatable.api.ICellObject;
import org.framework.hsven.dataset.datatable.api.IRowsLoader;
import org.framework.hsven.dataset.datatable.api.IRowsProcessor;
import org.framework.hsven.dataset.datatable.beans.DataTypeEnum;
import org.framework.hsven.dataset.datatable.beans.OperatorTypeEnum;
import org.framework.hsven.dataset.datatable.calcfun.BetweenCalcFun;
import org.framework.hsven.dataset.datatable.calcfun.EqualCalcFun;
import org.framework.hsven.dataset.datatable.calcfun.InCalcFun;
import org.framework.hsven.dataset.datatable.calcfun.LogicCalcFun;
import org.framework.hsven.dataset.datatable.calcfun.NotEqualCalcFun;
import org.framework.hsven.dataset.datatable.calcfun.StringCalcFun;
import org.framework.hsven.dataset.datatable.exception.DataSetException;
import org.framework.hsven.dataset.datatable.funmanager.FunctionExecuteExpResult;
import org.framework.hsven.dataset.datatable.funmanager.FunctionRegistery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用于存放的datatable数据
 */
/**
 * @author duhailiang
 *
 */

/**
 * @author duhailiang
 * 
 */
public class DataTable {
	private static final String MD5_FIELD = "md5";
	public static Logger logger = LoggerFactory.getLogger(DataTable.class);
	private int colCapacity = 1024; // 列的最大容量
	private int currentCapacity = 10 * 1024; // 当前的容量大小
	private int initCapacity = 10 * 1024; // 初始容量大小
	private int extendCapacity = 1024; // 每次的扩展大小
	private int currentSize = 0; // 当前实际存储的连续行数
	private int colSize = 0; // 列的个数

	private Object[] cols = new Object[colCapacity]; // 存储每个列的数据，即以按列数组存储
	private ColInfo[] colsInfo = new ColInfo[colCapacity]; // 列信息

	private boolean[] allSelectedArr = null; // 生成的全部数据队列，供后续queryResult使用

	private ReadWriteLock rwLock = new ReentrantReadWriteLock(); // 读写锁，用以对dataTable进行读写同步功能

	private AtomicInteger latestWriteCounter = new AtomicInteger(0); // 最新的更新datatable的计算器，用以判断是否取到脏数据,
																		// 只要数据有更新,该变量即+1操作

	private Object tmpSyncObj = new Object(); // 临时同步对象'

	// 存放字符串到QueryResult 的hash对象
	private QueryResultCachedMap queryRltCachedMap = new QueryResultCachedMap();

	private static boolean DEBUG = false;

	// 初始化时使用，为了让Bsh解析更快的加载
	private static Interpreter compiler = null;

	// 2013.10.16 增加了数据表的名称
	private String name = null;

	// 2013.11.28 增加了，是否忽略错误的字段名称的运算逻辑异常,缺省为true
	private boolean ignoreUnknowColumnException = true;

	/**
	 * 用指定最大长度构建块
	 * 
	 * @param initSize
	 * @param extendSize
	 *            必须指定最大长度，不能存放超过长度块的数据
	 */
	public DataTable(int initSize, int extendSize) {
		init();
		if (initSize <= 0 || extendSize <= 0) {
			throw new DataSetException("Illegal argument: " + initSize + "," + extendSize);
		}
		this.currentCapacity = initSize;
		this.extendCapacity = extendSize;
		this.initCapacity = initSize;
	}

	/**
	 * 使用指定的初始大小来创建DataTable
	 * 
	 * @param initSize
	 *            初始大小，决定了列式dataTable的初始存储长度
	 */
	public DataTable(int initSize) {
		init();
		if (initSize <= 0) {
			throw new DataSetException("Illegal argument: " + initSize);
		}
		this.currentCapacity = initSize;
		this.extendCapacity = initSize / 3 + 1;
		this.initCapacity = initSize;
	}

	/**
	 * 用缺省的容量参数构造Datatable对象
	 */
	public DataTable() {
		init();
	}

	/**
	 * 用缺省的容量参数构造Datatable对象
	 * 
	 * @param name
	 *            指定数据表的名称，不指定时为null
	 */
	public DataTable(String name) {
		this();
		this.setName(name);
	}

	public void init() {
		if (compiler == null) {
			compiler = new Interpreter();
			try {
				compiler.eval("a=1+1");
			} catch (EvalError e) {
				e.printStackTrace();
			}
		}

		FunctionRegistery.register(); // 函数注册完成
		EqualCalcFun.active();
		NotEqualCalcFun.active();
		BetweenCalcFun.active();
		LogicCalcFun.active();
		StringCalcFun.active();
		InCalcFun.active();
		DataSetUtil.active();

	}

	/**
	 * 得到数据表的名称
	 * 
	 * @return DataTable名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置数据表名称
	 * 
	 * @param name
	 *            数据表名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 判断是否忽略未知列运算错误，true时，在查询表达式中将忽略该错误列参与的单个表达式
	 * 
	 * @return true:忽略 false：有错误的字段名称将会报异常
	 */
	public boolean isIgnoreUnknowColumnException() {
		return ignoreUnknowColumnException;
	}

	/**
	 * 设置是否忽略未知列运算错误，true时，在查询表达式中将忽略该错误列参与的单个表达式 不设置时缺省为 true
	 * 
	 * @param ignoreUnknowColumnException
	 *            true:忽略 false：有错误的字段名称将会报异常
	 */
	public void setIgnoreUnknowColumnException(boolean ignoreUnknowColumnException) {
		this.ignoreUnknowColumnException = ignoreUnknowColumnException;
	}

	/**
	 * 得到列的数据，列存储数组
	 * 
	 * @return
	 */
	public Object[] getCols() {
		return cols;
	}

	/**
	 * 得到列头信息数组，使用时应取前面的colSize列
	 * 
	 * @return
	 */
	public ColInfo[] getColsInfo() {
		return colsInfo;
	}

	public ColInfo getColInfo(int colIndex) {
		return colsInfo[colIndex];
	}

	/**
	 * 得到当前列的个数
	 * 
	 * @return
	 */
	public int getColSize() {
		return colSize;
	}

	/**
	 * 得到需要准备clone的数组下标指示，采用同步调用，当发现数组下标变化时，重新clone对象
	 * 
	 * @return 供QueryResult clone 访问的 下标数组对象
	 */
	public boolean[] genWillClonedSelectedIndexArr() {
		synchronized (tmpSyncObj) // 采用同步对象，避免对数组的读写出现问题
		{
			if (allSelectedArr == null) {

				allSelectedArr = new boolean[currentSize];
				for (int jj = 0; jj < currentSize; jj++) {
					allSelectedArr[jj] = true;
				}

			}
			int arrSize = allSelectedArr.length;
			if (arrSize != currentSize) {
				allSelectedArr = Arrays.copyOf(allSelectedArr, currentSize);
				for (int jj = 0; jj < currentSize - arrSize; jj++) {
					allSelectedArr[arrSize + jj] = true;
				}

			}
		}
		return allSelectedArr;
	}

	/**
	 * 获得当前dataTable的最新时间写更新 counter
	 * 
	 * @return
	 */
	public int getLatestWriterCounter() {
		return this.latestWriteCounter.get();
	}

	/**
	 * 清空当前dataTable对象数据，但不会清除column 信息 直接调用时线程不安全，在executeBatchLoad
	 * 的IRowsLoader 中调用则线程安全
	 */
	public void clear() {
		if (this.currentSize == 0) {
			return;
		}
		this.latestWriteCounter.incrementAndGet(); // 清除对象时，修改更新标志
		this.queryRltCachedMap.clear();

		for (int jj = 0; jj < colSize; jj++) {
			cols[jj] = DataSetUtil.clearAndReInitAbstractArray(cols[jj], initCapacity, colsInfo[jj].type);
		}
		currentCapacity = initCapacity;
		currentSize = 0;
		System.gc();
	}

	/**
	 * 清空当前dataTable对象数据，但不会清除column 信息 可以直接调用，线程安全的清除调用
	 */
	public void clearSync() {
		try {
			this.rwLock.writeLock().lock();
			clear();
		}finally {
			this.rwLock.writeLock().unlock();
		}
	}

	/**
	 * 清空当前dataTable对象数据,并丢弃该dataTable对象不再使用
	 */
	public void destory(){
		String dataTableName = this.getName();
		try {
			this.rwLock.writeLock().lock();
			destoryInternal();
		}catch (Throwable t){
			logger.error(String.format("DataTable=[%s] destory Exception:%s",dataTableName,t.getMessage()),t);
		}finally {
			this.rwLock.writeLock().unlock();
		}
	}

	private void destoryInternal(){
		//先设置对象数据量为0
		this.currentSize = 0;

		this.latestWriteCounter.incrementAndGet(); // 清除对象时，修改更新标志
		this.queryRltCachedMap.clear();

		for (int jj = 0; jj < colSize; jj++) {
			cols[jj] = null;
			colsInfo[jj] =null;
		}

		cols = null;
		colsInfo = null;
		currentCapacity = initCapacity;
	}

	/**
	 * 得到当前dataTable的长度
	 * 
	 * @return 当前dataTable 行数
	 */
	public int getCurrentSize() {
		return currentSize;
	}

	/**
	 * @return 得到当前对象的全量查询结果对象
	 */
	public QueryResult query() {
		return QueryResult.genOneInstance(this);
	}

	/**
	 * 在当前DataTable对象上执行表达式查询，得到相应的查询结果对象
	 * 
	 * @param expStr
	 *            表达式
	 * @return 查询结果对象
	 */
	public QueryResult query(String expStr) {
		return queryFromQueryResult(expStr, null);
	}

	public Object getFieldValue(int rowIndex, String fieldName) {

		Object returnValue = null;
		int jj = 0;
		try {

			jj = this.getColIndex(fieldName);
			if (jj < 0 || jj >= colsInfo.length) {
				return null;
			}

			switch (colsInfo[jj].type) {
			case DtInt: {
				returnValue = ((int[]) (cols[jj]))[rowIndex];
				break;
			}
			case DtDouble: {

				returnValue = ((double[]) (cols[jj]))[rowIndex];
				break;
			}
			case DtString:
			case DtJsonObj:
			case DtXmlObj: {

				returnValue = ((String[]) (cols[jj]))[rowIndex];
				break;
			}
			case DtLong: {

				returnValue = ((long[]) (cols[jj]))[rowIndex];
				break;
			}
			case DtFloat: {

				returnValue = ((float[]) (cols[jj]))[rowIndex];
				break;
			}

			case DtShort: {

				returnValue = ((short[]) (cols[jj]))[rowIndex];
				break;
			}

			case DtByte: {

				returnValue = ((byte[]) (cols[jj]))[rowIndex];
				break;
			}

			case DtBool: {

				returnValue = ((boolean[]) (cols[jj]))[rowIndex];
				break;
			}

			case DtObject: {

				returnValue = ((ICellObject[]) (cols[jj]))[rowIndex];
				break;
			}

			case DtByteArr: {

				returnValue = ((byte[][]) (cols[jj]))[rowIndex];
				break;
			}

			case DtDate: {
				returnValue = ((java.util.Date[]) (cols[jj]))[rowIndex];
				break;
			}

			case DtDateMsecLong: {
				returnValue = ((long[]) (cols[jj]))[rowIndex];
				break;
			}

			case DtDateSecInt: {
				returnValue = ((int[]) (cols[jj]))[rowIndex];
				break;
			}

			}
			return returnValue;
		} catch (Exception ee) {

			logger.error("getFieldValue Exception.fieldName:" + fieldName + ",rowIndex:" + rowIndex, ee);
		}
		return null;
	}

	public List<Object> getFieldValue(String fieldName) {

		try {
			int size = this.getCurrentSize();
			List<Object> fieldValueList = new ArrayList<Object>(size);
			Object temp = null;
			for (int i = 0; i < size; i++) {
				temp = getFieldValue(i, fieldName);
				if (temp != null) {
					fieldValueList.add(temp);
				}
			}
			return fieldValueList;
		} catch (Exception ee) {

			logger.error("getFieldValue Exception.fieldName:" + fieldName, ee);
		}
		return null;
	}

	public int[] getFieldValueInt(String fieldName) {
		int[] valueList = null;
		int jj = 0;
		try {

			jj = this.getColIndex(fieldName);
			if (jj < 0 || jj >= colsInfo.length) {
				return null;
			}
			DataTypeEnum dataTypeEnum = colsInfo[jj].type;
			if (dataTypeEnum.equals(DataTypeEnum.DtInt)) {
				valueList = ((int[]) (cols[jj]));
				return valueList;
			}
		} catch (Exception e) {
			logger.error("getFieldValueInt Exception.fieldName:" + fieldName, e);
		}
		return null;
	}

	/**
	 * 根据表达式从QueryResult进行二次查询，当QueryResult为null时，从dataTable中进行全量查询
	 * 
	 * @param expStr
	 *            表达式字符串表示
	 * @return 得到结果选择查询结果集对象
	 * @throws DataSetException
	 *             取数出错时抛出异常
	 */
	public QueryResult queryFromQueryResult(String expStr, QueryResult qrObj) throws DataSetException {
		String queryExpStr = null;
		// 当表达式为true 或 null 或 为1 时间直接返回datatable的全部的表行对象
		boolean isTrueExp = false;

		if (expStr == null) {
			isTrueExp = true;
		} else {
			queryExpStr = expStr.trim();
			if (queryExpStr.equalsIgnoreCase("true") || queryExpStr.equalsIgnoreCase("1")) {
				isTrueExp = true;
			}
		}
		if (isTrueExp) // 当需要返回全部数据行时
		{
			if (qrObj == null) {
				return this.query();
			} else {
				return qrObj;
			}
		}
		// 当仅需要返回表头时
		if (queryExpStr.equalsIgnoreCase("false") || queryExpStr.equalsIgnoreCase("0") || queryExpStr.equalsIgnoreCase("null")) {
			return QueryResult.genOneEmptySelectedInstance(this);
		}

		// 得到替换解析后的表达式结果对象
		FunctionExecuteExpResult resultExp = FunctionRegistery.getExecutableExpression(queryExpStr);
		QueryResult[] qrArr = new QueryResult[resultExp.newQueryObjNameList.size()];
		int counter = 1;
		String resultObjStr = "retObj_" + counter;
		QueryResult retObj = null;

		boolean isException = false;
		String exceptionInfo = null;
		String exeExpStr = null;
		try {
			// 首先从缓存中查询
			if (qrObj == null && resultExp.isIdempotent) {
				retObj = this.queryRltCachedMap.get(queryExpStr, this.getLatestWriterCounter());
				if (retObj != null) {
					return retObj.query();
				}
			}
			// 缓存中不存在时使用bshell执行
			bsh.Interpreter intp = new Interpreter();
			for (int jj = 0; jj < qrArr.length; jj++) {
				if (qrObj == null) {
					qrArr[jj] = this.query();
				} else {
					qrArr[jj] = qrObj.query();
				}
				intp.set(resultExp.newQueryObjNameList.get(jj), qrArr[jj]);
			}
			exeExpStr = resultObjStr + "=" + resultExp.executableExpString;
			if (DEBUG) {
				System.out.println("Expression:" + exeExpStr);
			}
			intp.eval(exeExpStr); // 使用Bshell

			retObj = (QueryResult) (intp.get(resultObjStr));
			for (int jj = 0; jj < qrArr.length; jj++) {
				intp.unset(resultExp.newQueryObjNameList.get(jj));
				if (retObj != qrArr[jj]) {
					qrArr[jj].clear();
				}
				qrArr[jj] = null;
			}
			intp.unset(resultObjStr);
			intp = null;
		} catch (Exception ee) {
			isException = true;
			String errClassName = ee.getClass().getName();
			String errShellExp = ee.getMessage();
			String errMsg = ee.getMessage();
			String errPlace = null;
			if (ee instanceof bsh.TargetError) {
				Throwable expTarget = ((bsh.TargetError) ee).getTarget();
				if (expTarget != null) {
					errClassName = expTarget.getClass().getName();
					errMsg = expTarget.getMessage();
					errPlace = DataSetUtil.getExceptionStackTrace(expTarget.getStackTrace(), 1);
				}
			}
			exceptionInfo = "[ExceptionClass]:" + errClassName + "\r\n" + "[Message       ]:" + errMsg + "\r\n" + "[Expression    ]:" + exeExpStr + "\r\n" + "[BeanShellExp  ]:" + errShellExp + "\r\n"
					+ "[ErrorPlace    ]:" + "\r\n" + errPlace;
		}
		if (isException) {
			throw new DataSetException("Bean Shell execute error.\r\n" + exceptionInfo);
		}
		// 对于全量查询的进行缓存,缓存时采用clone 缓存方式
		if (qrObj == null && retObj != null && resultExp.isIdempotent) {
			this.queryRltCachedMap.put(queryExpStr, retObj.query());
		}
		return retObj;
	}

	/**
	 * 执行表达式,执行表达式时将取得同步读锁操作，再该方法执行完成前写锁将等待完成工作，
	 * 
	 * @param expStr
	 *            表达式
	 * @param processor
	 *            结果处理器，用户函数不能存在阻塞，否则将导致写线程无法完成工作
	 * @param rowsPageSize
	 *            期望循环分页处理时每次处理（调用IRowsProcessor.process())的的最大行数
	 *            为<=0时，将取得全部行数一次性调用 IRowsProcessor.process()
	 */
	public void execute(String expStr, IRowsProcessor processor, int rowsPageSize) {
		int pageSize = rowsPageSize;
		// 处理器为null，抛出异常
		if (processor == null) {
			throw new DataSetException("IRowsProcessor can not be null");
		}

		boolean isException = false;
		String exceptionMsg = null;
		try {
			this.lockRead(); // 读锁住
			// 执行 表达式查询
			QueryResult qRlt = this.query(expStr);

			// 每次处理的最大行数必须至少为1，如pageSize<=0时将一次性得到全部rows
			if (pageSize <= 0) {
				pageSize = qRlt.selectedResultSize;
			}

			// 首先执行一下处理器beforeProcess
			processor.beforeProcess(qRlt.selectedResultSize, pageSize);

			int leftSize = qRlt.selectedResultSize; // 剩余的选中行
			int searchLen = 0; // 每次调用的寻找行数
			int rawDtCurIndex = 0; // 当前的dataTable 的行号
			int[] rowNumArr = null; // 每次new的下标数组
			int rowNumIndex = 0; // 数组的下标号
			Rows rows = null; // 每次new的处理对象

			int startRowNo = 0; // 累计的开始行号

			// 如果结果为empty时，特殊执行 processor的 process
			if (qRlt.selectedResultSize == 0) {
				processor.process(0, -1, 0, new Rows(this, new int[0]));
			}

			while (leftSize > 0) // 当剩余的还有的话处理
			{
				if (pageSize > 0) {
					searchLen = Math.min(leftSize, pageSize);
				} else {
					searchLen = leftSize;
				}
				rowNumArr = new int[searchLen];
				rowNumIndex = 0;
				while (rowNumIndex < searchLen) // 从选择队列中寻找需要处理的对象
				{
					if (qRlt.selectedIndexArr[rawDtCurIndex]) {
						rowNumArr[rowNumIndex] = rawDtCurIndex;
						rowNumIndex++;
					}
					rawDtCurIndex++;
				}
				rows = new Rows(this, rowNumArr); // 用行数组集合创建行对象

				processor.process(qRlt.selectedResultSize, startRowNo, searchLen, rows);

				leftSize = leftSize - searchLen;
				startRowNo = startRowNo + searchLen;
			}
			processor.afterProcessed(); // 全部调用完成后的操作
			qRlt.clear(); // 清空查询对象
			qRlt = null;
		} catch (Exception ee) {
			logger.error("execute Exception,expStr:" + expStr, ee);
			isException = true;
			exceptionMsg = ee.getMessage();
		} finally {
			this.unlockRead(); // 解读锁
		}
		if (isException) {
			try {
				processor.whenException(new DataSetException(exceptionMsg));
			} catch (Exception ex) {
			}
		}

	}

	/**
	 * 执行批量load到dataTable，在执行过程中，表锁发生作用，直到load完成，读线程将被阻塞， loader 线程一般调用 clear 和
	 * addRow清空和追加数据
	 * 
	 * @param loader
	 */
	public void executeBatchLoad(IRowsLoader loader) {
		if (loader == null) {
			throw new DataSetException("IRowsLoader can not be null");
		}

		try {
			this.rwLock.writeLock().lock(); // 写锁完成
			this.latestWriteCounter.incrementAndGet();
			loader.beforeLoad();
			loader.load(this);
			loader.afterLoaded();
		} catch (Exception ee) {
			try {
				loader.whenException(new DataSetException(ee.getMessage()));
			} catch (Exception e1) {
			}
		} finally {
			this.rwLock.writeLock().unlock(); // 写锁解除
		}

	}

	/**
	 * @param col
	 *            增加列信息到dataTableBlock中,本方法调用时将会加写锁到datatable
	 */
	public void addCol(ColInfo col) {
		if (this.currentSize > 0) {
			throw new DataSetException("Only add column to empty DataTable,current Size=" + this.currentSize);
		}
		this.rwLock.writeLock().lock();
		// this.latestWriteCounter.incrementAndGet();
		Class<?> clsType = DataSetUtil.getClassType(col.type);
		if (clsType != null) {
			try {
				Object array = Array.newInstance(clsType, currentCapacity);
				cols[colSize] = array;
				colsInfo[colSize] = col;
				colSize++;
			} catch (Exception ee) {
				this.rwLock.writeLock().unlock();
				throw new DataSetException(ee.getMessage());
			}
		}
		this.rwLock.writeLock().unlock();
	}

	/**
	 * 得到全部ColInfo列信息,得到 colInfo不能进行修改
	 * 
	 * @return 全部的列信息
	 */
	public List<ColInfo> getAllColInfo() {
		List<ColInfo> retList = new ArrayList<ColInfo>(colSize);
		for (int jj = 0; jj < colSize; jj++) {
			retList.add(colsInfo[jj]);
		}
		return retList;
	}

	/**
	 * 扩展dataTable对象的行信息
	 */
	private void extendCapacity() {
		Object tmpArr = null;
		int extendSize = Math.max(this.extendCapacity, this.currentCapacity * 1 / 3 + 1);

		int newLen = this.currentCapacity + extendSize;
		DataTypeEnum type;
		for (int jj = 0; jj < colSize; jj++) {
			type = this.colsInfo[jj].type;
			tmpArr = DataSetUtil.extendAbstractArray(this.cols[jj], newLen, type);
			cols[jj] = tmpArr;
		}
		this.currentCapacity = newLen;
	}

	/**
	 * 增加一行数据到dataTable中,直接调用时线程不安全，在executeBatchLoad 的IRowsLoader 中调用则线程安全
	 * 
	 * @param row
	 *            待增加的 dataTable行信息，使用 List 数组表示，list的长度必须和数组列的信息长度相同
	 */
	public void addRow(List<Object> row) {
		this.latestWriteCounter.incrementAndGet(); // 更新当前的写标志
		this.queryRltCachedMap.clear(); // 清空缓存的查询结果数据

		if (row == null || row.size() != colSize) {
			throw new DataSetException("DataTable:" + name + ",Column size is not matched with gived list size:" + colSize);
		}

		if (this.currentSize >= this.currentCapacity) {
			extendCapacity();
			if (this.currentSize >= this.currentCapacity) {
				throw new DataSetException("DataTable:" + name + ",RuntimeException:can not extends array length.");
			}
		}

		int jj = -1;
		try {
			for (Object obj : row) {
				jj++;
				appendValue(this.currentSize,jj,obj);
			}
			this.currentSize++;
		} catch (Exception ee) {
			String info = "DataTable:" + name + " ColumnName:" + colsInfo[jj].name + " Title:" + colsInfo[jj].title + " Type:" + colsInfo[jj].type;
			throw new DataSetException("Put data with incompatible type:" + ee.getMessage() + "\r\n" + info);
		}
	}

	/**
	 * 加载指定行数据,只能在DataTable数据初始化时使用
	 * @param rowIndex  指定加载的行index(<0 表明是新增一行,>=0 表明加载更新的行的index)
	 * @param fieldName  指定行的指定属性名
	 * @param value 指定属性名对应的值
	 * @return
	 */
	public int loadRow(int rowIndex,String fieldName,Object value){
		//DataTable 初始化数据时,不用验证一致性
		//this.latestWriteCounter.incrementAndGet(); // 更新当前的写标志
		//this.queryRltCachedMap.clear(); // 清空缓存的查询结果数据

		if (this.currentSize >= this.currentCapacity) {
			extendCapacity();
			if (this.currentSize >= this.currentCapacity) {
				throw new DataSetException("DataTable:" + name + ",RuntimeException:loadRow can not extends array length.");
			}
		}
		try {
			int jj = this.getColIndex(fieldName);
			if (jj < 0 || jj >= colsInfo.length) {
				//没有找到此字段
				return -1;
			}
			boolean isAdd = false;
			if(rowIndex < 0 || rowIndex >= currentSize){
				//rowIndex <0 时是新增
				isAdd = true;
			}else{
				isAdd = false;
				//rowIndex 就是是赋值指定的行
			}

			if(isAdd){
				rowIndex = this.currentSize;
			}

			appendValue(rowIndex,jj,value);

			if(isAdd){
				this.currentSize++;
			}
			return rowIndex;
		}catch (Exception ee){
			String info = "DataTable:" + name;
			throw new DataSetException(String.format("loadRow Exception. fieldName=%s,value=%s type:\r\n%s" ,fieldName,value,ee.getMessage() ,info));
		}
	}

	/**
	 * DataTable数据加载完成,生成行数据的md5
	 */
	public void loadEnd(){
		try {
			for (int rowIndex = 0; rowIndex < currentSize; rowIndex++) {
				updateMD5(rowIndex);
			}
		}catch (Exception ee){
			String info = "DataTable:" + name;
			throw new DataSetException(String.format("loadEnd Exception. currentSize=%s type:\r\n%s",currentSize,info));
		}
	}

	public void updateMD5(int rowIndex){
		if(rowIndex < 0 || rowIndex > currentSize){
			//
		}else {
			StringBuilder md5Builder = new StringBuilder();
			for (int i = 0; i < colSize; i++) {
				ColInfo colInfo = colsInfo[i];
				String colName = colInfo.name;
				Object obj = getFieldValue(rowIndex, colName);
				md5Builder.append(obj);
			}
			String md5_fieldName = MD5_FIELD;
			int fieldIndex = this.getColIndex(md5_fieldName);
			if (fieldIndex < 0 || fieldIndex >= colsInfo.length) {
			} else {
				((String[]) (cols[fieldIndex]))[rowIndex] = MD5Util.toMD5(md5Builder.toString());
			}
		}
	}

	public void addRow(Map<String, Object> row) {
		this.latestWriteCounter.incrementAndGet(); // 更新当前的写标志
		this.queryRltCachedMap.clear(); // 清空缓存的查询结果数据

		if (this.currentSize >= this.currentCapacity) {
			extendCapacity();
			if (this.currentSize >= this.currentCapacity) {
				throw new DataSetException("DataTable:" + name + ",RuntimeException:can not extends array length.");
			}
		}

		StringBuilder md5Builder = new StringBuilder();

		try {
			Object obj = null;
			String fieldName = null;
			Iterator<Entry<String, Object>> it = row.entrySet().iterator();
			while (it.hasNext()) {
				int jj = 0;
				try {

					Entry<String, Object> entry = it.next();

					fieldName = entry.getKey();

					jj = this.getColIndex(fieldName);
					if (jj < 0 || jj >= colsInfo.length) {
						continue;
					}
					obj = entry.getValue();

					Object value = appendValue(this.currentSize,jj,obj);
					md5Builder.append(value);

				} catch (Exception ee) {
					String info = "DataTable:" + name + " ColumnName:" + colsInfo[jj].name + " Title:" + colsInfo[jj].title + " Type:" + colsInfo[jj].type + ",cloumnValue:" + obj.toString();
					throw new DataSetException("Put data with incompatible type:" + ee.getMessage() + "\r\n" + info);
				}
			}

			fieldName = MD5_FIELD;
			int fieldIndex = this.getColIndex(fieldName);
			if (fieldIndex < 0 || fieldIndex >= colsInfo.length) {
				return;
			}
			((String[]) (cols[fieldIndex]))[this.currentSize] = MD5Util.toMD5(md5Builder.toString());

			this.currentSize++;

		} catch (Exception ee) {
			String info = "DataTable:" + name;
			throw new DataSetException("add Row Exception. type:" + ee.getMessage() + "\r\n" + info);
		}
	}

	/**
	 * 根据行号移除一行记录
	 * 
	 * @param rowIndex
	 */
	public void removeRow(int rowIndex) {

		this.queryRltCachedMap.clear(); // 清空缓存的查询结果数据

		if (rowIndex < 0 || rowIndex > this.currentSize) {
			throw new DataSetException("DataTable:" + name + ",rowIndex gt currentSize.rowIndex:" + rowIndex + ",currentSize:" + currentSize);
		}
		try {
			// 使用同步对象，避免在删除时查询操作出现错误

			for (int i = 0; i < colSize; i++) {
				DataTypeEnum type = colsInfo[i].type;
				switch (type) {
				case DtInt: {
					int[] colArray = (int[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swapint(colArray, this.currentSize, rowIndex);
					continue;
				}
				case DtDouble: {
					double[] colArray = (double[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swapdouble(colArray, this.currentSize, rowIndex);
					continue;
				}
				case DtString:
				case DtJsonObj:
				case DtXmlObj: {
					String[] colArray = (String[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swap(colArray, this.currentSize, rowIndex);
					continue;
				}
				case DtLong: {
					long[] colArray = (long[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swaplong(colArray, this.currentSize, rowIndex);
					continue;
				}
				case DtFloat: {
					float[] colArray = (float[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swapfloat(colArray, this.currentSize, rowIndex);
					continue;
				}

				case DtShort: {
					short[] colArray = (short[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swapshort(colArray, this.currentSize, rowIndex);
					continue;
				}

				case DtByte: {
					byte[] colArray = (byte[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swapbyte(colArray, this.currentSize, rowIndex);
					continue;
				}

				case DtBool: {
					boolean[] colArray = (boolean[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swapboolean(colArray, this.currentSize, rowIndex);
					continue;
				}

				case DtObject: {
					ICellObject[] colArray = (ICellObject[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swap(colArray, this.currentSize, rowIndex);
					continue;
				}

				case DtByteArr: {
					byte[][] colArray = (byte[][]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swap(colArray, this.currentSize, rowIndex);
					continue;
				}

				case DtDate: {
					java.util.Date[] colArray = (java.util.Date[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swap(colArray, this.currentSize, rowIndex);
					continue;
				}

				case DtDateMsecLong: {
					long[] colArray = (long[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swaplong(colArray, this.currentSize, rowIndex);
					continue;
				}

				case DtDateSecInt: {
					int[] colArray = (int[]) cols[i];
					// for (int j = rowIndex; j < this.currentSize - 1; j++) {
					// colArray[j] = colArray[j + 1];
					// }
					swapint(colArray, this.currentSize, rowIndex);
					continue;
				}
				}
			}
			this.currentSize--;

		} catch (Exception e) {
			logger.error("removeRow Exception,rowIndex:" + rowIndex, e);
		}

	}

	private <T> void swap(T[] colArray, int currentSize, int swapRowIndex) {
		if (swapRowIndex + 1 == currentSize) {
			// 要替换的数据就是当前最后一行,不用交换
		} else {
			T swapValue = colArray[swapRowIndex];
			colArray[swapRowIndex] = colArray[currentSize - 1];
			colArray[currentSize - 1] = swapValue;
		}
	}

	private void swapbyte(byte[] colArray, int currentSize, int swapRowIndex) {
		if (swapRowIndex + 1 == currentSize) {
			// 要替换的数据就是当前最后一行,不用交换
		} else {
			byte swapValue = colArray[swapRowIndex];
			colArray[swapRowIndex] = colArray[currentSize - 1];
			colArray[currentSize - 1] = swapValue;
		}
	}

	private void swapboolean(boolean[] colArray, int currentSize, int swapRowIndex) {
		if (swapRowIndex + 1 == currentSize) {
			// 要替换的数据就是当前最后一行,不用交换
		} else {
			boolean swapValue = colArray[swapRowIndex];
			colArray[swapRowIndex] = colArray[currentSize - 1];
			colArray[currentSize - 1] = swapValue;
		}
	}

	private void swapshort(short[] colArray, int currentSize, int swapRowIndex) {
		if (swapRowIndex + 1 == currentSize) {
			// 要替换的数据就是当前最后一行,不用交换
		} else {
			short swapValue = colArray[swapRowIndex];
			colArray[swapRowIndex] = colArray[currentSize - 1];
			colArray[currentSize - 1] = swapValue;
		}
	}

	private void swapint(int[] colArray, int currentSize, int swapRowIndex) {
		if (swapRowIndex + 1 == currentSize) {
			// 要替换的数据就是当前最后一行,不用交换
		} else {
			int swapValue = colArray[swapRowIndex];
			colArray[swapRowIndex] = colArray[currentSize - 1];
			colArray[currentSize - 1] = swapValue;
		}
	}

	private void swaplong(long[] colArray, int currentSize, int swapRowIndex) {
		if (swapRowIndex + 1 == currentSize) {
			// 要替换的数据就是当前最后一行,不用交换
		} else {
			long swapValue = colArray[swapRowIndex];
			colArray[swapRowIndex] = colArray[currentSize - 1];
			colArray[currentSize - 1] = swapValue;
		}
	}

	private void swapfloat(float[] colArray, int currentSize, int swapRowIndex) {
		if (swapRowIndex + 1 == currentSize) {
			// 要替换的数据就是当前最后一行,不用交换
		} else {
			float swapValue = colArray[swapRowIndex];
			colArray[swapRowIndex] = colArray[currentSize - 1];
			colArray[currentSize - 1] = swapValue;
		}
	}

	private void swapdouble(double[] colArray, int currentSize, int swapRowIndex) {
		if (swapRowIndex + 1 == currentSize) {
			// 要替换的数据就是当前最后一行,不用交换
		} else {
			double swapValue = colArray[swapRowIndex];
			colArray[swapRowIndex] = colArray[currentSize - 1];
			colArray[currentSize - 1] = swapValue;
		}
	}

	public void updateRow(int rowIndex, Map<String, Object> row) {

		this.queryRltCachedMap.clear(); // 清空缓存的查询结果数据

		if (rowIndex >= this.currentSize) {

			throw new DataSetException("DataTable:" + name + ",rowIndex gt currentSize.rowIndex:" + rowIndex + ",currentSize:" + currentSize);

		}

		try {

			Iterator<Entry<String, Object>> it = row.entrySet().iterator();
			while (it.hasNext()) {
				int jj = 0;
				try {

					Entry<String, Object> entry = it.next();

					String fieldName = entry.getKey();
					jj = this.getColIndex(fieldName);

					if (jj < 0 || jj >= colsInfo.length) {
						continue;
					}
					Object obj = entry.getValue();

					appendValue(rowIndex,jj,obj);
				} catch (Exception ee) {
					String info = "DataTable:" + name + " ColumnName:" + colsInfo[jj].name + " Title:" + colsInfo[jj].title + " Type:" + colsInfo[jj].type;
					throw new DataSetException("Put data with incompatible type:" + ee.getMessage() + "\r\n" + info, ee);
				}
			}

			StringBuilder md5Builder = new StringBuilder();
			for (int i = 0; i < colSize; i++) {
				ColInfo colInfo = colsInfo[i];
				String colName = colInfo.name;
				Object obj = getFieldValue(rowIndex, colName);
				md5Builder.append(obj);
			}
			String fieldName = MD5_FIELD;
			int fieldIndex = this.getColIndex(fieldName);
			if (fieldIndex < 0 || fieldIndex >= colsInfo.length) {
				return;
			}
			((String[]) (cols[fieldIndex]))[rowIndex] = MD5Util.toMD5(md5Builder.toString());

		} catch (Exception ee) {
			String info = "DataTable:" + name;
			throw new DataSetException("update Row Exception. type:" + ee.getMessage() + "\r\n" + info, ee);
		}
	}

	private Object appendValue(int rowIndex,int fieldNameIndex,Object fieldNameValue){
		Object returnValue = null;
		switch (colsInfo[fieldNameIndex].type) {
		case DtInt:
			if (fieldNameValue == null) {
				((int[]) (cols[fieldNameIndex]))[rowIndex] = Integer.MIN_VALUE;
			}else {
				((int[]) (cols[fieldNameIndex]))[rowIndex] = ((Number) fieldNameValue).intValue();
			}
			returnValue = ((int[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtDouble:
			if (fieldNameValue == null) {
				((double[]) (cols[fieldNameIndex]))[rowIndex] = DataSetUtil.DOUBLE_MIN_VALUE;
			}else {
				((double[]) (cols[fieldNameIndex]))[rowIndex] = ((Number) fieldNameValue).doubleValue();
			}
			returnValue = ((double[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtString:
		case DtJsonObj:
		case DtXmlObj:
			if (fieldNameValue == null) {
				((String[]) (cols[fieldNameIndex]))[rowIndex] = null;
			}else {
				((String[]) (cols[fieldNameIndex]))[rowIndex] = (String) fieldNameValue;
			}
			returnValue = ((String[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtLong:
			if (fieldNameValue == null) {
				((long[]) (cols[fieldNameIndex]))[rowIndex] = Long.MIN_VALUE;
			}else {
				((long[]) (cols[fieldNameIndex]))[rowIndex] = ((Number) fieldNameValue).longValue();
			}
			returnValue = ((long[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtFloat:
			if (fieldNameValue == null) {
				((float[]) (cols[fieldNameIndex]))[rowIndex] = DataSetUtil.FLOAT_MIN_VALUE;
			}else {
				((float[]) (cols[fieldNameIndex]))[rowIndex] = ((Number) fieldNameValue).floatValue();
			}
			returnValue = ((float[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtShort:
			if (fieldNameValue == null) {
				((short[]) (cols[fieldNameIndex]))[rowIndex] = Short.MIN_VALUE;
			}else {
				((short[]) (cols[fieldNameIndex]))[rowIndex] = ((Number) fieldNameValue).shortValue();
			}
			returnValue = ((short[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtByte:
			if (fieldNameValue == null) {
				((byte[]) (cols[fieldNameIndex]))[rowIndex] = Byte.MIN_VALUE;
			}else {
				((byte[]) (cols[fieldNameIndex]))[rowIndex] = ((Number) fieldNameValue).byteValue();
			}
			returnValue = ((byte[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtBool:
			if (fieldNameValue == null) {
				((boolean[]) (cols[fieldNameIndex]))[rowIndex] = false;
			}else {
				((boolean[]) (cols[fieldNameIndex]))[rowIndex] = ((Boolean) fieldNameValue).booleanValue();
			}
			returnValue = ((boolean[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtObject:
			if (fieldNameValue == null) {
				((ICellObject[]) (cols[fieldNameIndex]))[rowIndex] = null;
			}else {
				((ICellObject[]) (cols[fieldNameIndex]))[rowIndex] = (ICellObject) fieldNameValue;
			}
			returnValue = ((ICellObject[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtByteArr:
			if (fieldNameValue == null) {
				((byte[][]) (cols[fieldNameIndex]))[rowIndex] = null;
			}else {
				((byte[][]) (cols[fieldNameIndex]))[rowIndex] = (byte[]) fieldNameValue;
			}
			returnValue = ((byte[][]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtDate:
			((java.util.Date[]) (cols[fieldNameIndex]))[rowIndex] = DataSetUtil.objectToDate(fieldNameValue, colsInfo[fieldNameIndex].dateFormat);
			returnValue = ((java.util.Date[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtDateMsecLong:
			((long[]) (cols[fieldNameIndex]))[rowIndex] = DataSetUtil.objectToDateMsecLong(fieldNameValue, colsInfo[fieldNameIndex].dateFormat);
			returnValue = ((long[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		case DtDateSecInt:
			((int[]) (cols[fieldNameIndex]))[rowIndex] = DataSetUtil.objectToDateSecInt(fieldNameValue, colsInfo[fieldNameIndex].dateFormat);
			returnValue = ((int[]) (cols[fieldNameIndex]))[rowIndex];
			break;
		}
		return returnValue;
	}

	/**
	 * 根据列名得到相应的位置
	 * 
	 * @param colName
	 *            列名
	 * @return 列的位置
	 */
	public int getColIndex(String colName) throws DataSetException {
		if (colName == null) {
			throw new DataSetException("Column name cann't be set to null.");
		}
		String tmpCol = colName.trim();
		int col = -1;
		for (int jj = 0; jj < this.colSize; jj++) {
			if (this.colsInfo[jj].name.equalsIgnoreCase(tmpCol)) {
				col = jj;
				break;
			}
		}
		if (col < 0 && !this.isIgnoreUnknowColumnException()) {
			throw new DataSetException("Error column name.");
		}
		return col;
	}

	/**
	 * 由外部调用锁住当前对象的读锁
	 */
	public void lockRead() {
		this.rwLock.readLock().lock();
	}

	/**
	 * 由外部调用解锁当前对象的读锁
	 */
	public void unlockRead() {
		this.rwLock.readLock().unlock();
	}

	/**
	 * 检查参数是否与当前datatable一致
	 * 
	 * @param qr
	 */
	public void checkQueryValid(QueryResult qr) {
		if (qr.getDataTable() != this) {
			throw new DataSetException("Can not use different dataTable for the same QueryResult object.");
		}
		if (qr.dtLatestUpdateCounter != this.getLatestWriterCounter()) {
			throw new DataSetException("using dirty data from old datatable.");
		}
	}

	/**
	 * 按照==条件过滤当前对象
	 * 
	 * @param colName
	 *            过滤的列
	 * @param value
	 *            待过滤的值
	 * @param qr
	 *            查询结果对象
	 */
	public void queryEqualData(String colName, Object value, QueryResult qr) {
		checkQueryValid(qr);
		int col = getColIndex(colName);
		if (col < 0) {
			logger.warn("queryEqualData,column(" + colName + ") does not exit in resource,all data will return");
			return;
		} // 表示错误的列名且忽略错误列名异常

		DataTypeEnum type = this.colsInfo[col].type;
		SimpleDateFormat dateFormat = this.colsInfo[col].dateFormat;
		boolean[] indexArr = qr.selectedIndexArr;

		int count = 0;
		switch (type) {
		case DtInt:
			count = EqualCalcFun.eq((int[]) (cols[col]), value, indexArr);
			break;
		case DtDouble:
			count = EqualCalcFun.eq((double[]) (cols[col]), value, indexArr);
			break;
		case DtString:
			count = EqualCalcFun.eq((String[]) (cols[col]), value, indexArr);
			break;
		case DtFloat:
			count = EqualCalcFun.eq((float[]) (cols[col]), value, indexArr);
			break;
		case DtShort:
			count = EqualCalcFun.eq((short[]) (cols[col]), value, indexArr);
			break;
		case DtBool:
			count = EqualCalcFun.eq((boolean[]) (cols[col]), value, indexArr);
			break;
		case DtByte:
			count = EqualCalcFun.eq((byte[]) (cols[col]), value, indexArr);
			break;
		case DtLong:
			count = EqualCalcFun.eq((long[]) (cols[col]), value, indexArr);
			break;
		case DtDate:
			java.util.Date dateObj = DataSetUtil.objectToDate(value, dateFormat);
			count = EqualCalcFun.eq((java.util.Date[]) (cols[col]), dateObj, indexArr);
			break;

		case DtDateMsecLong:
			long dateLong = DataSetUtil.objectToDateMsecLong(value, dateFormat);
			count = EqualCalcFun.eq((long[]) (cols[col]), dateLong, indexArr);
			break;
		case DtDateSecInt:
			int dateInt = DataSetUtil.objectToDateSecInt(value, dateFormat);
			count = EqualCalcFun.eq((int[]) (cols[col]), dateInt, indexArr);
			break;
		case DtObject: // 支持对象上的等于操作
			count = EqualCalcFun.eq((ICellObject[]) (cols[col]), value, indexArr);
			break;

		case DtXmlObj:
		case DtJsonObj:
		case DtByteArr:
		default:
			throw new DataSetException("Can't execute eq operator in such data type,type" + type);
		}
		qr.decrementSelected(count);
	}

	/**
	 * 按照!=条件过滤当前对象
	 * 
	 * @param colName
	 *            过滤的列
	 * @param value
	 *            待过滤的值
	 * @param qr
	 *            查询结果对象
	 */
	public void queryNotEqualData(String colName, Object value, QueryResult qr) {
		checkQueryValid(qr);

		int col = getColIndex(colName);
		if (col < 0) {
			logger.warn("queryNotEqualData,column(" + colName + ") does not exit in resource,all data will return");
			return;
		} // 表示错误的列名且忽略错误列名异常

		DataTypeEnum type = this.colsInfo[col].type;
		SimpleDateFormat dateFormat = this.colsInfo[col].dateFormat;
		boolean[] indexArr = qr.selectedIndexArr;

		int count = 0;
		switch (type) {
		case DtInt:
			count = NotEqualCalcFun.ne((int[]) (cols[col]), value, indexArr);
			break;
		case DtDouble:
			count = NotEqualCalcFun.ne((double[]) (cols[col]), value, indexArr);
			break;
		case DtString:
			count = NotEqualCalcFun.ne((String[]) (cols[col]), value, indexArr);
			break;
		case DtFloat:
			count = NotEqualCalcFun.ne((float[]) (cols[col]), value, indexArr);
			break;
		case DtShort:
			count = NotEqualCalcFun.ne((short[]) (cols[col]), value, indexArr);
			break;
		case DtBool:
			count = NotEqualCalcFun.ne((boolean[]) (cols[col]), value, indexArr);
			break;
		case DtByte:
			count = NotEqualCalcFun.ne((byte[]) (cols[col]), value, indexArr);
			break;
		case DtLong:
			count = NotEqualCalcFun.ne((long[]) (cols[col]), value, indexArr);
			break;
		case DtDate:
			java.util.Date dateObj = DataSetUtil.objectToDate(value, dateFormat);
			;
			count = NotEqualCalcFun.ne((java.util.Date[]) (cols[col]), dateObj, indexArr);
			break;

		case DtDateMsecLong:
			long dateLong = DataSetUtil.objectToDateMsecLong(value, dateFormat);
			count = NotEqualCalcFun.ne((long[]) (cols[col]), dateLong, indexArr);
			break;
		case DtDateSecInt:
			int dateInt = DataSetUtil.objectToDateSecInt(value, dateFormat);
			count = NotEqualCalcFun.ne((int[]) (cols[col]), dateInt, indexArr);
			break;
		case DtXmlObj:
		case DtJsonObj:
		case DtByteArr:
		case DtObject:
		default:
			throw new DataSetException("Can't execute eq operator in such data type,type" + type);
		}
		qr.decrementSelected(count);
	}

	/**
	 * 按照==条件过滤当前对象
	 * 
	 * @param colName
	 *            过滤的列
	 * @param value
	 *            待过滤的值
	 * @param qr
	 *            查询结果对象
	 */
	public void queryInData(String colName, String value, QueryResult qr) {
		checkQueryValid(qr);
		int col = getColIndex(colName);
		if (col < 0) {
			logger.warn("queryInData,column(" + colName + ") does not exit in resource,all data will return");
			return;
		} // 表示错误的列名且忽略错误列名异常

		DataTypeEnum type = this.colsInfo[col].type;
		SimpleDateFormat dateFormat = this.colsInfo[col].dateFormat;
		boolean[] indexArr = qr.selectedIndexArr;

		int count = 0;
		switch (type) {
		case DtInt:
			count = InCalcFun.in((int[]) (cols[col]), value, indexArr);
			break;
		case DtDouble:
			count = InCalcFun.in((double[]) (cols[col]), value, indexArr);
			break;
		case DtString:
			count = InCalcFun.in((String[]) (cols[col]), value, indexArr);
			break;

		// need add for every DataType
		case DtFloat:
			count = InCalcFun.in((float[]) (cols[col]), value, indexArr);
			break;
		case DtShort:
			count = InCalcFun.in((short[]) (cols[col]), value, indexArr);
			break;
		case DtByte:
			count = InCalcFun.in((byte[]) (cols[col]), value, indexArr);
			break;
		case DtLong:
			count = InCalcFun.in((long[]) (cols[col]), value, indexArr);
			break;
		case DtDate:
			// java.util.Date dateObj=DataSetUtil.objectToDate(value,
			// dateFormat);
			// count=InCalcFun.in((java.util.Date[])(cols[col]), dateObj,
			// indexArr);
			count = InCalcFun.in((java.util.Date[]) (cols[col]), value, indexArr, dateFormat);
			break;

		case DtDateMsecLong:
			// long dateLong=DataSetUtil.objectToDateMsecLong(value,
			// dateFormat);
			// count=InCalcFun.in((long[])(cols[col]), dateLong, indexArr);
			count = InCalcFun.in((long[]) (cols[col]), InCalcFun.dateStringToNumberStr(value, dateFormat, false), indexArr);
			break;
		case DtDateSecInt:
			// int dateInt=DataSetUtil.objectToDateSecInt(value, dateFormat);
			// count=InCalcFun.in((int[])(cols[col]), dateInt, indexArr);
			count = InCalcFun.in((int[]) (cols[col]), InCalcFun.dateStringToNumberStr(value, dateFormat, true), indexArr);
			break;
		case DtBool:
		case DtXmlObj:
		case DtJsonObj:
		case DtByteArr:
		case DtObject:
		default:
			throw new DataSetException("Can't execute 'in' operator in such data type,type" + type);
		}
		qr.decrementSelected(count);
	}

	/**
	 * 选在在范围之内的数据
	 * 
	 * @param colName
	 *            列名称
	 * @param minVal
	 *            最小值 >=
	 * @param maxVal
	 *            最大值 <
	 * @param qr
	 *            选择集合
	 */
	public void queryBetweenData(String colName, double minVal, double maxVal, QueryResult qr) {
		checkQueryValid(qr);
		int col = getColIndex(colName);
		if (col < 0) {
			logger.warn("queryBetweenData,column(" + colName + ") does not exit in resource,all data will return");
			return;
		} // 表示错误的列名且忽略错误列名异常

		DataTypeEnum type = this.colsInfo[col].type;

		boolean[] indexArr = qr.selectedIndexArr;
		int count = 0;

		switch (type) {
		case DtInt: {
			int[] colValArr = (int[]) (cols[col]);
			count = BetweenCalcFun.bw(colValArr, minVal, maxVal, indexArr);
			break;
		}
		case DtDouble: {
			double[] colValArr = (double[]) (cols[col]);
			count = BetweenCalcFun.bw(colValArr, minVal, maxVal, indexArr);
			break;
		}
		case DtShort: {
			short[] colValArr = (short[]) (cols[col]);
			count = BetweenCalcFun.bw(colValArr, minVal, maxVal, indexArr);
			break;
		}
		case DtFloat: {
			float[] colValArr = (float[]) (cols[col]);
			count = BetweenCalcFun.bw(colValArr, minVal, maxVal, indexArr);
			break;
		}
		case DtByte: {
			byte[] colValArr = (byte[]) (cols[col]);
			count = BetweenCalcFun.bw(colValArr, minVal, maxVal, indexArr);
			break;
		}
		case DtLong: {
			long[] colValArr = (long[]) (cols[col]);
			count = BetweenCalcFun.bw(colValArr, minVal, maxVal, indexArr);
			break;
		}
		case DtString: {
			String[] colValArr = (String[]) (cols[col]);
			count = BetweenCalcFun.bw(colValArr, minVal + "", maxVal + "", indexArr);
			break;
		}
		default:
			throw new DataSetException("Can't execute bw operator in such data type,type" + type);
		}
		qr.decrementSelected(count);
	}

	/**
	 * 选在在范围之内的数据
	 * 
	 * @param colName
	 *            列名称
	 * @param minVal
	 *            最小值 >=
	 * @param maxVal
	 *            最大值 <
	 * @param qr
	 *            选择集合
	 */
	public void queryBetweenDateData(String colName, Object minVal, Object maxVal, QueryResult qr) {
		checkQueryValid(qr);
		int col = getColIndex(colName);
		if (col < 0) {
			logger.warn("queryBetweenDateData,column(" + colName + ") does not exit in resource,all data will return");
			return;
		} // 表示错误的列名且忽略错误列名异常

		DataTypeEnum type = this.colsInfo[col].type;
		SimpleDateFormat dateFormat = this.colsInfo[col].dateFormat;

		boolean[] indexArr = qr.selectedIndexArr;
		int count = 0;

		switch (type) {
		case DtDateSecInt: {
			int minDateInt = DataSetUtil.objectToDateSecInt(minVal, dateFormat);
			int maxDateInt = DataSetUtil.objectToDateSecInt(maxVal, dateFormat);
			if (minDateInt == Integer.MIN_VALUE || maxDateInt == Integer.MIN_VALUE) {
				throw new DataSetException("Can not use null or error param for between date.");
			}
			int[] colValArr = (int[]) (cols[col]);
			count = BetweenCalcFun.bw(colValArr, (double) minDateInt, (double) maxDateInt, indexArr);
			break;
		}
		case DtDateMsecLong: {
			long minDateLong = DataSetUtil.objectToDateMsecLong(minVal, dateFormat);
			long maxDateLong = DataSetUtil.objectToDateMsecLong(maxVal, dateFormat);
			if (minDateLong == Long.MIN_VALUE || maxDateLong == Long.MIN_VALUE) {
				throw new DataSetException("Can not use null or error param for between date.");
			}
			long[] colValArr = (long[]) (cols[col]);
			count = BetweenCalcFun.bw(colValArr, (double) minDateLong, (double) maxDateLong, indexArr);
			break;
		}
		case DtDate: {
			java.util.Date minDate = DataSetUtil.objectToDate(minVal, dateFormat);
			java.util.Date maxDate = DataSetUtil.objectToDate(maxVal, dateFormat);
			if (minDate == null || maxDate == null) {
				throw new DataSetException("Can not use null or error param for between date.");
			}
			java.util.Date[] colValArr = (java.util.Date[]) (cols[col]);
			count = BetweenCalcFun.bwDate(colValArr, minDate, maxDate, indexArr);
			break;
		}
		default:
			throw new DataSetException("Can't execute bw date operator in such data type,type" + type);
		}
		qr.decrementSelected(count);
	}

	// like-------------------------------------------------------------------
	/**
	 * 对字符串执行 like操作
	 * 
	 * @param colName
	 *            列名
	 * @param likeStr
	 *            %字符串
	 * @param qr
	 *            查询结果对象
	 */
	public void queryLikeData(String colName, String likeStr, QueryResult qr) {
		checkQueryValid(qr);
		if (likeStr == null || likeStr.trim().length() == 0) {
			return;
		}
		String tmpStr = likeStr.trim();
		if (tmpStr.equals("*") || tmpStr.equals("%")) {
			return;
		}

		int col = getColIndex(colName);
		if (col < 0) {
			logger.warn("queryLikeData,column(" + colName + ") does not exit in resource,all data will return");
			return;
		} // 表示错误的列名且忽略错误列名异常

		int count = 0;
		boolean[] indexArr = qr.selectedIndexArr;

		DataTypeEnum type = this.colsInfo[col].type;

		switch (type) {
		case DtString: {
			String[] colValArr = (String[]) (cols[col]);
			count = StringCalcFun.like(colValArr, likeStr, indexArr);
			break;
		}
		case DtInt: {
			int[] colValArr = (int[]) (cols[col]);
			count = StringCalcFun.like(colValArr, likeStr, indexArr);
			break;
		}
		case DtLong: {
			long[] colValArr = (long[]) (cols[col]);
			count = StringCalcFun.like(colValArr, likeStr, indexArr);
			break;
		}
		case DtShort: {
			short[] colValArr = (short[]) (cols[col]);
			count = StringCalcFun.like(colValArr, likeStr, indexArr);
			break;
		}
		case DtByte: {
			byte[] colValArr = (byte[]) (cols[col]);
			count = StringCalcFun.like(colValArr, likeStr, indexArr);
			break;
		}
		case DtFloat: {
			float[] colValArr = (float[]) (cols[col]);
			count = StringCalcFun.like(colValArr, likeStr, indexArr);
			break;
		}
		case DtDouble: {
			double[] colValArr = (double[]) (cols[col]);
			count = StringCalcFun.like(colValArr, likeStr, indexArr);
			break;
		}
		default:
			throw new DataSetException("Can't execute like operator in such data type,type" + type);
		}
		qr.decrementSelected(count);
	}

	/**
	 * 过滤 colName value的数据,过滤条件包括 (>,>=,<,<=)
	 * 
	 * @param colName
	 *            列名
	 * @param value
	 *            值 ,不能为空
	 * @param qr
	 *            待过滤查询结果对象
	 * @param opType
	 *            过滤条件
	 */
	public void queryLogicCalcData(String colName, Object value, QueryResult qr, OperatorTypeEnum opType) {
		checkQueryValid(qr);
		if (value == null) {
			throw new DataSetException("logic compare value is not null");
		}

		int col = getColIndex(colName);
		if (col < 0) {
			logger.warn("queryLogicCalcData,column(" + colName + ") does not exit in resource,all data will return");
			return;
		} // 表示错误的列名且忽略错误列名异常

		DataTypeEnum type = this.colsInfo[col].type;
		SimpleDateFormat dateFormat = this.colsInfo[col].dateFormat;
		boolean[] indexArr = qr.selectedIndexArr;

		int count = 0;
		try {
			switch (type) {
			case DtInt:
				count = LogicCalcFun.logicCalcGtGeLtLe((int[]) (cols[col]), value, indexArr, opType);
				break;
			case DtDouble:
				count = LogicCalcFun.logicCalcGtGeLtLe((double[]) (cols[col]), value, indexArr, opType);
				break;
			case DtString:
				count = LogicCalcFun.logicCalcGtGeLtLe((String[]) (cols[col]), value, indexArr, opType);
				break;
			case DtFloat:
				count = LogicCalcFun.logicCalcGtGeLtLe((float[]) (cols[col]), value, indexArr, opType);
				break;
			case DtShort:
				count = LogicCalcFun.logicCalcGtGeLtLe((short[]) (cols[col]), value, indexArr, opType);
				break;
			case DtByte:
				count = LogicCalcFun.logicCalcGtGeLtLe((byte[]) (cols[col]), value, indexArr, opType);
				break;
			case DtLong:
				count = LogicCalcFun.logicCalcGtGeLtLe((long[]) (cols[col]), value, indexArr, opType);
				break;
			case DtDate:
				java.util.Date dateVal = DataSetUtil.objectToDate(value, dateFormat);
				count = LogicCalcFun.logicCalcGtGeLtLe((java.util.Date[]) (cols[col]), dateVal, indexArr, opType);
				break;
			case DtDateMsecLong:
				long dateLong = DataSetUtil.objectToDateMsecLong(value, dateFormat);
				if (dateLong == Long.MIN_VALUE) {
					throw new DataSetException("Date param can not be set to null.");
				}
				count = LogicCalcFun.logicCalcGtGeLtLe((long[]) (cols[col]), dateLong, indexArr, opType);
				break;
			case DtDateSecInt:
				int dateInt = DataSetUtil.objectToDateSecInt(value, dateFormat);
				if (dateInt == Integer.MIN_VALUE) {
					throw new DataSetException("Date param can not be set to null.");
				}
				count = LogicCalcFun.logicCalcGtGeLtLe((int[]) (cols[col]), dateInt, indexArr, opType);
				break;
			case DtBool:
			default:
				throw new DataSetException("Can't execute operator in such data type,type" + type + ",operator:" + opType);
			}
			qr.decrementSelected(count);
		} catch (Exception ee) {
			throw new DataSetException("Can't calucate,type:" + type + ",Operator:" + opType + "\r\n" + ee.getMessage());
		}
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

}
