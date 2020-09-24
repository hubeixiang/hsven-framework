package org.framework.hsven.annotation.function;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能注解的所有类的注解描述集合
 * 单例
 *
 * @see FunctionClassAnnotation
 */
public class FunctionAnnotationCollection {
    private static FunctionAnnotationCollection instance;
    private static Map<String, FunctionAnnotationEntitys> functionAnnotationEntityMap = new HashMap<>();

    private FunctionAnnotationCollection() {
    }

    public static FunctionAnnotationCollection getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new FunctionAnnotationCollection();
        }
    }

    public <T> boolean containsKey(Class<T> functionClass) {
        return containsKey(getGroup(functionClass));
    }

    public boolean containsKey(String group) {
        return functionAnnotationEntityMap.containsKey(group);
    }

    public <T> FunctionAnnotationEntitys getFunctionAnnotationEntitys(Class<T> functionClass) {
        return getFunctionAnnotationEntitys(getGroup(functionClass));
    }

    public FunctionAnnotationEntitys getFunctionAnnotationEntitys(String group) {
        return functionAnnotationEntityMap.get(group);
    }

    public synchronized void addFunctionAnnotationEntity(FunctionAnnotationEntity functionAnnotationEntity) {
        FunctionAnnotationEntitys functionAnnotationEntitys = null;
        if (containsKey(functionAnnotationEntity.getGroupClass())) {
            functionAnnotationEntitys = getFunctionAnnotationEntitys(functionAnnotationEntity.getGroupClass());
        } else {
            functionAnnotationEntitys = new FunctionAnnotationEntitys();
            functionAnnotationEntitys.setGroup(functionAnnotationEntity.getGroup());
            functionAnnotationEntitys.setGroupClass(functionAnnotationEntity.getGroupClass());
            functionAnnotationEntitys.setGroupName(functionAnnotationEntity.getGroupName());
            functionAnnotationEntityMap.put(getGroup(functionAnnotationEntity.getGroupClass()), functionAnnotationEntitys);
        }
        functionAnnotationEntitys.getFunctionEntity().getData().add(functionAnnotationEntity.getFunctionValue());
    }

    private <T> String getGroup(Class<T> groupClass) {
        return groupClass.getName();
    }
}
