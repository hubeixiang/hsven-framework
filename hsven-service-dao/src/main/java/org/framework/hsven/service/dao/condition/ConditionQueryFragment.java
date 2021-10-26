package org.framework.hsven.service.dao.condition;

/**
 * @description: 固定的查询条件中的使用sql片段的部分
 * @author: sven
 * @create: 2021-10-16
 **/

public class ConditionQueryFragment implements ConditionQuery {
    //过滤条件中的主动拼接的sql过滤片段(eg: age > 20 and age < 30)
    private String filterFragment;
    //过滤条件中的排序sql片段(eg: age desc,name)
    private String orderFragment;

    public static ConditionQueryFragment of() {
        return new ConditionQueryFragment();
    }

    @Override
    public void checkSQLinjectionException() {
        checkSQLinjectionException(getOrderFragment());
        checkSQLinjectionException(getFilterFragment());
    }

    public String getFilterFragment() {
        return filterFragment;
    }

    public void setFilterFragment(String filterFragment) {
        this.filterFragment = filterFragment;
    }

    public String getOrderFragment() {
        return orderFragment;
    }

    public void setOrderFragment(String orderFragment) {
        this.orderFragment = orderFragment;
    }
}
