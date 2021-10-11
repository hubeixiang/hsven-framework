package org.framework.hsven.service.core.support.collection;


import org.framework.hsven.service.core.support.page.Paging;

public interface ResultPageVO {
    /**
     * 返回分页信息
     *
     * @return
     */
    public Paging getPaging();

    /**
     * 返回分页结果集
     *
     * @return
     */
    public ResultVO getResult();
}
