package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.dependency.EnumTableType;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public interface CallableRelatedTaskDependency<TABLE, CALLRESULT> {
    /**
     * 获取表类型
     *
     * @return
     */
    public EnumTableType getEnumTableType();

    /**
     * 获取当前关联的表的别名
     *
     * @return
     */
    public Set<String> getCurrentTableAlias();

    /**
     * 是否有当前秒的描述
     *
     * @return
     */
    public boolean hasCurrentTable();

    /**
     * 获取当前表描述
     *
     * @return
     */
    public TABLE getCurrentTable(String key);

    public void addCurrentTable(TABLE table);

    /**
     * 遍历分组信息
     *
     * @return
     */
    public Iterator<Map.Entry<String, TABLE>> entryTABLEIterator();

    /**
     * 当前表是否有需要执行的任务
     *
     * @return
     */
    public boolean hasCurrentCallableRelatedTask();

    /**
     * 获取当前表描述对应的执行任务列表
     *
     * @return
     */
    public List<Callable<CALLRESULT>> getCurrentCallableRelatedTask();


    /**
     * 是否有下级依赖
     *
     * @return
     */
    public boolean hasNext();

    /**
     * 获取下级依赖
     *
     * @return
     */
    public CallableRelatedTaskDependency getNext();

    /**
     * 设置当前描述的下一级的描述
     *
     * @param next
     */
    public void setNext(CallableRelatedTaskDependency next);

    /**
     * 销毁临时内容
     *
     * @param defineType
     */
    public void destory(String defineType);
}
