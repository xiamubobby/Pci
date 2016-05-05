package com.wonders.xlab.patient.mvp.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/5/5.
 */
public class RegisterEntity extends BaseEntity{

    /**
     * avatarUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
     * name : 张三
     * tel : 13248227958
     * id : 1
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String avatarUrl;
        private String name;
        private String tel;
        private String id;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
