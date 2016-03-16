package com.wonders.xlab.patient.module.auth.login.mvn.model.impl;

import com.wonders.xlab.patient.mvp.entity.LoginEntity;

import im.hua.library.base.mvp.impl.IBaseModel;

/**
 * Created by hua on 16/3/16.
 */
public interface ILoginModel extends IBaseModel{
    void loginSuccess(LoginEntity.RetValuesEntity value);
}
