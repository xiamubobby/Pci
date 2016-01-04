package com.wonders.xlab.pci.module.base.mvn.entity.login;

import com.wonders.xlab.pci.module.base.mvn.entity.BaseEntity;

/**
 * Created by hua on 15/12/17.
 */
public class LoginEntity extends BaseEntity{


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
        private int id;
        private String tel;
        private String password;
        private String medicareCard;

        public void setId(int id) {
            this.id = id;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setMedicareCard(String medicareCard) {
            this.medicareCard = medicareCard;
        }

        public int getId() {
            return id;
        }

        public String getTel() {
            return tel;
        }

        public String getPassword() {
            return password;
        }

        public String getMedicareCard() {
            return medicareCard;
        }
    }
}
