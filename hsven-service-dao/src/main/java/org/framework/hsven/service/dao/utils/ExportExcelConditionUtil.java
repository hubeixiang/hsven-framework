package org.framework.hsven.service.dao.utils;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.service.dao.condition.ExportExcelCondition;
import org.framework.hsven.service.dao.condition.FieldMapping;
import org.springframework.util.CollectionUtils;

import java.util.List;

public final class ExportExcelConditionUtil {
    public static boolean filterFieldMapping(ExportExcelCondition fieldMappingCondition) {
        if (fieldMappingCondition != null && !filterFieldMappingIsEmpty(fieldMappingCondition.getMappings())) {
            return true;
        }
        return false;
    }

    public static boolean filterFieldMappingIsEmpty(List<FieldMapping> mappings) {
        if (CollectionUtils.isEmpty(mappings)) {
            return true;
        }

        boolean allFieldNameEmpty = true;
        for (FieldMapping fieldMapping : mappings) {
            if (StringUtils.isNotEmpty(fieldMapping.getFieldName())) {
                allFieldNameEmpty = false;
            }
        }
        return allFieldNameEmpty;
    }
}
