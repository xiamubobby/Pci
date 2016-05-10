package com.wonders.xlab.patient.mvp.entity;


import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 15/12/22.
 * 所有药品
 */
public class MedicineListEntity extends BaseEntity {

    private List<MedicineEntity> ret_values;

    public List<MedicineEntity> getRet_values() {
        return ret_values;
    }

    public void setRet_values(List<MedicineEntity> ret_values) {
        this.ret_values = ret_values;
    }

}
