package org.framework.hsven.quartz.core.api;

import java.io.Serializable;

/**
 * 针对需要参数的定时任务指定的参数生成接口,
 * 每种任务在已经定制完成任务后,
 * 该接口对应的类不要再次修改,
 * 如果再次修改会导致已经存在的任务再定时启动时失败,所有任务都不会初始化成功,不会再次执行
 */
public interface ISchedulerArguments extends Serializable {
    Object[] arguments();

    Class<?>[] parameterTypes();
}
