package com.wonders.xlab.pci.doctor.mvp.model.impl;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/3/2.
 */
public interface ISendMessageModel extends IBaseModel {
    void onSendMessageSuccess(long time);
}
