package org.framework.hsven.dispatch.tasks.simple.impl;

import org.framework.hsven.dispatch.tasks.core.business.IDispatchTaskExecute;
import org.framework.hsven.dispatch.tasks.core.business.IDispatchTaskOperation;
import org.framework.hsven.dispatch.tasks.core.distribute.impl.AbstractDispatchTasksService;
import org.framework.hsven.dispatch.tasks.core.entity.DispatchProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 一个简单的任务派发实现,提供
 *
 * @author sven
 * @date 2019/10/17 10:45
 */
@Service("simpleDispatchTasksService")
public class SimpleDispatchTasksService extends AbstractDispatchTasksService {
	@Value("${dispatch.tasks.simple.debug:false}")
	private boolean isDebug;

	@Value("${dispatch.tasks.simple.max-pool-size:2}")
	private int taskMaxPoolSize;

	@Value("${dispatch.tasks.simple.task-type:}")
	private String taskType;

	@Autowired
	@Qualifier("simpleDispatchTaskOperation")
	IDispatchTaskOperation iDispatchTaskOperation;

	@Autowired
	@Qualifier("simpleDispatchTaskExecute")
	IDispatchTaskExecute iDispatchTaskExecute;

	public void customAfterPropertiesSet() {
		//没有要单独执行的初始化
	}

	@Override
	public DispatchProperties getDispatchProperties() {
		DispatchProperties dispatchProperties = new DispatchProperties();
		dispatchProperties.setDebug(isDebug);
		dispatchProperties.setTaskMaxPoolSize(taskMaxPoolSize);
		dispatchProperties.setTaskType(taskType);
		return dispatchProperties;
	}

	public IDispatchTaskOperation getIDispatchTaskOperation() {
		return iDispatchTaskOperation;
	}

	public IDispatchTaskExecute getIDispatchTaskExecute() {
		return iDispatchTaskExecute;
	}
}
