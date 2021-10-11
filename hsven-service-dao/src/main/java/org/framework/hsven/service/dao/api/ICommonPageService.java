package org.framework.hsven.service.dao.api;

import org.framework.hsven.file.location.FileLocation;
import org.framework.hsven.service.core.support.collection.PageResultVO;
import org.framework.hsven.service.core.support.collection.ResultVOEntity;

/**
 * @description: 通用分页查询相关服务
 * @author: sven
 * @create: 2021-03-04
 **/

public interface ICommonPageService<CONDITION, CONDITION_EXPORT, PAGING> {

    public ResultVOEntity list(CONDITION condition);

    public PageResultVO listPage(PAGING paging, CONDITION condition);

    public FileLocation export2FileLocation(CONDITION_EXPORT conditionExport);
}
