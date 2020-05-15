package org.framework.hsven.dataloader.beans.dependency;

import org.apache.commons.lang3.StringUtils;

public class SheetFieldSource {
    private String sheetTableAlias;
    private String sheetFieldName;
    private String secondSheetFieldName;

    public String getSheetTableAlias() {
        return sheetTableAlias;
    }

    public void setSheetTableAlias(String sheetTableAlias) {
        this.sheetTableAlias = sheetTableAlias;
    }

    public String getSheetFieldName() {
        return sheetFieldName;
    }

    public void setSheetFieldName(String sheetFieldName) {
        this.sheetFieldName = sheetFieldName;
    }

    public String getSecondSheetFieldName() {
        return secondSheetFieldName;
    }

    public void setSecondSheetFieldName(String secondSheetFieldName) {
        this.secondSheetFieldName = secondSheetFieldName;
    }

    public boolean isValid() {
        if (StringUtils.isNotEmpty(sheetTableAlias) && StringUtils.isNotEmpty(sheetFieldName)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("SheetFieldSource [sheetTableAlias=%s,sheetFieldName=%s,secondSheetFieldName=%s]", sheetTableAlias, sheetFieldName, secondSheetFieldName);
    }
}
