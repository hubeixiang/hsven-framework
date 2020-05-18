package org.framework.hsven.dataset.datatable.api;


import org.framework.hsven.dataset.datatable.exception.DataSetException;

/**
 *该接口用于dataTable中存放纯对象类型，实现者需要实现相应的方法以支持表达式运算，
 *如没有运算需求，方法也可以不实现。
 * 推荐各个方法的传入参数采用 JSONObject 作为参数，以实现参数的灵活性，
 * 也可以自行设计传入参数的类型，以提升系统的处理效率
 */
public interface ICellObject 
{
	/**
	 * 比较运算
	 * @param expObj  用户输入的参数，可以为任意的类型和格式，由实现者设计
	 * @return >0 表示当前对象大于参数expObj对象， <0 表示当前对象小于参数expObj对象， =0表示相等
	 * @throws DataSetException 参数错误时抛出异常
	 */
	public int  compare(Object expObj) throws DataSetException;
	
	/**
	 * 判断当前对象是否在参数对象之间 
	 * @param expObj1 用户输入的参数，可以为任意的类型和格式，由实现者设计
	 * @param expObj2 用户输入的参数，可以为任意的类型和格式，由实现者设计
	 * @return 当当前对象 >=expObj1 且 < expObj2时返回 true，否则返回 false
	 * @throws DataSetException 参数错误时抛出异常
	 */
	public boolean between(Object expObj1, Object expObj2) throws DataSetException;
	
	
	
	/**
	 * 匹配运算
	 * @param expObj 用户输入的参数，可以为任意的类型，由实现者设计
	 * @return true 表示 匹配，否则表示不匹配
	 * @throws DataSetException 参数错误时抛出异常
	 */
	public boolean like(Object expObj) throws DataSetException;	
	
	
	/**
	 * in 运算
	 * @param expObj 用户输入的参数，可以为任意的类型和格式，由实现者设计
	 * @return true 表示对象包含在参数中，否则表示不包含
	 * @throws DataSetException 参数错误时抛出异常
	 */
	public boolean in(Object expObj)  throws DataSetException;

	
	/**
	 * 得到对象的编码的字符串表示，主要用于调试使用
	 * @return 当前对象的字符串表示
	 */
	public String toEncodingString();
	
	
	/**
	 * 得到编码的字节串表示
	 * @return 字节串
	 */
	public byte[] encode();
	
	
	/**
	 * 
	 * 根据字节串解码当前对象 
	 * @param encodeBytes 字节串编码
	 * @throws DataSetException 解码错误时抛出异常
	 */
	public void decode(byte[] encodeBytes) throws DataSetException;
	
	
}
