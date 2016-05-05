package com.wonders.xlab.patient.mvp.model;

import java.io.File;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/5.
 * 市民云认证
 */
public interface AuthorizeModelContract {
    interface Callback extends BaseModelListener {
        void authorizeSuccess(String message);
    }

    interface Actions extends IBaseModel {
        void authorize(String patientId, String name, String idNo, File idPic, Callback callback);
    }
}
