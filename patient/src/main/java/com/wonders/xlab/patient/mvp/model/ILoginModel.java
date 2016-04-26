package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/26.
 */
public interface ILoginModel extends IBaseModel{
    void login(String tel, String password);
}
