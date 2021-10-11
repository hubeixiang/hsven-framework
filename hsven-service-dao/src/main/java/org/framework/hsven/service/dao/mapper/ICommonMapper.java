package org.framework.hsven.service.dao.mapper;

import java.util.List;

/**
 * 直接需要mybaits的mapper固定实现的接口类
 *
 * @param <CONDITION> 查询条件
 * @param <QPAGING>   分页条件
 * @param <E>         操作对象
 * @param <PK>        操作的数据主键
 */
public interface ICommonMapper<CONDITION, QPAGING, E, PK> {
    int insert(E record);

    int updateByPrimaryKey(E record);

    int deleteByPrimaryKey(PK pk);

    E selectByPrimaryKey(PK pk);

    List<E> selectAll();

    int selectConditionCount(CONDITION condition);

    List<E> selectConditionPage(CONDITION condition, QPAGING qPaging);

    List<E> selectCondition(CONDITION condition);

}
