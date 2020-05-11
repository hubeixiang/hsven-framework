package org.framework.hsven.dataloader.tips;

import org.framework.hsven.i18n.I18nMessageUtil;

import java.util.Locale;

public class TipsMessageUsed {
    public static String getMessage(String key, Object... params) {
        return I18nMessageUtil.getInstance().getMessage(key, params);
    }

    public static String getMessage(String key, Locale locale, Object... params) {
        return I18nMessageUtil.getInstance().getMessage(key, params, locale);
    }
}
