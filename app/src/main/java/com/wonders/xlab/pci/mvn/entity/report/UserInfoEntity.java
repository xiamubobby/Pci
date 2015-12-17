package com.wonders.xlab.pci.mvn.entity.report;

import com.wonders.xlab.pci.mvn.BaseEntity;

/**
 * Created by hua on 15/12/17.
 */
public class UserInfoEntity extends BaseEntity {

    /**
     * id : 2
     * createdDate : 1450317211000
     * lastModifiedDate : 1450317211000
     * user : null
     * name : 阿花的咖啡壶
     * sex : Male
     * medicareCard : 1234234343
     * idCardNumber : 343435353222
     * address : 阿迪浪费空间的的尖端科技
     * phone : 121212121212
     * height : 111
     * onsetTime : 1450317211000
     * historyCase : 阿拉丁开始减肥 阿拉斯加的看法
     * historySmoking : 阿里看见对方垃圾袋粉
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private int id;
        private long createdDate;
        private long lastModifiedDate;
        private Object user;
        private String name;
        private String sex;
        private String medicareCard;
        private String idCardNumber;
        private String address;
        private String phone;
        private int height;
        private long onsetTime;
        private String historyCase;
        private String historySmoking;

        public void setId(int id) {
            this.id = id;
        }

        public void setCreatedDate(long createdDate) {
            this.createdDate = createdDate;
        }

        public void setLastModifiedDate(long lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public void setUser(Object user) {
            this.user = user;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setMedicareCard(String medicareCard) {
            this.medicareCard = medicareCard;
        }

        public void setIdCardNumber(String idCardNumber) {
            this.idCardNumber = idCardNumber;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setOnsetTime(long onsetTime) {
            this.onsetTime = onsetTime;
        }

        public void setHistoryCase(String historyCase) {
            this.historyCase = historyCase;
        }

        public void setHistorySmoking(String historySmoking) {
            this.historySmoking = historySmoking;
        }

        public int getId() {
            return id;
        }

        public long getCreatedDate() {
            return createdDate;
        }

        public long getLastModifiedDate() {
            return lastModifiedDate;
        }

        public Object getUser() {
            return user;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public String getMedicareCard() {
            return medicareCard;
        }

        public String getIdCardNumber() {
            return idCardNumber;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public int getHeight() {
            return height;
        }

        public long getOnsetTime() {
            return onsetTime;
        }

        public String getHistoryCase() {
            return historyCase;
        }

        public String getHistorySmoking() {
            return historySmoking;
        }
    }
}
