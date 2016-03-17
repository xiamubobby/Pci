package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.bs.bean.BSBean;

import java.util.List;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/22.
 */
public interface IBSPresenterListener extends BasePresenterListener {
    void showBloodPressureList(List<BSBean> bsBeanList);
}
