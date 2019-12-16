package org.framework.hsven.dispatch.tasks.core.distribute;

import org.framework.hsven.dispatch.tasks.core.business.IDispatchTaskExecute;
import org.framework.hsven.dispatch.tasks.core.business.IDispatchTaskOperation;
import org.framework.hsven.dispatch.tasks.core.entity.DispatchProperties;

/**
 * 派发任务的服务接口
 *
 * @author sven
 * @date 2019/10/16 10:21
 */
public interface IDispatchTasksService {
	/**
	 * 自定义的bean初始化后处理,在InitializingBean的afterPropertiesSet中执行
	 */
	public void customAfterPropertiesSet();

	/**
	 * 获取任务派发的相关外部限制属性,不能为空
	 *
	 * @return
	 */
	public DispatchProperties getDispatchProperties();

	/**
	 * 获取任务派发实体的操作服务
	 *
	 * @return
	 */
	IDispatchTaskOperation getIDispatchTaskOperation();

	/**
	 * 获取待派发任务的,定制的派发接口
	 *
	 * @return
	 */
	IDispatchTaskExecute getIDispatchTaskExecute();

	/**
	 * 同步复位任务状态
	 *
	 * @param createClient  任务的创建client
	 * @param taskStatusOld 待同步的任务状态
	 * @param taskStatusNew 同步后的任务状态
	 */
	public void synchronousReset(String createClient, int taskStatusOld, int taskStatusNew);

	/**
	 * 定时任务,定时扫描并派发任务
	 */
	public void scanAndDispatchTasks();
}
