package org.framework.hsven.annotation.enums;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.utils.JsonUtils;

import java.util.function.Function;
import java.util.function.Supplier;

public class EnumClassAnnotationUtil {
    /**
     * 将对应的枚举名称字符串,转换为对象
     *
     * @param supllier 枚举集合
     * @param function 枚举转换枚举名称
     * @param value    要判断的字符串
     * @param <T>
     * @return 如果不匹配则不能转换为对象
     */
    public static <T> T matchEnum(Supplier<T[]> supllier, Function<T, String> function, String value) {
        if (StringUtils.isNotEmpty(value)) {
            value = value.trim();
            T[] array = supllier.get();
            if (array != null && array.length > 0) {
                for (T xx : array) {
                    if (value.equalsIgnoreCase(function.apply(xx))) {
                        return xx;
                    }
                }
            }
        }
        return null;
    }

    public static EnumAnnotationEntity parserEnumAnnotationEntity(Class<?> group, String groupName, String enumValues) {
        EnumAnnotationEntity enumAnnotationEntity = new EnumAnnotationEntity();
        enumAnnotationEntity.setGroupClass(group);
        enumAnnotationEntity.setGroupName(getGroup(group));
        enumAnnotationEntity.setGroupName(groupName);
        EnumEntity enumEntity = JsonUtils.fromJson(enumValues, EnumEntity.class);
        if (enumEntity == null) {
            enumEntity = new EnumEntity();
        }
        enumAnnotationEntity.setEnumEntity(enumEntity);
        return enumAnnotationEntity;
    }

    public static boolean containsValue(EnumAnnotationEntity enumAnnotationEntity, String value) {
        EnumEntity enumEntity = enumAnnotationEntity.getEnumEntity();
        if (enumEntity != null && enumEntity.getData() != null) {
            for (EnumValue enumValue : enumEntity.getData()) {
                if (enumValue.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static <T> String getGroup(Class<T> groupClass) {
        return groupClass.getName();
    }
}
