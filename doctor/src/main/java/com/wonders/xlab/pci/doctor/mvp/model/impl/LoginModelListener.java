package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/25.
 */
public interface LoginModelListener extends BaseModelListener {
    void loginSuccess(LoginEntity entity);
}
