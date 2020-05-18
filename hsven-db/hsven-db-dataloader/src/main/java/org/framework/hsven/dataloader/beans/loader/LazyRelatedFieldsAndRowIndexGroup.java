package org.framework.hsven.dataloader.beans.loader;

import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LazyRelatedFieldsAndRowIndexGroup {
    private static Logger logger = LoggerFactory.getLogger(LazyRelatedFieldsAndRowIndexGroup.class);
    private final DefineRelatedFields defineRelatedFields;
    private LazyRelatedFieldsAndRowIndex lazyRelatedFieldsAndRowIndex;

    public LazyRelatedFieldsAndRowIndexGroup(DefineRelatedFields defineRelatedFields) {
        this.defineRelatedFields = defineRelatedFields;
        this.lazyRelatedFieldsAndRowIndex = new LazyRelatedFieldsAndRowIndex(defineRelatedFields);
    }

    public DefineRelatedFields getDefineRelatedFields() {
        return defineRelatedFields;
    }

    public LazyRelatedFieldsAndRowIndex getLazyRelatedFieldsAndRowIndex() {
        return lazyRelatedFieldsAndRowIndex;
    }

    public boolean addDBTableRowInfo(DBTableRowInfo dbTableRowInfo) {
        return lazyRelatedFieldsAndRowIndex.getRelatedRowIndexList().add(dbTableRowInfo.getRowIndex());
    }

    public LazyRelatedFieldsAndRowIndex readAndClear() {
        LazyRelatedFieldsAndRowIndex return_values = null;
        try {
            return_values = this.lazyRelatedFieldsAndRowIndex;
            this.lazyRelatedFieldsAndRowIndex = new LazyRelatedFieldsAndRowIndex(this.defineRelatedFields);
        } catch (Exception e) {
            logger.error("LazyRelatedFieldsAndRowIndexGroup readAndClear Exception", e);
        }

        return return_values;
    }

    public void destory() {
        if (lazyRelatedFieldsAndRowIndex != null) {
            lazyRelatedFieldsAndRowIndex.destory();
        }
        lazyRelatedFieldsAndRowIndex = null;
    }
}
