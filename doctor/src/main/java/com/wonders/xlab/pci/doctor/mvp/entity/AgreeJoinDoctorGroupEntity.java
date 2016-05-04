package com.wonders.xlab.pci.doctor.mvp.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/19.
 */
public class AgreeJoinDoctorGroupEntity extends BaseEntity{


    /**
     * doctorGroupId : 1
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String doctorGroupId;

        public String getDoctorGroupId() {
            return doctorGroupId;
        }

        public void setDoctorGroupId(String doctorGroupId) {
            this.doctorGroupId = doctorGroupId;
        }
    }
}
