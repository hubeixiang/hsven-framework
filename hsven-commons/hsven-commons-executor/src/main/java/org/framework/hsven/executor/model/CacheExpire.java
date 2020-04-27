package org.framework.hsven.executor.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sven on 2017/3/10.
 */
public class CacheExpire implements Serializable {
    private static final long serialVersionUID = 1L;
    //创建时间
    private Date createDate;
    //超时时间,为null时表示永不超时
    private Date expiredDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
