package org.framework.hsven.dataloader.valid.related;

import org.apache.commons.lang3.StringUtils;
import org.framework.hsven.dataloader.beans.related.ChildTable;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.utils.valid.ValidResult;

import java.util.Iterator;
import java.util.Map;

public class TableLoadDefineUtil {
    public final static ValidResult isEnableTableLoadDefine(TableLoadDefine tableLoadDefine) {
        ValidResult validResult = new ValidResult();
        if (StringUtils.isEmpty(tableLoadDefine.getDefineType())) {
            validResult.appendAllTipType("defineType必须配置");
        }
        if (tableLoadDefine.getMainTable() == null) {
            validResult.appendAllTipType("主表必须配置");
            return validResult;
        } else {
            ValidResult mainValidResult = MainTableUtil.isEnable(tableLoadDefine.getMainTable());
            if (!mainValidResult.isNormal()) {
                mainValidResult.appendAllTipType("主表配置错误如下:");
                validResult.mergeValidConfigResult(mainValidResult);
            }
        }

        Iterator<Map.Entry<String, ChildTable>> it = tableLoadDefine.entryChildTables();
        while (it != null && it.hasNext()) {
            Map.Entry<String, ChildTable> entry = it.next();
            ChildTable childTable = entry.getValue();
            ValidResult childValidResult = ChildTableUtil.isEnable(childTable);
            if (!childValidResult.isNormal()) {
                childValidResult.appendAllTipTypeByPosition(String.format("子表[%s]配置错误如下:", entry.getKey()));
                validResult.mergeValidConfigResult(childValidResult);
            }
        }

        return validResult;
    }
}
