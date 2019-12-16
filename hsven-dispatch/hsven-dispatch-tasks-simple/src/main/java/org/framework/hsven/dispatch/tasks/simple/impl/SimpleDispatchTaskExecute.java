package org.framework.hsven.dispatch.tasks.simple.impl;

import org.framework.hsven.dispatch.tasks.core.business.IDispatchTaskExecute;
import org.springframework.stereotype.Service;

/**
 * @author sven
 * @date 2019/10/17 15:10
 * 各个派发出来的任务业务执行service
 */
@Service("simpleDispatchTaskExecute")
public class SimpleDispatchTaskExecute implements IDispatchTaskExecute {

	@Override
	public void dispatch(Long taskId, String taskType) {

	}
}
