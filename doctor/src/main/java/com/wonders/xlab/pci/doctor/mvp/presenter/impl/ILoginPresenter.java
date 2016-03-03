package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 16/2/25.
 */
public interface ILoginPresenter extends IBasePresenter {
    void loginSuccess(String userId, String tel, String avatarUrl,String userName);
}
