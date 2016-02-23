package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/2/23.
 */
public interface IUserInfoModel extends IBaseModel {
    void onReceiveUserInfoSuccess(UserInfoEntity entity);
}
