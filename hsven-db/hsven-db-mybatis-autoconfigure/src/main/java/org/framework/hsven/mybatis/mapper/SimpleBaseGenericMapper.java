package org.framework.hsven.mybatis.mapper;


import org.apache.ibatis.annotations.Param;
import org.framework.hsven.mybatis.vo.LowerCaseResultMap;
import org.framework.hsven.mybatis.vo.PageRecord;
import org.framework.hsven.mybatis.vo.QueryEntity;
import org.framework.hsven.mybatis.vo.VarableBinds;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface SimpleBaseGenericMapper {

    int queryCountByCondition(@Param("queryEntity") QueryEntity queryEntity, @Param("varableBinds") VarableBinds varableBinds);

    List<LowerCaseResultMap<Object>> selectOraclePageByCondition(@Param("queryEntity") QueryEntity queryEntity, @Param("varableBinds") VarableBinds varableBinds, @Param("page") PageRecord page);

    List<LowerCaseResultMap> executeSql(String sql);

    int executeInsertSql(String sql);

    int executeUpdateSql(String sql);

    int executeDeleteSql(String sql);

    int selectCountSql(String sql);

    Object selectOneResult(String sql);

    List<LowerCaseResultMap> executeDynamicSql(Map<String, Object> params);

    int insertDynamicTable(Map<String, Object> params);

    int executeDynamicSelectInsert(Map<String, Object> params);

    int updateDynamicTable(Map<String, Object> params);

    int deleteDynamicTable(Map<String, Object> params);

    void callProcedure(String procedureName);

}
