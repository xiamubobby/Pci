package com.wonders.xlab.patient.mvp.entity;


import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 15/12/17.
 */
public class LoginEntity extends BaseEntity {


    /**
     * id : 1
     * createdDate : 1450317211000
     * lastModifiedDate : 1450317211000
     * tel : 13621673989
     * password : 111111
     * medicareCard : 1234567890
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private String id;
        private String tel;
        private String name;
        private String sex;
        private String age;
        private String avatarUrl;
        private String medicareCard;

        public void setId(String id) {
            this.id = id;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setMedicareCard(String medicareCard) {
            this.medicareCard = medicareCard;
        }

        public String getId() {
            return id;
        }

        public String getTel() {
            return tel;
        }

        public String getMedicareCard() {
            return medicareCard;
        }

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

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
