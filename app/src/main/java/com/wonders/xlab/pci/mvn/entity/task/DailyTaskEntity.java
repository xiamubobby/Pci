package com.wonders.xlab.pci.mvn.entity.task;

import android.os.Parcel;
import android.os.Parcelable;

import com.wonders.xlab.pci.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by hua on 15/12/16.
 */
public class DailyTaskEntity extends BaseEntity {

    /**
     * currentDate : 1450682365951
     * weekCount : 0
     * userActivityDtos : [{"currentDay":1450627200000,"names":["复诊1","复诊2"]}]
     * userMedicineMap : {"Morning":[{"id":1,"medicineName":"头痛药","measurement":"20","medicineSchedule":"Morning","take":true}],"Noon":[{"id":2,"medicineName":"头痛药1","measurement":"30","medicineSchedule":"Noon","take":true}],"Evening":[{"id":3,"medicineName":"头痛药3","measurement":"20","medicineSchedule":"Evening","take":false}]}
     * userBloodPressures : [{"id":3,"heartRate":123,"systolicPressure":23,"diastolicPressure":233,"recordTime":1450686600000},{"id":4,"heartRate":55,"systolicPressure":121,"diastolicPressure":55,"recordTime":1450690020000}]
     * userBloodSugars : [{"id":12,"bloodSugar":122,"bloodSugarTime":0,"recordTime":1450689960000},{"id":11,"bloodSugar":369,"bloodSugarTime":4,"recordTime":1450694640000}]
     * symptoms : ["感冒 3次","发热 1次","胸闷 1次"]
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
        private UserMedicineMapEntity userMedicineMap;
        /**
         * currentDay : 1450627200000
         * names : ["复诊1","复诊2"]
         */

        private List<UserActivityDtosEntity> userActivityDtos;
        /**
         * id : 3
         * heartRate : 123
         * systolicPressure : 23
         * diastolicPressure : 233
         * recordTime : 1450686600000
         */

        private List<UserBloodPressuresEntity> userBloodPressures;
        /**
         * id : 12
         * bloodSugar : 122.0
         * bloodSugarTime : 0
         * recordTime : 1450689960000
         */

        private List<UserBloodSugarsEntity> userBloodSugars;

        private List<String> symptoms;

        public void setCurrentDate(long currentDate) {
            this.currentDate = currentDate;
        }

        public void setWeekCount(int weekCount) {
            this.weekCount = weekCount;
        }

        public void setUserMedicineMap(UserMedicineMapEntity userMedicineMap) {
            this.userMedicineMap = userMedicineMap;
        }

        public void setUserActivityDtos(List<UserActivityDtosEntity> userActivityDtos) {
            this.userActivityDtos = userActivityDtos;
        }

        public void setUserBloodPressures(List<UserBloodPressuresEntity> userBloodPressures) {
            this.userBloodPressures = userBloodPressures;
        }

        public void setUserBloodSugars(List<UserBloodSugarsEntity> userBloodSugars) {
            this.userBloodSugars = userBloodSugars;
        }

        public void setSymptoms(List<String> symptoms) {
            this.symptoms = symptoms;
        }

        public long getCurrentDate() {
            return currentDate;
        }

        public int getWeekCount() {
            return weekCount;
        }

        public UserMedicineMapEntity getUserMedicineMap() {
            return userMedicineMap;
        }

        public List<UserActivityDtosEntity> getUserActivityDtos() {
            return userActivityDtos;
        }

        public List<UserBloodPressuresEntity> getUserBloodPressures() {
            return userBloodPressures;
        }

        public List<UserBloodSugarsEntity> getUserBloodSugars() {
            return userBloodSugars;
        }

        public List<String> getSymptoms() {
            return symptoms;
        }

        public static class UserMedicineMapEntity {
            /**
             * id : 1
             * medicineName : 头痛药
             * measurement : 20
             * medicineSchedule : Morning
             * take : true
             */

            private List<MorningEntity> Morning;
            /**
             * id : 2
             * medicineName : 头痛药1
             * measurement : 30
             * medicineSchedule : Noon
             * take : true
             */

            private List<NoonEntity> Noon;
            /**
             * id : 3
             * medicineName : 头痛药3
             * measurement : 20
             * medicineSchedule : Evening
             * take : false
             */

            private List<EveningEntity> Evening;

            public void setMorning(List<MorningEntity> Morning) {
                this.Morning = Morning;
            }

            public void setNoon(List<NoonEntity> Noon) {
                this.Noon = Noon;
            }

            public void setEvening(List<EveningEntity> Evening) {
                this.Evening = Evening;
            }

            public List<MorningEntity> getMorning() {
                return Morning;
            }

            public List<NoonEntity> getNoon() {
                return Noon;
            }

            public List<EveningEntity> getEvening() {
                return Evening;
            }

            public static class MorningEntity {
                private int id;
                private String medicineName;
                private String measurement;
                private String medicineSchedule;
                private boolean take;

                public void setId(int id) {
                    this.id = id;
                }

                public void setMedicineName(String medicineName) {
                    this.medicineName = medicineName;
                }

                public void setMeasurement(String measurement) {
                    this.measurement = measurement;
                }

                public void setMedicineSchedule(String medicineSchedule) {
                    this.medicineSchedule = medicineSchedule;
                }

                public void setTake(boolean take) {
                    this.take = take;
                }

                public int getId() {
                    return id;
                }

                public String getMedicineName() {
                    return medicineName;
                }

                public String getMeasurement() {
                    return measurement;
                }

                public String getMedicineSchedule() {
                    return medicineSchedule;
                }

                public boolean isTake() {
                    return take;
                }
            }

            public static class NoonEntity {
                private int id;
                private String medicineName;
                private String measurement;
                private String medicineSchedule;
                private boolean take;

                public void setId(int id) {
                    this.id = id;
                }

                public void setMedicineName(String medicineName) {
                    this.medicineName = medicineName;
                }

                public void setMeasurement(String measurement) {
                    this.measurement = measurement;
                }

                public void setMedicineSchedule(String medicineSchedule) {
                    this.medicineSchedule = medicineSchedule;
                }

                public void setTake(boolean take) {
                    this.take = take;
                }

                public int getId() {
                    return id;
                }

                public String getMedicineName() {
                    return medicineName;
                }

                public String getMeasurement() {
                    return measurement;
                }

                public String getMedicineSchedule() {
                    return medicineSchedule;
                }

                public boolean isTake() {
                    return take;
                }
            }

            public static class EveningEntity {
                private int id;
                private String medicineName;
                private String measurement;
                private String medicineSchedule;
                private boolean take;

                public void setId(int id) {
                    this.id = id;
                }

                public void setMedicineName(String medicineName) {
                    this.medicineName = medicineName;
                }

                public void setMeasurement(String measurement) {
                    this.measurement = measurement;
                }

                public void setMedicineSchedule(String medicineSchedule) {
                    this.medicineSchedule = medicineSchedule;
                }

                public void setTake(boolean take) {
                    this.take = take;
                }

                public int getId() {
                    return id;
                }

                public String getMedicineName() {
                    return medicineName;
                }

                public String getMeasurement() {
                    return measurement;
                }

                public String getMedicineSchedule() {
                    return medicineSchedule;
                }

                public boolean isTake() {
                    return take;
                }
            }
        }

        public static class UserActivityDtosEntity implements Parcelable {
            private long currentDay;
            private List<String> names;

            public void setCurrentDay(long currentDay) {
                this.currentDay = currentDay;
            }

            public void setNames(List<String> names) {
                this.names = names;
            }

            public long getCurrentDay() {
                return currentDay;
            }

            public List<String> getNames() {
                return names;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(this.currentDay);
                dest.writeStringList(this.names);
            }

            public UserActivityDtosEntity() {
            }

            protected UserActivityDtosEntity(Parcel in) {
                this.currentDay = in.readLong();
                this.names = in.createStringArrayList();
            }

            public static final Creator<UserActivityDtosEntity> CREATOR = new Creator<UserActivityDtosEntity>() {
                public UserActivityDtosEntity createFromParcel(Parcel source) {
                    return new UserActivityDtosEntity(source);
                }

                public UserActivityDtosEntity[] newArray(int size) {
                    return new UserActivityDtosEntity[size];
                }
            };
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
            private float bloodSugar;
            private int bloodSugarTime;
            private long recordTime;

            public void setId(int id) {
                this.id = id;
            }

            public void setBloodSugar(float bloodSugar) {
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

            public float getBloodSugar() {
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
}
