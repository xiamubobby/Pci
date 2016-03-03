package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;

import im.hua.library.base.mvp.impl.IBaseModel;

/**
 * Created by hua on 16/2/25.
 */
public interface ILoginModel extends IBaseModel {
    void loginSuccess(LoginEntity entity);
}
