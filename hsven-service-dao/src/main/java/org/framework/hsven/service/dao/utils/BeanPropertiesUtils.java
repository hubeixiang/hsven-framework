package org.framework.hsven.service.dao.utils;

import org.framework.hsven.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: sven
 * @create: 2021-10-11
 **/

public class BeanPropertiesUtils {

    public static <E> List<String> parserResultColumns(List<E> entitys) {
        List<String> columns = null;
        if (entitys == null || entitys.size() == 0) {
            columns = new ArrayList<>();
        } else {
            columns = parserResultColumn(entitys.get(0));
        }
        return columns;
    }

    public static <E> List<String> parserResultColumn(E entity) {
        List<String> columns = new ArrayList<>();
        if (entity != null) {
            Map<String, Object> map = JsonUtils.fromJson(JsonUtils.toJson(entity), Map.class);
            columns.addAll(map.keySet());
        }
        return columns;
    }

}
