package com.wonders.xlab.pci.mvn.entity.record.monitor;

import com.wonders.xlab.pci.mvn.entity.BaseEntity;

import java.util.List;

public class BSEntity extends BaseEntity {

    /**
     * id : 10
     * bloodSugar : 126.0
     * bloodSugarTime : 1
     * recordTime : 1450281600000
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
        private double bloodSugar;
        private int bloodSugarTime;
        private long recordTime;

        public void setId(int id) {
            this.id = id;
        }

        public void setBloodSugar(double bloodSugar) {
            this.bloodSugar = bloodSugar;
        }

        public void setBloodSugarTime(int bloodSugarTime) {
            this.bloodSugarTime = bloodSugarTime;
        }

        public void setRecordTime(long recordTime) {
            this.recordTime = recordTime;
        }

        public int getId() {
            return id;
        }

        public double getBloodSugar() {
            return bloodSugar;
        }

        public int getBloodSugarTime() {
            return bloodSugarTime;
        }

        public long getRecordTime() {
            return recordTime;
        }
    }
}
