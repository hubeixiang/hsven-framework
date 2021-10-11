package org.framework.hsven.service.core.support.collection;

import org.framework.hsven.service.core.support.page.PageResponse;
import org.framework.hsven.service.core.support.page.Paging;

/**
 * 分页返回的结果
 */
public class PageResultVO implements ResultPageVO {
    private PageResponse paging;
    private ResultVO result;

    public static PageResultVO of() {
        return new PageResultVO();
    }

    public static PageResultVO of(PageResponse paging, ResultVO resultVO) {
        PageResultVO pageResultVO = new PageResultVO();
        pageResultVO.setPaging(paging);
        pageResultVO.setResult(resultVO);
        return pageResultVO;
    }

    @Override
    public Paging getPaging() {
        return paging;
    }

    public void setPaging(PageResponse paging) {
        this.paging = paging;
    }

    @Override
    public ResultVO getResult() {
        return result;
    }

    public void setResult(ResultVO result) {
        this.result = result;
    }
}
