package org.framework.hsven.dataloader.related.dependency;

import org.framework.hsven.dataloader.beans.dependency.EnumTableType;
import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractChildCallableRelatedTaskDependency extends AbstractCallableRelatedTaskDependency<SimpleChildTableGroup, QueryLoaderResultDesc> {
    //按照关联关系的关联主表字段对当前操作表进行分组
    //Map<TableDefineRelatedFields.getRelatedMainFieldsKey(),TABLE>
    protected Map<String, SimpleChildTableGroup> relatedTableFields_tableDefineInfo_map = new HashMap<>();

    public AbstractChildCallableRelatedTaskDependency(EnumTableType tableType) {
        super(tableType);
    }

    @Override
    public void addCurrentTable(SimpleChildTableGroup simpleChildTableGroup) {
        relatedTableFields_tableDefineInfo_map.put(simpleChildTableGroup.getRelatedFieldsKeys(), simpleChildTableGroup);
    }

    @Override
    public SimpleChildTableGroup getCurrentTable(String key) {
        return relatedTableFields_tableDefineInfo_map == null ? null : relatedTableFields_tableDefineInfo_map.get(key);
    }

    @Override
    public void destory(String defineType) {
        if (relatedTableFields_tableDefineInfo_map != null) {
            for (Map.Entry<String, SimpleChildTableGroup> entry : relatedTableFields_tableDefineInfo_map.entrySet()) {
                entry.getValue().destory();
            }
            relatedTableFields_tableDefineInfo_map.clear();
        }
        relatedTableFields_tableDefineInfo_map = null;
        super.destory(defineType);
    }
}
