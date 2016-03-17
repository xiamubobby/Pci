package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/23.
 */
public interface UserInfoModelListener extends BaseModelListener {
    void onReceiveUserInfoSuccess(UserInfoEntity entity);
}
