package com.wonders.xlab.patient.assist.deviceconnection.otto;

/**
 * Created by hua on 16/1/7.
 */
public class EmptyDataOtto {
    private boolean isEmpty;

    public EmptyDataOtto(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
