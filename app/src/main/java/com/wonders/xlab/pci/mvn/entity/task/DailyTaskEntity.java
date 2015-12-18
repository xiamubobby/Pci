package com.wonders.xlab.pci.mvn.entity.task;

import com.wonders.xlab.pci.mvn.BaseEntity;

import java.util.List;

/**
 * Created by hua on 15/12/16.
 */
public class DailyTaskEntity extends BaseEntity {

    /**
     * currentDate : 1450430760000
     * weekCount : 0
     * userActivityDtos : []
     * userMedicineMap : {}
     * userBloodPressures : [{"id":4,"heartRate":55,"systolicPressure":121,"diastolicPressure":55,"recordTime":1450430820000}]
     * userBloodSugars : [{"id":4,"bloodSuger":123,"bloodSugarTime":0,"recordTime":1450368000000},{"id":5,"bloodSuger":123,"bloodSugarTime":0,"recordTime":1450368000000},{"id":6,"bloodSuger":111,"bloodSugarTime":0,"recordTime":1450368000000},{"id":7,"bloodSuger":12,"bloodSugarTime":0,"recordTime":1450368000000},{"id":8,"bloodSuger":23,"bloodSugarTime":0,"recordTime":1450368000000},{"id":9,"bloodSuger":555,"bloodSugarTime":4,"recordTime":1450368000000},{"id":3,"bloodSuger":22,"bloodSugarTime":1,"recordTime":1450425326000},{"id":12,"bloodSuger":122,"bloodSugarTime":0,"recordTime":1450430760000}]
     * userSmokeDtos : [{"value":"12根香烟","recordTime":1450433040000}]
     * smokeStr : 今日一共抽了12根烟
     * userWineDtos : [{"value":"12杯啤酒","recordTime":null}]
     * wineStr : 今日一共饮了12两酒
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private long currentDate;
        private int weekCount;
        private String smokeStr;
        private String wineStr;
        private List<?> userActivityDtos;
        /**
         * id : 4
         * heartRate : 55
         * systolicPressure : 121
         * diastolicPressure : 55
         * recordTime : 1450430820000
         */

        private List<UserBloodPressuresEntity> userBloodPressures;
        /**
         * id : 4
         * bloodSuger : 123.0
         * bloodSugarTime : 0
         * recordTime : 1450368000000
         */

        private List<UserBloodSugarsEntity> userBloodSugars;
        /**
         * value : 12根香烟
         * recordTime : 1450433040000
         */

        private List<UserSmokeDtosEntity> userSmokeDtos;
        /**
         * value : 12杯啤酒
         * recordTime : null
         */

        private List<UserWineDtosEntity> userWineDtos;

        public void setCurrentDate(long currentDate) {
            this.currentDate = currentDate;
        }

        public void setWeekCount(int weekCount) {
            this.weekCount = weekCount;
        }

        public void setSmokeStr(String smokeStr) {
            this.smokeStr = smokeStr;
        }

        public void setWineStr(String wineStr) {
            this.wineStr = wineStr;
        }

        public void setUserActivityDtos(List<?> userActivityDtos) {
            this.userActivityDtos = userActivityDtos;
        }

        public void setUserBloodPressures(List<UserBloodPressuresEntity> userBloodPressures) {
            this.userBloodPressures = userBloodPressures;
        }

        public void setUserBloodSugars(List<UserBloodSugarsEntity> userBloodSugars) {
            this.userBloodSugars = userBloodSugars;
        }

        public void setUserSmokeDtos(List<UserSmokeDtosEntity> userSmokeDtos) {
            this.userSmokeDtos = userSmokeDtos;
        }

        public void setUserWineDtos(List<UserWineDtosEntity> userWineDtos) {
            this.userWineDtos = userWineDtos;
        }

        public long getCurrentDate() {
            return currentDate;
        }

        public int getWeekCount() {
            return weekCount;
        }

        public String getSmokeStr() {
            return smokeStr;
        }

        public String getWineStr() {
            return wineStr;
        }

        public List<?> getUserActivityDtos() {
            return userActivityDtos;
        }

        public List<UserBloodPressuresEntity> getUserBloodPressures() {
            return userBloodPressures;
        }

        public List<UserBloodSugarsEntity> getUserBloodSugars() {
            return userBloodSugars;
        }

        public List<UserSmokeDtosEntity> getUserSmokeDtos() {
            return userSmokeDtos;
        }

        public List<UserWineDtosEntity> getUserWineDtos() {
            return userWineDtos;
        }

        public static class UserBloodPressuresEntity {
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

        public static class UserBloodSugarsEntity {
            private int id;
            private double bloodSuger;
            private int bloodSugarTime;
            private long recordTime;

            public void setId(int id) {
                this.id = id;
            }

            public void setBloodSuger(double bloodSuger) {
                this.bloodSuger = bloodSuger;
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

            public double getBloodSuger() {
                return bloodSuger;
            }

            public int getBloodSugarTime() {
                return bloodSugarTime;
            }

            public long getRecordTime() {
                return recordTime;
            }
        }

        public static class UserSmokeDtosEntity {
            private String value;
            private long recordTime;

            public void setValue(String value) {
                this.value = value;
            }

            public void setRecordTime(long recordTime) {
                this.recordTime = recordTime;
            }

            public String getValue() {
                return value;
            }

            public long getRecordTime() {
                return recordTime;
            }
        }

        public static class UserWineDtosEntity {
            private String value;
            private Object recordTime;

            public void setValue(String value) {
                this.value = value;
            }

            public void setRecordTime(Object recordTime) {
                this.recordTime = recordTime;
            }

            public String getValue() {
                return value;
            }

            public Object getRecordTime() {
                return recordTime;
            }
        }
    }
}
