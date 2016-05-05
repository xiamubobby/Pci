package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.RegisterEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/5.
 */
public interface RegisterModelContract {
    interface Callback extends BaseModelListener{
        void onRegisterSuccess(RegisterEntity entity);
    }

    interface Actions extends IBaseModel{
        void register(String tel,String password,String capture,Callback callback);
    }
}
