package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/1.
 */
public interface IBloodSugarPresenter extends IBasePresenter {
    void getBSList(String userId);
}
