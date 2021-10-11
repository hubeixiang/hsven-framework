package org.framework.hsven.service.dao.api.base;

import org.framework.hsven.file.location.FileLocation;
import org.framework.hsven.service.core.exception.NotImplementedException;
import org.framework.hsven.service.core.support.collection.PageResultVO;
import org.framework.hsven.service.core.support.collection.ResultVOEntity;
import org.framework.hsven.service.core.support.page.PageResponse;
import org.framework.hsven.service.core.support.page.Paging;
import org.framework.hsven.service.core.support.page.QPaging;
import org.framework.hsven.service.core.support.tuple.SimpleResult;
import org.framework.hsven.service.core.utils.QPagingUtil;
import org.framework.hsven.service.dao.api.ICommonData2File;
import org.framework.hsven.service.dao.api.ICommonPageService;
import org.framework.hsven.service.dao.api.ICommonRepository;
import org.framework.hsven.service.dao.condition.ConditionExport;
import org.framework.hsven.service.dao.condition.ConditionQuery;
import org.framework.hsven.service.dao.mapper.ICommonMapper;
import org.framework.hsven.service.dao.utils.BeanPropertiesUtils;

import java.util.List;

/**
 * @description: 通用对象操作服务
 * @author: sven
 * @create: 2021-03-04
 **/

public abstract class CommonServiceImpl<CONDITION extends ConditionQuery, CONDITION_EXPORT extends ConditionExport<CONDITION>, E, PK, BPK> implements ICommonRepository<CONDITION, QPaging, E, PK, BPK>, ICommonPageService<CONDITION, CONDITION_EXPORT, Paging> {

    public abstract ICommonMapper<CONDITION, QPaging, E, PK> getDaoCommonMapper();

    public abstract ICommonData2File getICommonData2File();

    public PK generatePrimaryKey(E record) {
        throw NotImplementedException.of("没有实现主键生成方法");
    }

    @Override
    public E insert(E record) {
        getDaoCommonMapper().insert(record);
        return record;
    }

    @Override
    public int updateByPrimaryKey(E record) {
        return getDaoCommonMapper().updateByPrimaryKey(record);
    }

    @Override
    public int deleteByPrimaryKey(PK pk) {
        return getDaoCommonMapper().deleteByPrimaryKey(pk);
    }

    @Override
    public E selectByPrimaryKey(PK pk) {
        return getDaoCommonMapper().selectByPrimaryKey(pk);
    }

    @Override
    public E selectByBusinessKey(BPK bpk) {
        throw NotImplementedException.of("没有实现依据业务主键查询对象方法");
    }

    @Override
    public List<E> selectAll() {
        return getDaoCommonMapper().selectAll();
    }

    @Override
    public int selectRepositoryConditionCount(CONDITION condition) {
        return getDaoCommonMapper().selectConditionCount(condition);
    }

    @Override
    public List<E> selectRepositoryConditionPage(CONDITION condition, QPaging qPaging) {
        return getDaoCommonMapper().selectConditionPage(condition, qPaging);
    }

    @Override
    public List<E> selectRepositoryCondition(CONDITION condition) {
        return getDaoCommonMapper().selectCondition(condition);
    }

    @Override
    public ResultVOEntity list(CONDITION condition) {
        List data = selectRepositoryCondition(condition);
        List<String> columns = BeanPropertiesUtils.parserResultColumns(data);
        return ResultVOEntity.of(columns, data);
    }

    @Override
    public PageResultVO listPage(Paging paging, CONDITION condition) {
        int totalSize = selectRepositoryConditionCount(condition);
        PageResponse pageResponse = PageResponse.of(paging.getPageIndex(), paging.getPageSize(), totalSize);
        ResultVOEntity resultVO = null;
        if (totalSize == 0) {
            resultVO = ResultVOEntity.of();
        } else {
            QPaging qPaging = QPagingUtil.toQPaging(paging, totalSize);
            List data = selectRepositoryConditionPage(condition, qPaging);
            List<String> columns = BeanPropertiesUtils.parserResultColumns(data);
            resultVO = ResultVOEntity.of(columns, data);
        }

        return PageResultVO.of(pageResponse, resultVO);
    }

    @Override
    public FileLocation export2FileLocation(CONDITION_EXPORT conditionExport) {
        return getICommonData2File().export2FileLocation(conditionExport.getExportCondition(),
                () -> selectRepositoryCondition(conditionExport.getCondition()));
    }

    /**
     * 判断当前资源是否还有其它资源依赖当前资源
     *
     * @param pk 主键
     * @return 说明如下:
     * true:没有依赖,msg:null;
     * false:有依赖,msg:为依赖的提示
     */
    public SimpleResult dependencyObject(PK pk) {
        throw NotImplementedException.of("没有实现判断当前资源是否有被其它资源依赖方法");
    }

    /**
     * 是否有任意一个对象满足条件/或者是否所有对象满足条件
     *
     * @param pks   主键列表
     * @param isAny true:短路操作,只要一个不满足就返回;false:非短路操作,必须列表所有对象都判断一遍
     * @return 判断结果
     */
    public SimpleResult dependencyObject(List<PK> pks, boolean isAny) {
        if (isAny) {
            for (PK pk : pks) {
                SimpleResult simpleResult = dependencyObject(pk);
                if (!simpleResult.isOk()) {
                    return simpleResult;
                }
            }
            return SimpleResult.ok();
        } else {
            SimpleResult simpleResult = SimpleResult.ok();
            for (PK pk : pks) {
                SimpleResult tmp = dependencyObject(pk);
                if (!tmp.isOk()) {
                    tmp.setOk(false);
                    SimpleResult.appendMsg(simpleResult, tmp.getMsg());
                }
            }
            return SimpleResult.ok();
        }
    }

}
