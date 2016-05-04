package com.wonders.xlab.pci.doctor.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/20.
 */
public interface IBSPresenter extends IBasePresenter {
    void getBSList(String patientId, boolean isRefresh);
}
