package org.framework.hsven.dataset.datatable.api;

import org.framework.hsven.dataset.datatable.cache.DataTable;
import org.framework.hsven.dataset.datatable.exception.DataSetException;

public interface IRowsLoader
{
	/**
	 * 在load被调用以前进行调用
	 */
	public void beforeLoad();
	
	/**
	 * 完成load操作，可以使用 clear 和 addRow对 destTable进行加载操作，这两个函数本身是非线程安全的，但在
	 * 此处调用是线程安全的。
	 * @param destTable 待load的dataTable，在上面可以调用 clear 和 addRow
	 */
	public void load(DataTable destTable);
		
	/**
	 *在load调用完成后被调用 
	 */
	public void afterLoaded();
	
	
	/**
	 * 当上面三个方法在调用的过程中发生异常，将调用本方法，当该方法抛出异常时，将被丢掉
	 * @param ex
	 */
	public void whenException(DataSetException ex);

}

