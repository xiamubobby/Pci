package com.wonders.xlab.patient.module.dailyrecord.mvn.presenter.impl;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public interface IAddRecordPresenter extends IBasePresenter {
    void onSaveRecordSuccess(String message);
}
