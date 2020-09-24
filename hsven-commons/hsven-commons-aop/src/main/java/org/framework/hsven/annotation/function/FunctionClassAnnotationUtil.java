package org.framework.hsven.annotation.function;

public class FunctionClassAnnotationUtil {

    public static FunctionAnnotationEntity parserFunctionAnnotationEntity(Class<?> group, String groupName, String beanName, String beanDesc, String format) {
        FunctionAnnotationEntity functionAnnotationEntity = new FunctionAnnotationEntity();
        functionAnnotationEntity.setGroupClass(group);
        functionAnnotationEntity.setGroup(getGroup(group));
        functionAnnotationEntity.setGroupName(groupName);
        FunctionValue functionValue = new FunctionValue();
        functionAnnotationEntity.setFunctionValue(functionValue);
        functionValue.setValue(beanName);
        functionValue.setName(beanDesc);
        functionValue.setFormat(format);
        return functionAnnotationEntity;
    }

    public static boolean containsValue(FunctionAnnotationEntitys functionAnnotationEntitys, String value) {
        FunctionEntity functionEntity = functionAnnotationEntitys.getFunctionEntity();
        if (functionEntity != null && functionEntity.getData() != null) {
            for (FunctionValue functionValue : functionEntity.getData()) {
                if (functionValue.getValue().equals(value)) {
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
