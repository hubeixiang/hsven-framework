package org.framework.hsven.dataset.datatable.api;

import org.framework.hsven.dataset.datatable.cache.Rows;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

/**
 * 行处理器，在执行完查询后进行结果处理时将被调用
 */
public interface IRowsProcessor
{	
	/**
	 *处理函数，每次调用会处理一页的rows 
	 * @param totalRowSize	总的处理行数，在一次表达式执行中保持不变
	 * @param startRow 本页处理的开始行号，第一次处理时，从0开始编号
	 * @param rowSize 本次处理rows对象中的总行数
	 * @param rows 当前页处理传入的行集合对象
	 * @throws Exception 处理过程中发生异常时将停止后面的处理，并把异常信息转换为 DataSetException 传入 whenException
	 */
	public void process(final int totalRowSize, final int startRow, final int rowSize, final Rows rows) throws Exception;

	/**
	 * 在process 调用以前，本方法将被调用
	 * @param totalRowSize 查询结果总行数
	 * @param rowPageSize 处理时使用的每页的大小（由调用 execute时传入，但是为<=0时，将调整为全部的大小）
	 */
	public void beforeProcess(final int totalRowSize, final int rowPageSize);
	
	/**
	 * 处理结束时会被调用
	 */
	public void afterProcessed();	
	
	
	/**
	 * 当发生异常时被调用，异常包括 process中抛出的异常和execute执行查询过程中的其他差异，
	 * 该方法被调用时，afterProcessed不会被调用，如果在查询过程中发生异常，beforeProcess和process也不会被调用
	 * @param ex 所有异常会被转换为 DataSetException传入
	 */
	public void whenException(final DataSetException ex);

}
