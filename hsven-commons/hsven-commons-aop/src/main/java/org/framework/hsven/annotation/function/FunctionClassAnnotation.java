package org.framework.hsven.annotation.function;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Constraint(validatedBy = {FunctionConstraintValidator.class})
public @interface FunctionClassAnnotation {

    /**
     * 当前实现类的接口分组,默认取值为此注解所在类的实现接口的package+class
     * 接口类
     *
     * @return
     */
    Class<?> group();

    /**
     * 填写的用于展现说明的枚举类用法的
     *
     * @return
     */
    String groupName();


    /**
     * 注解所在类的Service在spring application 中的bean name
     *
     * @return
     */
    String beanName();

    /**
     * bean 的功能描述
     *
     * @return
     */
    String beanDesc();

    /**
     * bean需要的参数描述,或者格式描述
     *
     * @return
     */
    String format();

    String message() default "";
}
