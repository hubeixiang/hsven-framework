package org.framework.hsven.i18n.autoconfigure;

import org.framework.hsven.i18n.I18nMessageUtil;
import org.framework.hsven.i18n.message.I18nMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Configuration(proxyBeanMethods = false)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties
public class I18nMessageSourceAutoConfiguration {
    private static Logger logger = LoggerFactory.getLogger(I18nMessageSourceAutoConfiguration.class);

    /**
     * @Bean
     * @ConditionalOnProperty(prefix = "hsven.locale", name = {"language", "country"})
     * @ConditionalOnMissingBean(name = "messageLocaleProperties")
     * public MessageLocaleProperties messageLocaleProperties() {
     * return new MessageLocaleProperties();
     * }
     **/

    @Bean
    public Locale defaultLocale(MessageLocaleProperties messageLocaleProperties) {
        if (messageLocaleProperties == null || StringUtils.isEmpty(messageLocaleProperties.getLanguage())) {
            return Locale.getDefault();
        } else {
            return new Locale(messageLocaleProperties.getLanguage(), messageLocaleProperties.getCountry() == null ? "" : messageLocaleProperties.getCountry());
        }
    }

    @Bean
    public I18nMessageSource i18nMessageSource(MessageSource messageSource, Locale defaultLocale) {
        if (messageSource == null) {
            logger.error("Please init i18n MessageSource bean");
        }
        I18nMessageSource i18nMessageSource = new I18nMessageSource();
        i18nMessageSource.setMessageSource(messageSource);
        i18nMessageSource.setDefaultLocale(defaultLocale);

        I18nMessageUtil.getInstance().initI18nMessageSource(i18nMessageSource);
        return i18nMessageSource;
    }


}
