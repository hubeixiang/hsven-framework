package org.framework.hsven.dataset.datatable.cache;

import org.framework.hsven.dataset.datatable.beans.OperatorTypeEnum;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

import java.io.Serializable;
import java.util.Arrays;


/**
 * @author duhailiang
 *  非线程安全对象，用于存储
 */
public class QueryResult implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//当前对象所对应的 datatable
	private DataTable dataTable=null;
		
	//与datatable的当前行数相同
	private int currentSize=0;
	
	//当前查询结果数据对应每一行，true表示被选择，false表示没有被选择
	public boolean[] selectedIndexArr=null;

	//记录当前结果集中的对象数,即选中的对象数
	public int selectedResultSize=0;
	
	//指示dataTable中最新的更新计数器
	public int dtLatestUpdateCounter=0;		
	
	/**
	 * 根据当前dataTable得到全量的查询结果
	 * @param dataTable 待查询的dataTable
	 */
	private QueryResult(DataTable dataTable)
	{
		this.dataTable=dataTable;	
		this.currentSize=dataTable.getCurrentSize();
		selectedIndexArr=Arrays.copyOf(dataTable.genWillClonedSelectedIndexArr(),currentSize);
		this.selectedResultSize=this.currentSize;
		this.dtLatestUpdateCounter=dataTable.getLatestWriterCounter();	
	}
		
	
	public QueryResult()
	{
	}
	
	/**
	 * clone当前对象到一个新的查询对象中，
	 * @return 查询对象
	 */
	public QueryResult query()
	{
		QueryResult qr=new QueryResult();
		qr.dataTable=this.dataTable;
		qr.currentSize=this.currentSize;
		qr.selectedIndexArr=Arrays.copyOf(this.selectedIndexArr, this.currentSize);
		qr.selectedResultSize=this.selectedResultSize;
		qr.dtLatestUpdateCounter=this.dtLatestUpdateCounter;
		return qr;
	}
	
	/**
	 * 根据dataTable得到一个新的查询结果对象
	 * @param dataTable 待查询的DataTable
	 * @return 一个新的查询对象
	 */
	public static QueryResult genOneInstance(DataTable dataTable)
	{
		if(dataTable==null)
		{
			throw new DataSetException("DataTable param is null.");
		}
		return new QueryResult(dataTable);
	}

	/**
	 * 产生一个选择行为空的queryResult 对象
	 * @param dataTable queryResult所关联的dataTable对象
	 * @return QueryResult 对象
	 */
	public  static QueryResult genOneEmptySelectedInstance(DataTable dataTable)
	{		
		if(dataTable==null)
		{
			throw new DataSetException("DataTable param is null.");
		}
		QueryResult qr=new QueryResult();
		qr.dataTable=dataTable;
		qr.currentSize=dataTable.getCurrentSize();
		qr.selectedIndexArr=new boolean[qr.currentSize];
		qr.selectedResultSize=0;
		qr.dtLatestUpdateCounter=dataTable.getLatestWriterCounter();	
		return qr;
	}
	
	
	/**
	 * 清空当前查询对象，释放其所有资源,该对象将不可再用
	 */
	public void clear()
	{
		this.dataTable=null;
		this.selectedIndexArr=null;
		this.currentSize=0;
		this.selectedResultSize=0;
	}
	
	/**
	 * 得到当前的查询结果需要匹配的总行数，和datatable 行的数量相同
	 * @return
	 */
	public int getCurrentSize() {
		return currentSize;
	}

 
	/**
	 *得到当前查询对象的dataTable对象 
	 * @return
	 */
	public DataTable getDataTable() {
		return dataTable;
	}
	
 

	/**
	 * 得到当前被查询到的行的数量
	 * @return 被选中行数
	 */
	public int getSelectedResultSize() {
		return selectedResultSize;
	}


	
	/**
	 * 减掉相应的选择数
	 * @param count 被减掉的选择数，为负数时表示增加
	 */
	public void decrementSelected(int count)
	{	
		selectedResultSize=selectedResultSize-count;
	}
	
	/**
	 * 相等表达式
	 * @param field 字段名称
	 * @param obj 字段值
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult eq(String field,Object obj )
	{
		dataTable.queryEqualData(field, obj, this);
		return this;
	}
	
	/**
	 * 相等表达式
	 * @param field 字段名称
	 * @param line 字段用逗号分隔的字符串表示
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult in(String field,String line )
	{
		dataTable.queryInData(field, line, this);
		return this;
	}
	
	
	/**
	 * 不相等表达式
	 * @param field 字段名称
	 * @param obj 字段值
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult ne(String field,Object obj )
	{
		dataTable.queryNotEqualData(field, obj, this);
		return this;
	}
	
	
	/**
	 * 范围表达式
	 * @param field 字段名称
	 * @param minVal >= 最小值
	 * @param maxVal < 最大值
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult bw(String field,double minVal,double maxVal )
	{
		dataTable.queryBetweenData(field, minVal, maxVal, this);
		return this;
	}
	
	/**
	 * 时间范围表达式
	 * @param field 字段名称
	 * @param minVal >= 最小时间值
	 * @param maxVal < 最大时间值
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult bwt(String field,Object minVal,Object maxVal )
	{
		dataTable.queryBetweenDateData(field, minVal, maxVal, this);
		return this;
	}
	
	/**
	 * 字符串匹配表达式
	 * @param field 字段名称
	 * @param likeExpr 匹配表达式，支持通配符号 %
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult like(String field,String likeExpr)
	{
		dataTable.queryLikeData(field, likeExpr, this);
		return this;
	}

	
	/**
	 * 大于 greater than
	 * @param field 列名
	 * @param obj 比较值
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult gt(String field,Object obj)
	{
		dataTable.queryLogicCalcData(field, obj, this, OperatorTypeEnum.OtGt);
		return this;
	}
	
	/**
	 * 大于等于  greater or equal than
	 * @param field 列名
	 * @param obj 比较值
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult ge(String field,Object obj)
	{
		dataTable.queryLogicCalcData(field, obj, this, OperatorTypeEnum.OtGe);
		return this;
	}
	
	/**
	 * 小于 less than
	 * @param field 列名
	 * @param obj 比较值
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult lt(String field,Object obj)
	{
		dataTable.queryLogicCalcData(field, obj, this, OperatorTypeEnum.OtLt);
		return this;
	}

	/**
	 * 小于等于 less or equal than
	 * @param field 列名
	 * @param obj 比较值
	 * @return 当前结果集的过滤结果 
	 */
	public QueryResult le(String field,Object obj)
	{
		dataTable.queryLogicCalcData(field, obj, this, OperatorTypeEnum.OtLe);
		return this;
	}
	
	/**
	 * 在当前查询对象基础上查询表达式，得到新的查询结果对象
	 * @param expStr 查询表达式
	 * @return 新的查询结果对象，当前对象本身的选择结果不被改变
	 */
	public QueryResult query(String expStr)
	{
		return dataTable.queryFromQueryResult(expStr, this);
	}
	
}

