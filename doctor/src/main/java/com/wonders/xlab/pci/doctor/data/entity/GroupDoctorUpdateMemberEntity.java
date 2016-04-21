package com.wonders.xlab.pci.doctor.data.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/14.
 * 包括邀请医生和移除医生
 */
public class GroupDoctorUpdateMemberEntity extends BaseEntity {

    /**
     * doctorGroupId : 2
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
        private String ownerId;

        public String getDoctorGroupId() {
            return doctorGroupId;
        }

        public void setDoctorGroupId(String doctorGroupId) {
            this.doctorGroupId = doctorGroupId;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }
    }
}
