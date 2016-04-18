package com.wonders.xlab.pci.doctor.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/4/18.
 */
public class NotifiOthersRealm extends RealmObject {
    private String message;
    private long receiveTimeInMill;
    private boolean hasRead;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getReceiveTimeInMill() {
        return receiveTimeInMill;
    }

    public void setReceiveTimeInMill(long receiveTimeInMill) {
        this.receiveTimeInMill = receiveTimeInMill;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }
}
