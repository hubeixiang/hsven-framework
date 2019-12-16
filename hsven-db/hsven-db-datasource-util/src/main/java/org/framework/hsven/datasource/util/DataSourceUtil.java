package org.framework.hsven.datasource.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author sven
 * @date 2019/12/2 14:17
 */
public class DataSourceUtil {
	private static Logger logger = LoggerFactory.getLogger(DataSourceUtil.class);

	public static <T> T binderOfProperties(Binder binder, Class<T> classzz) {
		ConfigurationProperties configurationProperties = AnnotationUtils.findAnnotation(classzz, ConfigurationProperties.class);
		String prefix = "";
		if (configurationProperties != null) {
			prefix = configurationProperties.prefix();
		}
		try {
			T object = binder.bind(prefix, Bindable.of(classzz)).get();
			return object;
		} catch (Exception e) {
			logger.warn(
					String.format(String.format("properties prefix=%s binder %s Exception:%s", prefix, classzz.getName(), e.getMessage())));
			return null;
		}
	}
}
