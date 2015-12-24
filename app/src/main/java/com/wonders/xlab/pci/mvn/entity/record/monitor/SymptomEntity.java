package com.wonders.xlab.pci.mvn.entity.record.monitor;

import com.wonders.xlab.pci.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by hua on 15/12/22.
 */
public class SymptomEntity extends BaseEntity {

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private List<String> symptomList;
        /**
         * id : 2
         * user : null
         * content : 大风大风
         * recordTime : 1450720800000
         * portrait : null
         */

        private List<UserSymptomAdvicesEntity> userSymptomAdvices;

        public void setSymptomList(List<String> symptomList) {
            this.symptomList = symptomList;
        }

        public void setUserSymptomAdvices(List<UserSymptomAdvicesEntity> userSymptomAdvices) {
            this.userSymptomAdvices = userSymptomAdvices;
        }

        public List<String> getSymptomList() {
            return symptomList;
        }

        public List<UserSymptomAdvicesEntity> getUserSymptomAdvices() {
            return userSymptomAdvices;
        }

        public static class UserSymptomAdvicesEntity {
            private int id;
            private Object user;
            private String content;
            private long recordTime;
            private String portrait;

            public void setId(int id) {
                this.id = id;
            }

            public void setUser(Object user) {
                this.user = user;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setRecordTime(long recordTime) {
                this.recordTime = recordTime;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public int getId() {
                return id;
            }

            public Object getUser() {
                return user;
            }

            public String getContent() {
                return content;
            }

            public long getRecordTime() {
                return recordTime;
            }

            public String getPortrait() {
                return portrait;
            }
        }
    }
}
