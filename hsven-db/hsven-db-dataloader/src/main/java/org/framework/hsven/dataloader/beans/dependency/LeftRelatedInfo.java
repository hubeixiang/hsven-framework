package org.framework.hsven.dataloader.beans.dependency;

import org.apache.commons.lang3.StringUtils;

public class LeftRelatedInfo {
    private String childSheetField;
    private String mainSheetField;

    public String getChildSheetField() {
        return childSheetField;
    }

    public void setChildSheetField(String childSheetField) {
        this.childSheetField = childSheetField;
    }

    public String getMainSheetField() {
        return mainSheetField;
    }

    public void setMainSheetField(String mainSheetField) {
        this.mainSheetField = mainSheetField;
    }

    public boolean isValid() {
        if (StringUtils.isNotEmpty(childSheetField) && StringUtils.isNotEmpty(mainSheetField)) {
            return true;
        }
        return false;
    }
}
