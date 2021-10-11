package org.framework.hsven.service.dao.api;

import java.util.Collection;
import java.util.List;

/**
 * 通用数据仓库操作
 *
 * @param <CONDITION> 查询条件
 * @param <PAGING>    分页条件
 * @param <E>         数据仓库存储对象
 * @param <PK>        对象对应的数据主键
 * @param <BPK>       对象对应的业务主键
 */
public interface ICommonRepository<CONDITION, PAGING, E, PK, BPK> {
    /**
     * 插入对象到数据库
     *
     * @param record 操作对象
     * @return 新插入到数据库后的对象值
     */
    E insert(E record);

    /**
     * 更新对象属性
     *
     * @param record 操作对象
     * @return 返回更新影响的数据量
     */
    int updateByPrimaryKey(E record);

    /**
     * 依据主键删除数据
     *
     * @param pk 数据主键
     * @return 删除的数量
     */
    int deleteByPrimaryKey(PK pk);

    /**
     * 批量删除数据
     *
     * @param pks 批量数据主键
     * @return
     */
    default int deleteByPrimaryKey(Collection<PK> pks) {
        if (pks == null || pks.size() == 0) {
            return 0;
        }
        int total = 0;
        for (PK pk : pks) {
            int x = deleteByPrimaryKey(pk);
            total = total + x;
        }
        return total;
    }

    /**
     * 查询指定数据主键的数据
     *
     * @param pk 数据主键
     * @return 返回对象
     */
    E selectByPrimaryKey(PK pk);

    /**
     * 查询指定业务主键的数据
     *
     * @param bpk 业务主键
     * @return 返回对象
     */
    E selectByBusinessKey(BPK bpk);

    /**
     * 检查是否已经存在数据主键
     *
     * @param pk 数据主键
     * @return true:存在,false:不存在
     */
    default boolean checkAlreadyExistsByPK(PK pk) {
        E entity = selectByPrimaryKey(pk);
        return entity != null;
    }

    /**
     * 检查指定的主键是否已经存在
     *
     * @param bpk 业务主键
     * @return true:存在,false:不存在
     */
    default boolean checkAlreadyExistsByBPK(BPK bpk) {
        E entity = selectByBusinessKey(bpk);
        return entity != null;
    }

    /**
     * 查询所有对象
     *
     * @return 数据列表
     */
    List<E> selectAll();

    /**
     * 按照条件查询满足条件的数据量
     *
     * @param condition 查询条件
     * @return 数据量
     */
    int selectRepositoryConditionCount(CONDITION condition);

    /**
     * 按照条件,分页查询满足条件的数据列表
     *
     * @param condition 查询条件
     * @param paging    分页条件
     * @return 分页数据列表
     */
    List<E> selectRepositoryConditionPage(CONDITION condition, PAGING paging);

    /**
     * 依据条件所有满足条件的数据
     *
     * @param condition 查询条件
     * @return 数据列表
     */
    List<E> selectRepositoryCondition(CONDITION condition);
}
