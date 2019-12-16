package org.framework.hsven.loggin.aop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.aop.AopUtils;
import org.framework.hsven.aop.context.MethodInterceptorHolder;
import org.framework.hsven.logging.LoggerDefine;

import java.lang.reflect.Method;

/**
 * 特殊的对swagger中访问注解访问日志的分析
 * @author sven
 */
public class SwaggerAccessLoggerParser implements AccessLoggerParser {
	@Override
	public boolean support(Class clazz, Method method) {

		Api api = AopUtils.findAnnotation(clazz, Api.class);
		ApiOperation operation = AopUtils.findAnnotation(method, ApiOperation.class);

		return api != null || operation != null;
	}

	@Override
	public LoggerDefine parse(MethodInterceptorHolder holder) {
		Api api = holder.findAnnotation(Api.class);
		ApiOperation operation = holder.findAnnotation(ApiOperation.class);
		String action = "";
		if (api != null) {
			action = action.concat(api.value());
		}
		if (null != operation) {
			action = StringUtils.isEmpty(action) ? operation.value() : action + "-" + operation.value();
		}
		return new LoggerDefine(action, "");
	}
}
