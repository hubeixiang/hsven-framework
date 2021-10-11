package org.framework.hsven.service.dao.condition;

import org.framework.hsven.service.dao.utils.NonSQLinjectionUtil;

import java.util.Objects;

public interface ConditionQuery {
    /**
     * 检查参数是否合法,不包含sql注入检查
     */
    default void checkParam() {
    }

    /**
     * 检查参数是否有sql注入,并抛出异常
     */
    default void checkSQLinjectionException() {
    }

    default void checkSQLinjectionExceptions(String... fragments) {
        Objects.requireNonNull(fragments, " fragments not null");
        for (int i = 1; i < fragments.length; i++) {
            checkSQLinjectionException(fragments[i]);
        }
    }

    default void checkSQLinjectionException(String fragment) {
        if (checkSQLinjection(fragment)) {
            String msg = NonSQLinjectionUtil.getMessage("ConditionQuery.Illegal_injection");
            throw new RuntimeException(msg);
        }
    }

    /**
     * 检查是否有sql注入危险
     * 主要检查在mybaits中使用${}引用的变量
     *
     * @param fragment
     * @return true:有sql注入风险
     */
    default boolean checkSQLinjection(String fragment) {
        return NonSQLinjectionUtil.checkSQLinjection(fragment);
    }
}
