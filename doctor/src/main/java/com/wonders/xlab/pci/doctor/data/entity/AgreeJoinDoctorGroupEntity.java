package com.wonders.xlab.pci.doctor.data.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/19.
 */
public class AgreeJoinDoctorGroupEntity extends BaseEntity{

    /**
     * ret_values : 同意加入诊所
     */

    private String ret_values;

    public String getRet_values() {
        return ret_values;
    }

    public void setRet_values(String ret_values) {
        this.ret_values = ret_values;
    }
}
