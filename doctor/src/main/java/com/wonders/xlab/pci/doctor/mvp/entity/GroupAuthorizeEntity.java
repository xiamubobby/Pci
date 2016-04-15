package com.wonders.xlab.pci.doctor.mvp.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/14.
 */
public class GroupAuthorizeEntity extends BaseEntity {

    /**
     * id : 7
     * tel : 18317725722
     * name : 超妹
     * jobTitle : 主任医师
     * type : Attending
     * avatarUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
     * description : 你好，超妹
     * hospital : {"id":1,"name":"第一人民医院"}
     * department : {"id":1,"name":"儿科"}
     * imId : doctor18317725722
     */

    private String ret_values;

    public String getRet_values() {
        return ret_values;
    }

    public void setRet_values(String ret_values) {
        this.ret_values = ret_values;
    }
}
