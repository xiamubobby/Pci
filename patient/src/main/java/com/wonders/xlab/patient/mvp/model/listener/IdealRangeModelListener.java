package com.wonders.xlab.patient.mvp.model.listener;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/16.
 */
public interface IdealRangeModelListener extends BaseModelListener {
    void onReceiveIdealRangeSuccess(String message);
}
