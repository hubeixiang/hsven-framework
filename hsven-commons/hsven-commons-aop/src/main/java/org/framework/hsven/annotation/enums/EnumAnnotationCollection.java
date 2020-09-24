package org.framework.hsven.annotation.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举注解枚举类的所有描述信息集合
 * 单例
 *
 * @see EnumClassAnnotation
 */
public class EnumAnnotationCollection {
    private static EnumAnnotationCollection instance;
    private static Map<String, EnumAnnotationEntity> enumAnnotationEntityMap = new HashMap<>();

    private EnumAnnotationCollection() {
    }

    public static EnumAnnotationCollection getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new EnumAnnotationCollection();
        }
    }

    public <T> boolean containsKey(Class<T> enumClass) {
        return containsKey(getGroup(enumClass));
    }

    public <T> boolean containsKey(Class<T> enumClass, String value) {
        if (containsKey(getGroup(enumClass))) {
            EnumAnnotationEntity enumAnnotationEntity = getEnumAnnotationEntity(enumClass);
            return EnumClassAnnotationUtil.containsValue(enumAnnotationEntity, value);
        }
        return false;
    }

    public boolean containsKey(String group) {
        return enumAnnotationEntityMap.containsKey(group);
    }

    public <T> boolean containsKey(String group, String value) {
        if (containsKey(group)) {
            EnumAnnotationEntity enumAnnotationEntity = getEnumAnnotationEntity(group);
            return EnumClassAnnotationUtil.containsValue(enumAnnotationEntity, value);
        }
        return false;
    }

    public <T> EnumAnnotationEntity getEnumAnnotationEntity(Class<T> enumClass) {
        return getEnumAnnotationEntity(getGroup(enumClass));
    }

    public EnumAnnotationEntity getEnumAnnotationEntity(String group) {
        return enumAnnotationEntityMap.get(group);
    }

    public synchronized void addEnumAnnotationEntity(EnumAnnotationEntity enumAnnotationEntity) {
        enumAnnotationEntity.setGroup(getGroup(enumAnnotationEntity.getGroupClass()));
        enumAnnotationEntityMap.put(enumAnnotationEntity.getGroup(), enumAnnotationEntity);
    }

    private <T> String getGroup(Class<T> groupClass) {
        return groupClass.getName();
    }

}
