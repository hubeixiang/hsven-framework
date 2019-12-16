package org.framework.hsven.loggin.aop;


import org.framework.hsven.logging.AccessLogger;
import org.framework.hsven.aop.AopUtils;
import org.framework.hsven.aop.context.MethodInterceptorHolder;
import org.framework.hsven.logging.LoggerDefine;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author sven
 */
public class DefaultAccessLoggerParser implements AccessLoggerParser {
    @Override
    public boolean support(Class clazz, Method method) {
        AccessLogger ann = AopUtils.findAnnotation(method, AccessLogger.class);
        //注解了并且未取消
        return null != ann && !ann.ignore();
    }

    @Override
    public LoggerDefine parse(MethodInterceptorHolder holder) {
        AccessLogger methodAnn = holder.findMethodAnnotation(AccessLogger.class);
        AccessLogger classAnn = holder.findClassAnnotation(AccessLogger.class);
        String action = Stream.of(classAnn, methodAnn)
                .filter(Objects::nonNull)
                .map(AccessLogger::value)
                .reduce((c, m) -> c.concat("-").concat(m))
                .orElse("");
        String describe = Stream.of(classAnn, methodAnn)
                .filter(Objects::nonNull)
                .map(AccessLogger::describe)
                .flatMap(Stream::of)
                .reduce((c, s) -> c.concat("\n").concat(s))
                .orElse("");
        return new LoggerDefine(action,describe);
    }
}
