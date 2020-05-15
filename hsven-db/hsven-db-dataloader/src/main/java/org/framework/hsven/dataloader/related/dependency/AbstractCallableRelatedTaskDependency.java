package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.dependency.EnumTableType;
import org.framework.hsven.dataloader.related.ITableLoaderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * 每个都是同一级别下的所有要处理的表信息
 *
 * @param <TABLE>      要处理的表信息,对应的分组是按照不同的关联字段进行分组的
 * @param <CALLRESULT> 表加载完毕后的返回结果描述
 */
public abstract class AbstractCallableRelatedTaskDependency<TABLE, CALLRESULT> implements CallableRelatedTaskDependency<TABLE, CALLRESULT> {
    private static Logger logger = LoggerFactory.getLogger(AbstractCallableRelatedTaskDependency.class);
    //保存的当前操作的表的别名集合
    private Set<String> tableAliasSet = new HashSet<>();
    private EnumTableType tableType;
    private List<Callable<CALLRESULT>> callableTasks = new ArrayList<>();
    private CallableRelatedTaskDependency next;


    public AbstractCallableRelatedTaskDependency(EnumTableType tableType) {
        this.tableType = tableType;
    }

    @Override
    public EnumTableType getEnumTableType() {
        return tableType;
    }

    @Override
    public Set<String> getCurrentTableAlias() {
        return tableAliasSet;
    }

    @Override
    public boolean hasCurrentTable() {
        return tableAliasSet != null && tableAliasSet.size() > 0;
    }

    @Override
    public boolean hasCurrentCallableRelatedTask() {
        return callableTasks != null && callableTasks.size() > 0;
    }

    @Override
    public List<Callable<CALLRESULT>> getCurrentCallableRelatedTask() {
        return callableTasks;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public CallableRelatedTaskDependency getNext() {
        return next;
    }

    @Override
    public void setNext(CallableRelatedTaskDependency next) {
        this.next = next;
    }

    @Override
    public void destory(String defineType) {
        if (callableTasks != null) {
            for (Callable<CALLRESULT> task : callableTasks) {
                if (task != null && task instanceof ITableLoaderTask) {
                    ((ITableLoaderTask) task).destory();
                } else {
                    logger.warn(String.format("MainTableLoader resourceType=%s child table load task exception. task is null or not implements IChildTableLoaderTask", defineType));
                }
            }
            callableTasks.clear();
        }
        callableTasks = null;

        if (hasNext()) {
            getNext().destory(defineType);
        }
    }
}
