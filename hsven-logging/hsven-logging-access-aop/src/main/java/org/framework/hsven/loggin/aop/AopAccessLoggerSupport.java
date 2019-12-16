package org.framework.hsven.loggin.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.framework.hsven.logging.AccessLoggerInfo;
import org.framework.hsven.logging.events.AccessLoggerAfterEvent;
import org.framework.hsven.logging.events.AccessLoggerBeforeEvent;
import org.framework.hsven.utils.web.WebUtil;
import org.framework.hsven.utils.id.IDGenerator;
import org.framework.hsven.aop.context.MethodInterceptorHolder;
import org.framework.hsven.logging.AccessLoggerListener;
import org.framework.hsven.logging.LoggerDefine;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用AOP记录访问日志,并触发{@link AccessLoggerListener#onLogger(AccessLoggerInfo)}
 *
 * @author sven
 */
public class AopAccessLoggerSupport extends StaticMethodMatcherPointcutAdvisor {

	@Autowired(required = false)
	private final List<AccessLoggerListener> listeners = new ArrayList<>();

	@Autowired(required = false)
	private final List<AccessLoggerParser> loggerParsers = new ArrayList<>();

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public AopAccessLoggerSupport addListener(AccessLoggerListener loggerListener) {
		if (!listeners.contains(loggerListener)) {
			listeners.add(loggerListener);
		}
		return this;
	}

	public AopAccessLoggerSupport addParser(AccessLoggerParser parser) {
		if (!loggerParsers.contains(parser)) {
			loggerParsers.add(parser);
		}
		return this;
	}

	public AopAccessLoggerSupport() {
		setAdvice((MethodInterceptor) methodInvocation -> {
			MethodInterceptorHolder methodInterceptorHolder = MethodInterceptorHolder.create(methodInvocation);
			//创建日志信息
			AccessLoggerInfo info = createLogger(methodInterceptorHolder);
			//日志监听处理
			Object response;
			try {
				eventPublisher.publishEvent(new AccessLoggerBeforeEvent(info));
				listeners.forEach(listener -> listener.onLogBefore(info));
				response = methodInvocation.proceed();
				info.setResponse(response);
			} catch (Throwable e) {
				info.setException(e);
				throw e;
			} finally {
				info.setResponseTime(System.currentTimeMillis());
				//触发监听
				eventPublisher.publishEvent(new AccessLoggerAfterEvent(info));
				listeners.forEach(listener -> listener.onLogger(info));
			}
			return response;
		});
	}

	protected AccessLoggerInfo createLogger(MethodInterceptorHolder holder) {
		AccessLoggerInfo info = new AccessLoggerInfo();
		info.setId(IDGenerator.MD5.generate());

		info.setRequestTime(System.currentTimeMillis());
		LoggerDefine define = loggerParsers.stream()
				.filter(parser -> parser.support(ClassUtils.getUserClass(holder.getTarget()), holder.getMethod())).findAny()
				.map(parser -> parser.parse(holder)).orElse(null);

		if (define != null) {
			info.setAction(define.getAction());
			info.setDescribe(define.getDescribe());
		}
		info.setParameters(holder.getArgs());
		info.setTarget(holder.getTarget().getClass());
		info.setMethod(holder.getMethod());

		HttpServletRequest request = WebUtil.getHttpServletRequest();
		if (null != request) {
			info.setHttpHeaders(WebUtil.getHeaders(request));
			info.setIp(WebUtil.getIpAddr(request));
			info.setHttpMethod(request.getMethod());
			info.setUrl(request.getRequestURL().toString());
		}
		return info;

	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public boolean matches(Method method, Class<?> aClass) {
		return loggerParsers.stream().anyMatch(parser -> parser.support(aClass, method));
	}
}
