package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.mvp.presenter.IHomePresenter;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/3/11.
 */
public class HomePresenter extends BasePresenter implements IHomePresenter {
    public HomePresenter() {
    }

    @Override
    public String getTopData(String patientId) {
        return "success";
    }
}