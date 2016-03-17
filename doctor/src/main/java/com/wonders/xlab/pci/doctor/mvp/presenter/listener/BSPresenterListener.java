package com.wonders.xlab.pci.doctor.mvp.presenter.listener;

import com.wonders.xlab.pci.doctor.module.bs.bean.BSBean;

import java.util.List;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/22.
 */
public interface BSPresenterListener extends BasePresenterListener {
    void showBloodPressureList(List<BSBean> bsBeanList);
}
