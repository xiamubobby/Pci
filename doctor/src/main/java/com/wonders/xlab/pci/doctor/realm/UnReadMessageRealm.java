package com.wonders.xlab.pci.doctor.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/4/4.
 */
public class UnReadMessageRealm extends RealmObject {
    private String imGroupId;
    private String ownerId;
    /**
     * 3：聊天
     * 5：血压异常
     * 6、血糖异常
     * 7、上传就诊记录图片
     * 8、保存了不适症状
     */
    private int type = 3;

    public UnReadMessageRealm() {
    }

    public UnReadMessageRealm(String ownerId, String imGroupId) {
        this.ownerId = ownerId;
        this.imGroupId = imGroupId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(String imGroupId) {
        this.imGroupId = imGroupId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
