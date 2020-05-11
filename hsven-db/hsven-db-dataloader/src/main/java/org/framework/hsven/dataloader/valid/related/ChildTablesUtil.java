package org.framework.hsven.dataloader.valid.related;

import org.framework.hsven.dataloader.beans.related.ChildTables;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.tips.TipsMessageUsed;
import org.framework.hsven.utils.valid.ValidResult;

import java.util.Iterator;
import java.util.Map;

public class ChildTablesUtil {
    public final static ValidResult isEnable(ChildTables childTables) {
        ValidResult validResult = new ValidResult();
        if (childTables == null || !childTables.hasSimpleChildTable()) {
            return validResult;
        }
        Iterator<Map.Entry<String, SimpleChildTable>> it = childTables.entryChildTables();
        while (it != null && it.hasNext()) {
            Map.Entry<String, SimpleChildTable> entry = it.next();
            SimpleChildTable simpleChildTable = entry.getValue();
            ValidResult childValidResult = SimpleChildTableUtil.isEnable(simpleChildTable);
            if (!childValidResult.isNormal()) {
                childValidResult.appendAllTipTypeByPosition(TipsMessageUsed.getMessage("tips.valid_childtable_one_error_config", entry.getKey()));
                validResult.mergeValidConfigResult(childValidResult);
            }
        }
        return validResult;
    }
}
