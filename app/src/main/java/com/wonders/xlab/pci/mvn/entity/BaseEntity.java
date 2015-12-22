package com.wonders.xlab.pci.mvn.entity;

/**
 * Created by hua on 15/12/3.
 */
public abstract class BaseEntity{
    private int ret_code;
    private String message;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
