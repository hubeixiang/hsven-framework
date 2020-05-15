package org.framework.hsven.dataloader.related;


import org.framework.hsven.dataloader.loader.model.QueryLoaderResultDesc;

public class TableLoadResult {

    private String taskId;
    private long taskCreateTimeMS;
    private boolean dealResult;
    private long dealUsingTimeMS;
    private QueryLoaderResultDesc queryLoaderResultDesc;

    public long getTaskUsingTimeMS() {
        long currentTimeMillis = System.currentTimeMillis();

        return currentTimeMillis - taskCreateTimeMS;
    }

    public boolean isDealResult() {
        return dealResult;
    }

    public void setDealResult(boolean dealResult) {
        this.dealResult = dealResult;
    }

    public long getDealUsingTimeMS() {
        return dealUsingTimeMS;
    }

    public void setDealUsingTimeMS(long dealUsingTimeMS) {
        this.dealUsingTimeMS = dealUsingTimeMS;
    }

    public long getTaskCreateTimeMS() {
        return taskCreateTimeMS;
    }

    public void setTaskCreateTimeMS(long taskCreateTimeMS) {
        this.taskCreateTimeMS = taskCreateTimeMS;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public QueryLoaderResultDesc getQueryLoaderResultDesc() {
        return queryLoaderResultDesc;
    }

    public void setQueryLoaderResultDesc(QueryLoaderResultDesc queryLoaderResultDesc) {
        this.queryLoaderResultDesc = queryLoaderResultDesc;
    }

    @Override
    public String toString() {
        return "TableLoadResult [taskId=" + taskId + ", taskCreateTimeMS=" + taskCreateTimeMS + ", dealResult=" + dealResult + ", dealUsingTimeMS=" + dealUsingTimeMS
                + ", queryLoaderResultDesc=" + queryLoaderResultDesc + "]";
    }

}
