package org.framework.hsven.dataloader.related.child;

import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedFields;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedValues;
import org.framework.hsven.dataloader.beans.loader.DefineRelatedValuesUtil;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;

import java.util.HashMap;
import java.util.Map;

/**
 * 预先加载的字表的数据缓存实体
 */
public class ChildTableConfigCacheEntity {
    private final String defineType;
    private final TableLoadDefine tableLoadDefine;
    private final SimpleChildTable simpleChildTable;
    private final DefineRelatedFields defineRelatedFields;
    //配置中配置了可以缓存的时候,缓存加载出来的数据
    //Map<DefineRelatedValues.getRelatedFieldsKeyValue(),DBTableRowInfo>
    private Map<String, DBTableRowInfo> data = new HashMap<String, DBTableRowInfo>();

    public ChildTableConfigCacheEntity(String defineType, TableLoadDefine tableLoadDefine, SimpleChildTable simpleChildTable) {
        this.defineType = defineType;
        this.tableLoadDefine = tableLoadDefine;
        this.simpleChildTable = simpleChildTable;
        this.defineRelatedFields = simpleChildTable.getDefineRelatedFields();
    }

    public String getIdentify() {
        return String
                .format("resourceType=%s,SimpleChildTable=%s", defineType, simpleChildTable.getIdentify());
    }

    public String getDefineType() {
        return defineType;
    }

    public TableLoadDefine getTableLoadDefine() {
        return tableLoadDefine;
    }

    public SimpleChildTable getSimpleChildTable() {
        return simpleChildTable;
    }

    public DefineRelatedFields getDefineRelatedFields() {
        return defineRelatedFields;
    }

    public DBTableRowInfo findRelatedDataBaseTableRow(String relatedValue) {
        return data.get(relatedValue);
    }

    public void cacheDataBaseTableRow(DBTableRowInfo dbTableRowInfo) {
        if (!defineRelatedFields.hasTableRelatedFieldDefine()) {
            return;
        }

        DefineRelatedValues defineRelatedValues = DefineRelatedValuesUtil.createTableDefineRelatedValues(defineRelatedFields, dbTableRowInfo);
        if (defineRelatedValues != null) {
            data.put(defineRelatedValues.getRelatedFieldsKeyValue(), dbTableRowInfo);
        }
    }


    public void destory() {
        if (data != null) {
            data.clear();
        }
        data = null;
    }
}
