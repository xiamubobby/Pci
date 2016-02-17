package com.wonders.xlab.pci.assist.deviceconnection.otto;

/**
 * Created by hua on 15/12/31.
 */
public class ConnStatusOtto {
    private STATUS mStatus;

    public enum STATUS {
        START, SUCCESS, FAILED
    }

    public ConnStatusOtto(STATUS status) {
        mStatus = status;
    }

    public STATUS getStatus() {
        return mStatus;
    }

    public void setStatus(STATUS status) {
        mStatus = status;
    }
}
