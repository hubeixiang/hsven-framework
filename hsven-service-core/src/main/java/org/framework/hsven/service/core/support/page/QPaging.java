package org.framework.hsven.service.core.support.page;

/**
 * dao侧,在数据库中使用时的对象
 */
public class QPaging {
    /**
     * 开始行,包括
     */
    private int startIndex;
    /**
     * 结束行,不包括
     */
    private int endIndex;
    /**
     * 每页大小
     */
    private int pageSize;

    public static QPaging of(int startIndex, int endIndex) {
        QPaging qPaging = new QPaging();
        qPaging.setStartIndex(startIndex);
        qPaging.setEndIndex(endIndex);
        return qPaging;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
