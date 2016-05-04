package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/4.
 */
public interface LoginModelContract {

    interface Callback extends BaseModelListener{
        void loginSuccess(LoginEntity entity);
    }

    interface Actions extends IBaseModel{
        void login(String tel, String password, Callback callback);
    }
}
