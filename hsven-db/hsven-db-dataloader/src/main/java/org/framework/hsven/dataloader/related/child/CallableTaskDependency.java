package org.framework.hsven.dataloader.related.child;

import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.related.TableLoadResult;
import org.framework.hsven.dataloader.related.ITableLoaderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class CallableTaskDependency {
    public static Logger childTableLoadLogger = LoggerFactory.getLogger("childTableLoadLogger");
    //本对象是直接关联主表,如果是直接关联主表,可以直接获取值,如果是关联的辅表,需要延时获取值(因为只有等关联的辅表有值后才能获取到值)
    private final boolean isRelatedMainTable;
    //当前关联的左关联表
    private Set<String> childTableAlias = new HashSet<String>();
    //当前关联的左关联表依据关联的字段分类
    private Map<String, List<SimpleChildTable>> relatedTableField_childTableList_map = new HashMap<String, List<SimpleChildTable>>();
    //当前关联的左关联表依据关联的字段分类的值集合,用于直接关联主表的
    private Map<String, RelatedFieldValueSet> relatedTableFieldName_relatedTableFieldValueSet_map = new HashMap<String, RelatedFieldValueSet>();
    //当前关联的左关联表依据关联的字段分类的行号集合,用于直接没有直接关联主表,而是关联其它左关联表得到的
    private Map<String, LazyRelatedTableField> relatedTableFieldName_lazyRelatedTableFieldRowIndex_map = new HashMap<String, LazyRelatedTableField>();
    //创建的任务
    private List<Callable<TableLoadResult>> childTableLoadTaskList = new ArrayList<Callable<TableLoadResult>>();
    private CallableTaskDependency next;

    public CallableTaskDependency(boolean isRelatedMainTable) {
        this.isRelatedMainTable = isRelatedMainTable;
    }

    public boolean isRelatedMainTable() {
        return isRelatedMainTable;
    }

    public boolean hasDependencyTable() {
        if (childTableAlias != null && childTableAlias.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean hasDependencyTask() {
        if (childTableLoadTaskList != null && childTableLoadTaskList.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean hasNext() {
        if (next != null) {
            return true;
        }
        return false;
    }

    public Set<String> getChildTableAlias() {
        return childTableAlias;
    }

    public void setChildTableAlias(Set<String> childTableAlias) {
        this.childTableAlias = childTableAlias;
    }

    public Map<String, List<SimpleChildTable>> getRelatedTableField_childTableList_map() {
        return relatedTableField_childTableList_map;
    }

    public void setRelatedTableField_childTableList_map(Map<String, List<SimpleChildTable>> relatedTableField_childTableList_map) {
        this.relatedTableField_childTableList_map = relatedTableField_childTableList_map;
    }

    public Map<String, RelatedFieldValueSet> getRelatedTableFieldName_relatedTableFieldValueSet_map() {
        return relatedTableFieldName_relatedTableFieldValueSet_map;
    }

    public void setRelatedTableFieldName_relatedTableFieldValueSet_map(Map<String, RelatedFieldValueSet> relatedTableFieldName_relatedTableFieldValueSet_map) {
        this.relatedTableFieldName_relatedTableFieldValueSet_map = relatedTableFieldName_relatedTableFieldValueSet_map;
    }

    public Map<String, LazyRelatedTableField> getRelatedTableFieldName_lazyRelatedTableFieldRowIndex_map() {
        return relatedTableFieldName_lazyRelatedTableFieldRowIndex_map;
    }

    public void setRelatedTableFieldName_lazyRelatedTableFieldRowIndex_map(Map<String, LazyRelatedTableField> relatedTableFieldName_lazyRelatedTableFieldRowIndex_map) {
        this.relatedTableFieldName_lazyRelatedTableFieldRowIndex_map = relatedTableFieldName_lazyRelatedTableFieldRowIndex_map;
    }

    public List<Callable<TableLoadResult>> getChildTableLoadTaskList() {
        return childTableLoadTaskList;
    }

    public void setChildTableLoadTaskList(List<Callable<TableLoadResult>> childTableLoadTaskList) {
        this.childTableLoadTaskList = childTableLoadTaskList;
    }

    public CallableTaskDependency getNext() {
        return next;
    }

    public void setNext(CallableTaskDependency next) {
        this.next = next;
    }

    public void destory(String resourceType){

    	if(childTableLoadTaskList != null) {
            for (Callable<TableLoadResult> task : childTableLoadTaskList) {
                if (task != null && task instanceof ITableLoaderTask) {
                    ((ITableLoaderTask) task).destory();
                } else {
                    childTableLoadLogger.warn(String.format("MainTableLoader resourceType=%s child table load task exception. task is null or not implements IChildTableLoaderTask",resourceType));
                }
            }
        }

        if(relatedTableField_childTableList_map != null){
            relatedTableField_childTableList_map.clear();
        }
        relatedTableField_childTableList_map = null;
        if(relatedTableFieldName_relatedTableFieldValueSet_map != null){
            relatedTableFieldName_relatedTableFieldValueSet_map.clear();
        }
        relatedTableFieldName_relatedTableFieldValueSet_map = null;
        if(relatedTableFieldName_lazyRelatedTableFieldRowIndex_map != null){
            for(Map.Entry<String,LazyRelatedTableField> entry : relatedTableFieldName_lazyRelatedTableFieldRowIndex_map.entrySet()){
                if(entry.getValue() != null) {
                    entry.getValue().destory();
                }
            }
            relatedTableFieldName_lazyRelatedTableFieldRowIndex_map.clear();
        }
        relatedTableFieldName_lazyRelatedTableFieldRowIndex_map = null;
        if(hasNext()){
    	    getNext().destory(resourceType);
        }
    }
}
