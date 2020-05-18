package org.framework.hsven.dataloader.beans.loader;

import org.framework.hsven.dataloader.beans.data.DBTableRowInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelatedValuesAndRowIndexGroup {
    private static Logger logger = LoggerFactory.getLogger(RelatedValuesAndRowIndexGroup.class);
    private final DefineRelatedFields defineRelatedFields;
    private RelatedValuesAndRowIndexEntity relatedValuesAndRowIndexEntity = new RelatedValuesAndRowIndexEntity();

    public RelatedValuesAndRowIndexGroup(DefineRelatedFields defineRelatedFields) {
        this.defineRelatedFields = defineRelatedFields;
    }

    public boolean addDBTableRowInfo(DBTableRowInfo dbTableRowInfo) {
        return relatedValuesAndRowIndexEntity.addDBTableRowInfo(defineRelatedFields, dbTableRowInfo);
    }

    public RelatedValuesAndRowIndexEntity readAndClear() {
        RelatedValuesAndRowIndexEntity return_values = null;
        try {
            return_values = this.relatedValuesAndRowIndexEntity;
            this.relatedValuesAndRowIndexEntity = new RelatedValuesAndRowIndexEntity();
        } catch (Exception e) {
            logger.error("RelatedValuesAndRowIndexGroup readAndClear Exception", e);
        }

        return return_values;
    }

    public void destory() {
        if (relatedValuesAndRowIndexEntity != null) {
            relatedValuesAndRowIndexEntity.destory();
        }
        relatedValuesAndRowIndexEntity = null;
    }
}
