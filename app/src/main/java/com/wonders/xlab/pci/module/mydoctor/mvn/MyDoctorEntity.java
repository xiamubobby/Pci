package com.wonders.xlab.pci.module.mydoctor.mvn;

import com.wonders.xlab.pci.module.base.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by hua on 16/1/28.
 */
public class MyDoctorEntity extends BaseEntity {

    /**
     * name : 陈诚
     * id : 32
     * parentdepartname : 心血管内科
     * hospitalname : 上海交通大学附属瑞金医院
     * title : 主任医师
     * pic : null
     * des : 主任医师
     */

    private List<DoctorInfoEntity> ret_values;

    public void setRet_values(List<DoctorInfoEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public List<DoctorInfoEntity> getRet_values() {
        return ret_values;
    }

}
