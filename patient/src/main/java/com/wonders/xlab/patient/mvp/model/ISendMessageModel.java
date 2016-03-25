package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/3/21.
 */
public interface ISendMessageModel extends IBaseModel {
    void sendMessage(String message, String patientTel, String imGroupId, long time);
}
