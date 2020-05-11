package org.framework.hsven.dataloader.api;

import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.child.ChildTableLoadResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 关联的主表+子表数据加载,固定的复杂加载过程(先加载主表,然后加载子表,并将子表的数据关联到主表上)
 */
public interface IRelatedTableLoadListener {

    /**
     * 主表加载开始
     *
     * @param simpleMainTable
     */
    public void onMainTableLoadBegin(SimpleMainTable simpleMainTable);

    /**
     * 指定子表加载开始
     *
     * @param loadbyCache
     * @param simpleChildTable
     * @param select_relatedFieldValueSet
     */
    public void onChildTableLoadBegin(boolean loadbyCache, SimpleChildTable simpleChildTable, Set<String> select_relatedFieldValueSet);

    /**
     * 加载时新增/更新一行数据,只能在DataTable数据初始化时使用
     *
     * @param rowIndex       <0 新增,>=0 更新指定的行的数据
     * @param dbTableRowInfo 要加载到dataTable的数据
     * @param fieldSet       要加载的属性,未指定属性时,全部加载
     */
    public void loadRow(int rowIndex, DBTableRowInfo dbTableRowInfo, Collection<TableField> fieldSet);

    /**
     * 只做加载更新,将数据加载到指定的行上
     *
     * @param rowIndex   first 0,1,2...
     * @param fieldValue
     */
    public void updateRow(int rowIndex, Map<String, Object> fieldValue);

    /**
     * 整个关联表数据加载完成后的处理
     *
     * @param mainTableQueryLoaderResultDesc
     * @param childTableLoadResultList
     */
    public void onEnd(QueryLoaderResultDesc mainTableQueryLoaderResultDesc, List<ChildTableLoadResult> childTableLoadResultList);

    /**
     * 加载过程中可能会使用到的,查询已经加载完成后的指定行号,指定列名的值
     *
     * @param rowIndex  指定的行号
     * @param fieldName 指定的列名
     * @return
     */
    public Object getFieldValue(Integer rowIndex, String fieldName);

}
