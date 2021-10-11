package org.framework.hsven.service.core.support.page;

/**
 * 页面的传参请求
 */
public class PageRecord implements Paging {
    /**
     * 分页下表,从0开始
     */
    private int index = 0;
    /**
     * 分页,每页的大小, 最小值为1,默认为15
     */
    private int size = 15;

    @Override
    public int getPageIndex() {
        return index;
    }

    public void setPageIndex(int index) {
        this.index = index;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    public void setPageSize(int size) {
        this.size = size;
    }
}
