package com.wonders.xlab.patient.mvp.presenter.listener;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/16.
 */
public interface LoginPresenterListenerListener extends BasePresenterListener {
    void loginSuccess(String patientId,String tel,String portraitUrl,String patientName);
}
