package com.wonders.xlab.pci.mvn.entity.record.monitor;

import com.wonders.xlab.pci.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineEntity extends BaseEntity {

    /**
     * name : 阿斯匹灵
     * userMedicineRecords : [{"id":3,"measurement":"6","doseCount":4,"recordTime":1450710000000},{"id":2,"measurement":"3","doseCount":1,"recordTime":1450706400000},{"id":1,"measurement":"2","doseCount":2,"recordTime":1450627200000}]
     */

    private List<RetValuesEntity> ret_values;

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private String name;
        /**
         * id : 3
         * measurement : 6
         * doseCount : 4
         * recordTime : 1450710000000
         */

        private List<UserMedicineRecordsEntity> userMedicineRecords;

        public void setName(String name) {
            this.name = name;
        }

        public void setUserMedicineRecords(List<UserMedicineRecordsEntity> userMedicineRecords) {
            this.userMedicineRecords = userMedicineRecords;
        }

        public String getName() {
            return name;
        }

        public List<UserMedicineRecordsEntity> getUserMedicineRecords() {
            return userMedicineRecords;
        }

        public static class UserMedicineRecordsEntity {
            private int id;
            private String measurement;
            private int doseCount;
            private long recordTime;

            public void setId(int id) {
                this.id = id;
            }

            public void setMeasurement(String measurement) {
                this.measurement = measurement;
            }

            public void setDoseCount(int doseCount) {
                this.doseCount = doseCount;
            }

            public void setRecordTime(long recordTime) {
                this.recordTime = recordTime;
            }

            public int getId() {
                return id;
            }

            public String getMeasurement() {
                return measurement;
            }

            public int getDoseCount() {
                return doseCount;
            }

            public long getRecordTime() {
                return recordTime;
            }
        }
    }
}
