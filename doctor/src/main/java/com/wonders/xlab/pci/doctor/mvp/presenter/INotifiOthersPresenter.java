package com.wonders.xlab.pci.doctor.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/18.
 */
public interface INotifiOthersPresenter extends IBasePresenter{
    void getOthersNotifi(boolean isRefresh, String doctorId);
}
