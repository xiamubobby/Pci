package com.wonders.xlab.patient.data.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/2.
 */
public class SendMessageEntity extends BaseEntity {

    /**
     * ret_values : 111111
     */

    private long ret_values;

    public void setRet_values(long ret_values) {
        this.ret_values = ret_values;
    }

    public long getRet_values() {
        return ret_values;
    }
}
