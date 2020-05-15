package org.framework.hsven.dataloader.related.task;

import org.framework.hsven.dataloader.beans.dependency.SheetDefine;
import org.framework.hsven.dataloader.beans.dependency.SheetDefineHelper;
import org.framework.hsven.dataloader.beans.dependency.SheetDependency;
import org.framework.hsven.dataloader.beans.dependency.SheetDependencyHelper;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.related.ITableLoaderTask;
import org.framework.hsven.dataloader.related.RelatedLoaderHandlerHolder;
import org.framework.hsven.dataloader.related.TableLoadResult;
import org.framework.hsven.dataloader.related.child.CallableTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleMainTableCallableTaskDependency;
import org.framework.hsven.dataloader.related.main.MainTableLoadPartitionContext;
import org.framework.hsven.dataloader.related.main.task.MainTableLoaderTask;
import org.framework.hsven.dataloader.valid.related.TableLoadDefineUtil;
import org.framework.hsven.utils.valid.ValidResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 整体控制关联加载的流程
 */
public class RelatedTableLoaderTask implements ITableLoaderTask<TableLoadResult> {
    private static Logger logger = LoggerFactory.getLogger(RelatedTableLoaderTask.class);
    private final RelatedLoaderHandlerHolder relatedLoaderHandlerHolder;
    private final MainTableLoadPartitionContext mainTableLoadPartitionContext;
    private final Object tableModel;
    private TableLoadDefine tableLoadDefine;
    private CallableTaskDependency callableTaskDependency;
    private SimpleMainTableCallableTaskDependency simpleMainTableCallableTaskDependency;

    public RelatedTableLoaderTask(RelatedLoaderHandlerHolder relatedLoaderHandlerHolder, MainTableLoadPartitionContext mainTableLoadPartitionContext, Object tableModel) {
        Assert.isTrue(tableModel != null, "Loader Data TableModel can't null");
        this.relatedLoaderHandlerHolder = relatedLoaderHandlerHolder;
        this.mainTableLoadPartitionContext = mainTableLoadPartitionContext;
        this.tableModel = tableModel;
    }

    /**
     * 创建完毕后,需要先执行此方法,以验证配置是否正确,是否满足关联表加载的配置条件
     *
     * @return
     */
    public ValidResult isEnable() {
        logger.info(String.format("%s RelatedTableLoaderTask start valid tableModel config[%s]!", this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), this.tableModel));
        ValidResult validResult = new ValidResult();
        this.tableLoadDefine = this.relatedLoaderHandlerHolder.getiRelatedTableParserHandler().parser(this.tableModel);
        ValidResult tableLoadDefineValidResult = TableLoadDefineUtil.isEnableTableLoadDefine(this.tableLoadDefine);
        if (!tableLoadDefineValidResult.isNormal()) {
            validResult.mergeValidConfigResult(tableLoadDefineValidResult);
        }
        SheetDefine sheetDefine = SheetDefineHelper.structSheetDefine(this.tableLoadDefine);
        StringBuffer verifyStringBuffer = SheetDefineHelper.validSheetDefine(sheetDefine);
        if (verifyStringBuffer == null || verifyStringBuffer.length() == 0) {
            SheetDependency sheetDependency = SheetDefineHelper.parserDependency(sheetDefine);
            this.callableTaskDependency = SheetDefineHelper.structCallableTaskDependency(sheetDependency, this.tableLoadDefine);
            this.simpleMainTableCallableTaskDependency = SheetDependencyHelper.structCallableRelatedTaskDependency(sheetDependency, this.tableLoadDefine);
        } else {
            validResult.appendAllTipType(verifyStringBuffer.toString());
        }
        if (!validResult.isNormal()) {
            logger.error(String.format("%s RelatedTableLoaderTask tableModel config[%s] error:%s", this.relatedLoaderHandlerHolder.getiRelatedTableLoadListener().relatedListenerIdentification(), this.tableLoadDefine, validResult.getDefaultAbnormalInfo()));
        }
        return validResult;
    }

    @Override
    public void destory() {
        String defineType = this.tableLoadDefine.getDefineType();
        this.tableLoadDefine = null;
        if (this.simpleMainTableCallableTaskDependency != null) {
            this.simpleMainTableCallableTaskDependency.destory(defineType);
        }
        this.simpleMainTableCallableTaskDependency = null;
    }

    @Override
    public TableLoadResult call() throws Exception {
        try {
            ITableLoaderTask<TableLoadResult> iTableLoaderTask = createMainTableLoaderTask();
            TableLoadResult tableLoadResult = iTableLoaderTask.call();
            iTableLoaderTask.destory();
            return tableLoadResult;
        } finally {
            destory();
        }
    }

    private ITableLoaderTask<TableLoadResult> createMainTableLoaderTask() {
        MainTableLoaderTask mainTableLoaderTask = new MainTableLoaderTask(this.relatedLoaderHandlerHolder, this.mainTableLoadPartitionContext, this.tableLoadDefine, this.simpleMainTableCallableTaskDependency);
        return mainTableLoaderTask;
    }
}
