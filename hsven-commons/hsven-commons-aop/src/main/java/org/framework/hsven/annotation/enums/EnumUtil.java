package org.framework.hsven.annotation.enums;

import java.util.function.Function;
import java.util.function.Supplier;

public class EnumUtil {
    public static <R> R valueOfEnum(String value, Function<String, R> function) {
        return function.apply(value);
    }

    public static void valueOfEnums(Object[] array, String value) {
        DefineEnumType.FieldDisplayEnumType[] xxx = DefineEnumType.FieldDisplayEnumType.values();

    }

    public static <T> T convert(Supplier<T[]> supllier, Function<T, String> function, String value) {
        T[] array = supllier.get();
        if (array != null && array.length > 0) {
            for (T xx : array) {
                if (value.equalsIgnoreCase(function.apply(xx))) {
                    return xx;
                }
            }
        }
        return null;
    }


    public static class InternalEnum {
        Enum<?> enumType;

        public Enum<?> getEnumType() {
            return enumType;
        }

        public void setEnumType(Enum<?> enumType) {
            this.enumType = enumType;
        }
    }
}
