package org.framework.hsven.mybatis.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

/**
 * 分页的属性
 */
@JSONType(ignores = {"rowNumStart", "rowNumEnd"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class PageRecord {

    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private int defaultPageSize = DEFAULT_PAGE_SIZE;

    @JSONField(name = "pi")
    @JsonProperty("pi")
    private int pageIndex = DEFAULT_PAGE_INDEX;

    @JSONField(name = "ps")
    @JsonProperty("ps")
    private int pageSize = DEFAULT_PAGE_SIZE;

    private int totalItems = 0;

    public static PageRecord calculate(Integer pageIndex, Integer pageSize) {
        PageRecord pageRecord = new PageRecord();
        pageRecord.setPageIndex(pageIndex);
        pageRecord.setPageSize(pageSize);
        return pageRecord;
    }

    public static PageRecord calculate(Integer pageIndex, Integer pageSize, int defaultPageSize) {
        Assert.isTrue(defaultPageSize >= 5, "Page size must large than 5.");
        PageRecord pageRecord = new PageRecord();
        pageRecord.defaultPageSize = defaultPageSize;
        pageRecord.setPageIndex(pageIndex);
        pageRecord.setPageSize(pageSize);
        return pageRecord;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        if (pageIndex == null || pageIndex <= DEFAULT_PAGE_INDEX) {
            pageIndex = DEFAULT_PAGE_INDEX;
        }
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = defaultPageSize;
        }
        this.pageSize = pageSize;
    }

    @JsonIgnore
    public int getRowNumStart() {
        return (pageIndex - 1) * pageSize + 1;
    }

    @JsonIgnore
    public int getRowNumEnd() {
        return pageIndex * pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
