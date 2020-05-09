package org.framework.hsven.dataloader.beans.dependency;

import org.apache.commons.lang3.StringUtils;

public class SheetFieldSource {
    private String sheetAlias;
    private String sheetFieldName;

    public String getSheetAlias() {
        return sheetAlias;
    }

    public void setSheetAlias(String sheetAlias) {
        this.sheetAlias = sheetAlias;
    }

    public String getSheetFieldName() {
        return sheetFieldName;
    }

    public void setSheetFieldName(String sheetFieldName) {
        this.sheetFieldName = sheetFieldName;
    }

    public boolean isValid() {
        if (StringUtils.isNotEmpty(sheetAlias) && StringUtils.isNotEmpty(sheetFieldName)) {
            return true;
        }
        return false;
    }
}
