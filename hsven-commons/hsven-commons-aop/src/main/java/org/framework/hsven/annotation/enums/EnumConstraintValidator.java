package org.framework.hsven.annotation.enums;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Configurable(autowire = Autowire.BY_TYPE)
public class EnumConstraintValidator implements ConstraintValidator<EnumClassAnnotation, Object> {
    private String enumValues;
    private Class<?> group;
    private String groupName;
    private String message;

    @Override
    public void initialize(EnumClassAnnotation constraintAnnotation) {
        group = constraintAnnotation.group();
        groupName = constraintAnnotation.groupName();
        enumValues = constraintAnnotation.enumValues();
        message = constraintAnnotation.message();
        if (group == null) {
            throw new RuntimeException(String.format("group must configure!"));
        } else if (!group.isEnum()) {
            throw new RuntimeException(String.format("group must enum class!"));
        }
        if (StringUtils.isEmpty(groupName)) {
            throw new RuntimeException(String.format("groupName must configure!"));
        }
        EnumEntity enumEntity = null;
        if (StringUtils.isEmpty(enumValues)) {
            throw new RuntimeException(String.format("enumValues must configure!"));
        } else {
            enumEntity = JsonUtils.fromJson(enumValues, EnumEntity.class);
            if (enumEntity == null) {
                throw new RuntimeException(String.format("enumValues can't Deserialization EnumEntity.class!"));
            }
        }
        EnumAnnotationEntity enumAnnotationEntity = EnumClassAnnotationUtil.parserEnumAnnotationEntity(group, groupName, enumValues);
        EnumAnnotationCollection.getInstance().addEnumAnnotationEntity(enumAnnotationEntity);

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
