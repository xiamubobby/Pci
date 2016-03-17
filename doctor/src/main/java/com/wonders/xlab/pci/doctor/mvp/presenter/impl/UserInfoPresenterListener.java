package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.userinfo.bean.UserInfoBean;

import java.util.List;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/23.
 */
public interface UserInfoPresenterListener extends BasePresenterListener {
    void showUserInfo(List<UserInfoBean> userInfoBeanList);
}