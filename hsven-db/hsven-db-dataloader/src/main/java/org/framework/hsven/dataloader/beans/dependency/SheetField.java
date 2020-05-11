package org.framework.hsven.dataloader.beans.dependency;


import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class SheetField {
    private String fieldAlias;
    private boolean isMainSheet = false;
    //Map<SheetAlias,SheetFieldSource>
    private Map<String, SheetFieldSource> sheetFieldSourceMap = new HashMap<String, SheetFieldSource>();

    public String getFieldAlias() {
        return fieldAlias;
    }

    public void setFieldAlias(String fieldAlias) {
        this.fieldAlias = fieldAlias;
    }

    public boolean isMainSheet() {
        return isMainSheet;
    }

    public void setMainSheet(boolean mainSheet) {
        isMainSheet = mainSheet;
    }

    public SheetFieldSource getSheetFieldSource(String tableAlias) {
        return sheetFieldSourceMap.get(tableAlias);
    }

    public Map<String, SheetFieldSource> getSheetFieldSource() {
        return sheetFieldSourceMap;
    }

    public void addSheetFieldSource(SheetFieldSource sheetFieldSource) {
        if (sheetFieldSource != null && sheetFieldSource.isValid()) {
            sheetFieldSourceMap.put(sheetFieldSource.getSheetAlias(), sheetFieldSource);
        }
    }

    public boolean isValid() {
        if (StringUtils.isNotEmpty(fieldAlias) && sheetFieldSourceMap.size() > 0) {
            return true;
        }
        return false;
    }
}
