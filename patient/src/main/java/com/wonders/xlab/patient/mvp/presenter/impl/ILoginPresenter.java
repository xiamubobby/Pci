package com.wonders.xlab.patient.mvp.presenter.impl;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public interface ILoginPresenter extends IBasePresenter {
    void loginSuccess(String patientId,String tel,String portraitUrl,String patientName);
}
