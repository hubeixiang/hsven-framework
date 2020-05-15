package org.framework.hsven.dataloader.related.task;

import org.framework.hsven.dataloader.related.RelatedLoaderHandlerHolder;
import org.framework.hsven.dataloader.related.main.MainTableLoadPartitionContext;
import org.framework.hsven.utils.valid.ValidResult;

public class RelatedTableLoaderTaskUtil {

    public static RelatedTableLoaderTask createRelatedTableLoaderTask(ValidResult validResult, RelatedLoaderHandlerHolder relatedLoaderHandlerHolder, MainTableLoadPartitionContext mainTableLoadPartitionContext, Object tableModel) {
        if (relatedLoaderHandlerHolder == null) {
            relatedLoaderHandlerHolder = new RelatedLoaderHandlerHolder(null, null, null, null);
        }
        RelatedTableLoaderTask relatedTableLoaderTask = new RelatedTableLoaderTask(relatedLoaderHandlerHolder, mainTableLoadPartitionContext, tableModel);
        ValidResult enableValidResult = relatedTableLoaderTask.isEnable();
        if (!enableValidResult.isNormal()) {
            validResult.mergeValidConfigResult(enableValidResult);
        }
        return relatedTableLoaderTask;
    }
}
