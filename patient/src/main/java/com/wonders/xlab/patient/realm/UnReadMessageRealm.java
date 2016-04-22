package com.wonders.xlab.patient.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/4/4.
 */
public class UnReadMessageRealm extends RealmObject {
    private String imGroupId;
    private String ownerId;
    private int type;

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
