package org.framework.hsven.datasource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author sven
 */
@Configuration
public class UtilsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SpringDataSourceContextUtil.class)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SpringDataSourceContextUtil contextUtil() {
		return new SpringDataSourceContextUtil();
	}
}
