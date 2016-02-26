package com.wonders.xlab.pci.doctor.mvp.entity;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/23.
 */
public class UserInfoEntity extends BaseEntity {

    /**
     * birthday : 1452124800000
     * surgeryHospital : surgeryHospital
     * sex : 女
     * surgeon : 大风大风
     * name : 阿花的咖啡壶
     * tel : 13621673989
     * lastOperationDate : 1452124800000
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private long birthday;
        private String surgeryHospital;
        private String sex;
        private String surgeon;
        private String name;
        private String tel;
        private long lastOperationDate;

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public void setSurgeryHospital(String surgeryHospital) {
            this.surgeryHospital = surgeryHospital;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setSurgeon(String surgeon) {
            this.surgeon = surgeon;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setLastOperationDate(long lastOperationDate) {
            this.lastOperationDate = lastOperationDate;
        }

        public long getBirthday() {
            return birthday;
        }

        public String getSurgeryHospital() {
            return surgeryHospital;
        }

        public String getSex() {
            return sex;
        }

        public String getSurgeon() {
            return surgeon;
        }

        public String getName() {
            return name;
        }

        public String getTel() {
            return tel;
        }

        public long getLastOperationDate() {
            return lastOperationDate;
        }
    }
}
