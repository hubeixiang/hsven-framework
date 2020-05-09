package org.framework.hsven.dataloader.related;

import java.util.concurrent.Callable;

public interface ITableLoaderTask<V> extends Callable<V> {

	public void destory();

}
