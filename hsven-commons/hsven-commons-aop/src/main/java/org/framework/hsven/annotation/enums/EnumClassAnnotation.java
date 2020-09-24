package org.framework.hsven.annotation.enums;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 枚举类上的注解说明
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Constraint(validatedBy = {EnumConstraintValidator.class})
public @interface EnumClassAnnotation {

    /**
     * 枚举类中具体每个枚举值的说明
     * 可以直接是EnumAnnotationEntity.class 的json序列化对象
     *
     * @see EnumEntity
     */
    String enumValues() default "{}";

    /**
     * 当前枚举的分组,默认取值为此注解的枚举类的package+class名称
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

    String message() default "";
}
