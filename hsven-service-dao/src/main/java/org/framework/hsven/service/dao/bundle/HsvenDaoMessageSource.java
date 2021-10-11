package org.framework.hsven.service.dao.bundle;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @description:
 * @author: sven
 * @create: 2021-10-11
 **/

public class HsvenDaoMessageSource extends ResourceBundleMessageSource {

    public HsvenDaoMessageSource() {
        setBasename("org.framework.hsven.service.dao.messages");
    }

    // ~ Methods
    // ========================================================================================================

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new HsvenDaoMessageSource());
    }
}
