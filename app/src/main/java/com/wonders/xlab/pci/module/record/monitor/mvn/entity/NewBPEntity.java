package com.wonders.xlab.pci.module.record.monitor.mvn.entity;

import com.wonders.xlab.pci.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by sjy on 2015/12/24.
 */
public class NewBPEntity extends BaseEntity{


    /**
     * id : 3
     * heartRate : 123
     * systolicPressure : 23
     * diastolicPressure : 233
     * recordTime : 1450513800000
     */

    private List<RetValuesEntity> ret_values;

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private int id;
        private int heartRate;
        private int systolicPressure;
        private int diastolicPressure;
        private long recordTime;

        public void setId(int id) {
            this.id = id;
        }

        public void setHeartRate(int heartRate) {
            this.heartRate = heartRate;
        }

        public void setSystolicPressure(int systolicPressure) {
            this.systolicPressure = systolicPressure;
        }

        public void setDiastolicPressure(int diastolicPressure) {
            this.diastolicPressure = diastolicPressure;
        }

        public void setRecordTime(long recordTime) {
            this.recordTime = recordTime;
        }

        public int getId() {
            return id;
        }

        public int getHeartRate() {
            return heartRate;
        }

        public int getSystolicPressure() {
            return systolicPressure;
        }

        public int getDiastolicPressure() {
            return diastolicPressure;
        }

        public long getRecordTime() {
            return recordTime;
        }
    }
}
