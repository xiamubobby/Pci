package com.wonders.xlab.patient.mvp.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/24.
 */
public class BSSaveEntity extends BaseEntity {

    /**
     * bloodSugar : 7.3
     * recordTime : 1458800282604
     * bloodSugarTime : 2
     * id : 91
     * status : 1
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String userId;
        private String bloodSugar;
        private long recordTime;
        private String bloodSugarTime;
        private String id;
        private int status;

        public String getBloodSugar() {
            return bloodSugar;
        }

        public void setBloodSugar(String bloodSugar) {
            this.bloodSugar = bloodSugar;
        }

        public long getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(long recordTime) {
            this.recordTime = recordTime;
        }

        public String getBloodSugarTime() {
            return bloodSugarTime;
        }

        public void setBloodSugarTime(String bloodSugarTime) {
            this.bloodSugarTime = bloodSugarTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
