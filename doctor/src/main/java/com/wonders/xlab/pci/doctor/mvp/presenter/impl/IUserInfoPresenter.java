package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.userinfo.bean.UserInfoBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/2/23.
 */
public interface IUserInfoPresenter extends IBasePresenter {
    void showUserInfo(List<UserInfoBean> userInfoBeanList);
}