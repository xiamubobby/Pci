package com.wonders.xlab.patient.mvp.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServiceEntity extends BaseEntity {
    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
    }
}
