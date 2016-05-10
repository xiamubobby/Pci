package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.entity.EmptyValueEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/10.
 */
public interface RealNameValidateModelContract {
    interface Callback extends BaseModelListener {
        void onReceiveValidateResultSuccess(EmptyValueEntity entity);
    }

    interface Actions extends IBaseModel {
        void getValidateResult(String patientId, Callback callback);
    }
}
