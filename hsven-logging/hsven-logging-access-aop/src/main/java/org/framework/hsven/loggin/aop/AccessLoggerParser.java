package org.framework.hsven.loggin.aop;


import org.framework.hsven.aop.context.MethodInterceptorHolder;
import org.framework.hsven.logging.LoggerDefine;

import java.lang.reflect.Method;

public interface AccessLoggerParser {
    boolean support(Class clazz, Method method);

    LoggerDefine parse(MethodInterceptorHolder holder);
}
