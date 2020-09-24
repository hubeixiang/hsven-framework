package org.framework.hsven.annotation.function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Configurable(autowire = Autowire.BY_TYPE)
public class FunctionConstraintValidator implements ConstraintValidator<FunctionClassAnnotation, Object> {
    private Class<?> group;
    private String groupName;
    private String beanName;
    private String beanDesc;
    private String format;
    private String message;

    @Override
    public void initialize(FunctionClassAnnotation constraintAnnotation) {
        group = constraintAnnotation.group();
        groupName = constraintAnnotation.groupName();
        beanName = constraintAnnotation.beanName();
        beanDesc = constraintAnnotation.beanDesc();
        format = constraintAnnotation.format();
        message = constraintAnnotation.message();
        if (group == null) {
            throw new RuntimeException(String.format("group must configure!"));
        } else if (!group.isInterface()) {
            throw new RuntimeException(String.format("group must interface class!"));
        }
        if (StringUtils.isEmpty(groupName)) {
            throw new RuntimeException(String.format("groupName must configure!"));
        }
        if (StringUtils.isEmpty(beanName)) {
            throw new RuntimeException(String.format("beanName must configure!"));
        }
        if (StringUtils.isEmpty(beanDesc)) {
            throw new RuntimeException(String.format("beanDesc must configure!"));
        }
        if (StringUtils.isEmpty(format)) {
            throw new RuntimeException(String.format("format must configure!"));
        }

        FunctionAnnotationEntity functionAnnotationEntity = FunctionClassAnnotationUtil.
                parserFunctionAnnotationEntity(group, groupName, beanName, beanDesc, format);
        FunctionAnnotationCollection.getInstance().addFunctionAnnotationEntity(functionAnnotationEntity);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return true;
    }
}
