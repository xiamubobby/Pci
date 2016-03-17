package com.wonders.xlab.pci.doctor.mvp.model.impl;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/2.
 */
public interface SendMessageModelListener extends BaseModelListener {
    void onSendMessageSuccess(long time);
}
