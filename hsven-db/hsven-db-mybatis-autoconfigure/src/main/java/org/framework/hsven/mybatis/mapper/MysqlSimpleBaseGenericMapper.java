package org.framework.hsven.mybatis.mapper;


import org.apache.ibatis.annotations.Param;
import org.framework.hsven.mybatis.vo.QueryEntity;
import org.framework.hsven.mybatis.vo.VarableBinds;

/**
 *
 */
public interface MysqlSimpleBaseGenericMapper extends SimpleBaseGenericMapper {

    int queryCountByCondition(@Param("queryEntity") QueryEntity queryEntity, @Param("varableBinds") VarableBinds varableBinds);
}
