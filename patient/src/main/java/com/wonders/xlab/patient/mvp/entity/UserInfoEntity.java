package com.wonders.xlab.patient.mvp.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/5/10.
 */
public class UserInfoEntity extends BaseEntity {

    /**
     * address : 上海市黄浦区外马路974号
     * bracketNum : 1
     * lastOperationDate :
     * hospital :
     * surgeon :
     * caseHistory :
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String name;
        private String sex;
        private String age;
        private String addressCode;
        private String address;
        private String bracketNum;
        private long lastOperationDate;
        private String hospital;
        private String hospitalId;
        private String surgeon;
        private String caseHistory;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAddressCode() {
            return addressCode;
        }

        public void setAddressCode(String addressCode) {
            this.addressCode = addressCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBracketNum() {
            return bracketNum;
        }

        public void setBracketNum(String bracketNum) {
            this.bracketNum = bracketNum;
        }

        public long getLastOperationDate() {
            return lastOperationDate;
        }

        public void setLastOperationDate(long lastOperationDate) {
            this.lastOperationDate = lastOperationDate;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getSurgeon() {
            return surgeon;
        }

        public void setSurgeon(String surgeon) {
            this.surgeon = surgeon;
        }

        public String getCaseHistory() {
            return caseHistory;
        }

        public void setCaseHistory(String caseHistory) {
            this.caseHistory = caseHistory;
        }
    }
}
