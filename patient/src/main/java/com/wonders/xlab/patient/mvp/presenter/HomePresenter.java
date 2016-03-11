package com.wonders.xlab.patient.mvp.presenter;

import javax.inject.Inject;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/3/11.
 */
public class HomePresenter extends BasePresenter {
    @Inject
    public HomePresenter() {
    }

    public String getTopData() {
        return "success";
    }
}
