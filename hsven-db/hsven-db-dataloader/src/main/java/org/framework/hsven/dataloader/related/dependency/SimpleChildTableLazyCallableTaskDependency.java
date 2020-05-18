package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.dependency.EnumTableType;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;
import org.framework.hsven.dataloader.related.TableLoadResult;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SimpleChildTableLazyCallableTaskDependency extends AbstractCallableRelatedTaskDependency<SimpleLazyChildTableGroup, TableLoadResult> {
    //按照关联关系的关联主表字段对当前操作表进行分组
    //Map<TableDefineRelatedFields.getRelatedMainFieldsKey(),TABLE>
    protected Map<String, SimpleLazyChildTableGroup> relatedTableFields_lazyTableDefineInfo_map = new HashMap<>();


    public SimpleChildTableLazyCallableTaskDependency() {
        super(EnumTableType.Lazy_subtable);
    }

    public Iterator<Map.Entry<String, SimpleLazyChildTableGroup>> entryTABLEIterator() {
        return relatedTableFields_lazyTableDefineInfo_map.entrySet().iterator();
    }

    @Override
    public void addCurrentTable(SimpleLazyChildTableGroup simpleLazyChildTableGroup) {
        relatedTableFields_lazyTableDefineInfo_map.put(simpleLazyChildTableGroup.getRelatedFieldsKeys(), simpleLazyChildTableGroup);
    }

    @Override
    public SimpleLazyChildTableGroup getCurrentTable(String key) {
        return relatedTableFields_lazyTableDefineInfo_map == null ? null : relatedTableFields_lazyTableDefineInfo_map.get(key);
    }

    @Override
    public void destory(String defineType) {
        if (relatedTableFields_lazyTableDefineInfo_map != null) {
            for (Map.Entry<String, SimpleLazyChildTableGroup> entry : relatedTableFields_lazyTableDefineInfo_map.entrySet()) {
                entry.getValue().destory();
            }
            relatedTableFields_lazyTableDefineInfo_map.clear();
        }
        relatedTableFields_lazyTableDefineInfo_map = null;
        super.destory(defineType);
    }
}
