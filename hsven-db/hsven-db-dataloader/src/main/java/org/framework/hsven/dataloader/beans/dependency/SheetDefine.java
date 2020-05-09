package org.framework.hsven.dataloader.beans.dependency;


import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据加载内部的复杂类
 * 梳理了主子关联表之间的复杂的关联关系,支持子表多级关联
 */
public class SheetDefine {
    //主表的别名
    private String mainSheetAlias;
    //主表的表名或者查询SQL
    private String mainSheetContext;
    //所有表的字段
    //Map<fieldName,SheetField>
    private Map<String, SheetField> sheetFieldMap = new HashMap<>();
    //表左关联表的连接关系
    //Map<sheetAlias,LeftInfo>
    private Map<String, LeftInfo> leftInfoMap = new HashMap<>();

    public SheetDefine(String mainSheetAlias, String mainSheetContext) {
        this.mainSheetAlias = mainSheetAlias;
        this.mainSheetContext = mainSheetContext;
    }

    public String getMainSheetAlias() {
        return mainSheetAlias;
    }

    public String getMainSheetContext() {
        return mainSheetContext;
    }

    public Map<String, SheetField> getSheetFieldBySheetAlias(String sheetAlias) {
        Map<String, SheetField> tmpMap = new HashMap<String, SheetField>();
        for (Map.Entry<String, SheetField> entry : sheetFieldMap.entrySet()) {
            if (entry.getValue().getSheetFieldSource(sheetAlias) != null) {
                tmpMap.put(entry.getKey(), entry.getValue());
            }
        }
        return tmpMap;
    }

    public SheetField getSheetField(String fieldAlias) {
        return sheetFieldMap.get(fieldAlias);
    }

    public Map<String, SheetField> getSheetField() {
        return sheetFieldMap;
    }

    public void addSheetField(SheetField sheetField) {
        if (sheetField != null && sheetField.isValid()) {
            //包含有主表来源映射,设置为字段为主表
            if (sheetField.getSheetFieldSource(mainSheetAlias) != null) {
                sheetField.setMainSheet(true);
            } else {
                sheetField.setMainSheet(false);
            }
            sheetFieldMap.put(sheetField.getFieldAlias(), sheetField);
        }
    }

    public boolean haveConfig() {
        if (StringUtils.isNotEmpty(mainSheetAlias) && sheetFieldMap != null && sheetFieldMap.size() > 0) {
            return true;
        }
        return false;
    }

    public Map<String, LeftInfo> getLeftInfo() {
        return leftInfoMap;
    }

    public void addLeftInfo(LeftInfo leftInfo) {
        if (leftInfo != null && leftInfo.isValid()) {
            leftInfoMap.put(leftInfo.getSheetAlias(), leftInfo);
        }
    }

    public boolean haveLeftInfo() {
        if (leftInfoMap != null && leftInfoMap.size() > 0) {
            return true;
        }
        return false;
    }
}
