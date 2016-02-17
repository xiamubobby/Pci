package com.wonders.xlab.pci.assist.deviceconnection.otto;

/**
 * Created by hua on 16/1/7.
 */
public class RequestDataFailed {
    private String message;

    public RequestDataFailed(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
