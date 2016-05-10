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
        private String address;
        private int bracketNum;
        private String lastOperationDate;
        private String hospital;
        private String surgeon;
        private String caseHistory;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getBracketNum() {
            return bracketNum;
        }

        public void setBracketNum(int bracketNum) {
            this.bracketNum = bracketNum;
        }

        public String getLastOperationDate() {
            return lastOperationDate;
        }

        public void setLastOperationDate(String lastOperationDate) {
            this.lastOperationDate = lastOperationDate;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
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
