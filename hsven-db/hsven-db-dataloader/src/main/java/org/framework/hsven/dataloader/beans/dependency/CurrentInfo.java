package org.framework.hsven.dataloader.beans.dependency;

import java.util.HashMap;
import java.util.Map;

public class CurrentInfo {
    private EnumTableType tableType;
    //当前级别的需要生成的所有字段
    //Map<fieldName,SheetField>
    private Map<String, SheetField> sheetFieldMap = new HashMap<>();
    //当前级别对应的左关联表信息,如果没有左关联表表明是第一层的主表信息
    //Map<sheetAlias,LeftInfo>
    private Map<String, LeftInfo> leftInfoMap = new HashMap<>();

    public CurrentInfo(EnumTableType tableType) {
        this.tableType = tableType;
    }

    public EnumTableType getTableType() {
        return tableType;
    }

    public Map<String, SheetField> getSheetFieldMap() {
        return sheetFieldMap;
    }

    public void setSheetFieldMap(Map<String, SheetField> sheetFieldMap) {
        this.sheetFieldMap = sheetFieldMap;
    }

    public Map<String, LeftInfo> getLeftInfoMap() {
        return leftInfoMap;
    }

    public void setLeftInfoMap(Map<String, LeftInfo> leftInfoMap) {
        this.leftInfoMap = leftInfoMap;
    }
}
