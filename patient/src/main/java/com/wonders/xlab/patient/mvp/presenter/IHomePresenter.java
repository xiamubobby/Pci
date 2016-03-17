package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 */
public interface IHomePresenter extends IBasePresenter {
    String getTopData(String patientId);
}
