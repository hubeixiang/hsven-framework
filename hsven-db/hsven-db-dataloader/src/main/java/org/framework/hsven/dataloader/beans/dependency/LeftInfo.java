package org.framework.hsven.dataloader.beans.dependency;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LeftInfo {
    //左关联表的别名
    private String sheetAlias;
    //左关联表的名称或者查询SQL
    private String sheetAliasContext;
    private List<LeftRelatedInfo> leftRelatedInfoList = new ArrayList<LeftRelatedInfo>();

    public LeftInfo(String sheetAlias, String sheetAliasContext) {
        this.sheetAlias = sheetAlias;
        this.sheetAliasContext = sheetAliasContext;
    }

    public String getSheetAlias() {
        return sheetAlias;
    }

    public String getSheetAliasContext() {
        return sheetAliasContext;
    }

    public List<LeftRelatedInfo> getLeftRelatedInfoList() {
        return leftRelatedInfoList;
    }

    public void addLeftRelatedInfoList(LeftRelatedInfo leftRelatedInfo) {
        if (leftRelatedInfo != null && leftRelatedInfo.isValid()) {
            leftRelatedInfoList.add(leftRelatedInfo);
        }
    }

    public boolean isValid() {
        if (StringUtils.isNotEmpty(sheetAlias) && leftRelatedInfoList.size() > 0) {
            return true;
        }
        return false;
    }
}
