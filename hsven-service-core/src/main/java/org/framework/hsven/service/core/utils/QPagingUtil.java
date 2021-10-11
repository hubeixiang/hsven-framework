package org.framework.hsven.service.core.utils;

import org.framework.hsven.service.core.support.page.Paging;
import org.framework.hsven.service.core.support.page.QPaging;

public class QPagingUtil {
    public static QPaging toQPaging(Paging paging, int totalSize) {
        QPaging qPaging = toQPaging(paging);
        if (qPaging != null && totalSize < qPaging.getEndIndex()) {
            qPaging.setEndIndex(totalSize);
        }
        if (qPaging != null) {
            qPaging.setPageSize(qPaging.getEndIndex() - qPaging.getStartIndex());
        }
        if (qPaging.getPageSize() < 0) {
            qPaging.setPageSize(0);
            qPaging.setEndIndex(qPaging.getStartIndex());
        }
        return qPaging;
    }

    public static QPaging toQPaging(Paging paging) {
        if (paging == null) {
            return null;
        }
        int startIndex = paging.getOffset();
        int endIndex = startIndex + paging.getPageSize();
        return QPaging.of(startIndex, endIndex);
    }
}
