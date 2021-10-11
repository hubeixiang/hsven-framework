package org.framework.hsven.service.dao.utils;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.service.dao.bundle.HsvenDaoMessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class NonSQLinjectionUtil {
    protected static MessageSourceAccessor messages = HsvenDaoMessageSource.getAccessor();

    /**
     * @param condition 检查的条件
     * @return true:有sql注入风险
     */
    public static boolean checkSQLinjection(String condition) {
        if (StringUtils.isNotEmpty(condition)) {
            int index = condition.toLowerCase().indexOf("select ");
            if (index != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 有参数的拼接为提示语的方法
     *
     * @param code
     * @param args
     * @return
     */
    public static String getMessage(String code, Object[] args) {
        return messages.getMessage(code, args);
    }

    /**
     * 没有参数,并且提供默认提示语的方法
     *
     * @param code
     * @param defaultMessage
     * @return
     */
    public static String getMessage(String code, String defaultMessage) {
        return messages.getMessage(code, defaultMessage);
    }

    /**
     * 直接获取对应提示语,没有则异常
     *
     * @param code
     * @return
     */
    public static String getMessage(String code) {
        return messages.getMessage(code);
    }
}
