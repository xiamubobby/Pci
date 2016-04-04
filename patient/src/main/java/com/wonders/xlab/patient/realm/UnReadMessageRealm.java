package com.wonders.xlab.patient.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/4/4.
 */
public class UnreadMessageRealm extends RealmObject {
    private String imGroupId;
    private String groupId;
    private int type;

    public UnreadMessageRealm() {
    }

    public UnreadMessageRealm(String groupId, String imGroupId) {
        this.groupId = groupId;
        this.imGroupId = imGroupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
