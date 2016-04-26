package com.wonders.xlab.patient.data.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/24.
 */
public class BPSaveEntity extends BaseEntity {

    /**
     * id : 318
     * heartRate : 87
     * systolicPressure : 150
     * diastolicPressure : 98
     * recordTimeInStr : 1458800282604
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String id;
        private String userId;
        private String heartRate;
        private int heartStatus;
        /**
         * 收缩压/高压
         */
        private String systolicPressure;
        private int systolicStatus;
        /**
         * 舒张压/低压
         */
        private String diastolicPressure;
        private int diastolicStatus;
        private long recordTime;
        /**
         * 正常值范围
         */
        private String heartContent;
        private String systolicContent;
        private String diastolicContent;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeartRate() {
            return heartRate;
        }

        public void setHeartRate(String heartRate) {
            this.heartRate = heartRate;
        }

        public String getSystolicPressure() {
            return systolicPressure;
        }

        public void setSystolicPressure(String systolicPressure) {
            this.systolicPressure = systolicPressure;
        }

        public String getDiastolicPressure() {
            return diastolicPressure;
        }

        public void setDiastolicPressure(String diastolicPressure) {
            this.diastolicPressure = diastolicPressure;
        }

        public long getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(long recordTime) {
            this.recordTime = recordTime;
        }

        public int getHeartStatus() {
            return heartStatus;
        }

        public void setHeartStatus(int heartStatus) {
            this.heartStatus = heartStatus;
        }

        public int getSystolicStatus() {
            return systolicStatus;
        }

        public void setSystolicStatus(int systolicStatus) {
            this.systolicStatus = systolicStatus;
        }

        public int getDiastolicStatus() {
            return diastolicStatus;
        }

        public void setDiastolicStatus(int diastolicStatus) {
            this.diastolicStatus = diastolicStatus;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getHeartContent() {
            return heartContent;
        }

        public void setHeartContent(String heartContent) {
            this.heartContent = heartContent;
        }

        public String getSystolicContent() {
            return systolicContent;
        }

        public void setSystolicContent(String systolicContent) {
            this.systolicContent = systolicContent;
        }

        public String getDiastolicContent() {
            return diastolicContent;
        }

        public void setDiastolicContent(String diastolicContent) {
            this.diastolicContent = diastolicContent;
        }
    }
}
