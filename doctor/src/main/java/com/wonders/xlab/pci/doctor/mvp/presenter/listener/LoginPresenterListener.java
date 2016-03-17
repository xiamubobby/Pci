package com.wonders.xlab.pci.doctor.mvp.presenter.listener;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/25.
 */
public interface LoginPresenterListener extends BasePresenterListener {
    void loginSuccess(String userId, String tel, String avatarUrl,String userName);
}
