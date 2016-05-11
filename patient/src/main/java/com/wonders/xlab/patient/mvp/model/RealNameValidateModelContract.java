package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.RealNameValidateEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/10.
 */
public interface RealNameValidateModelContract {
    interface Callback extends BaseModelListener {
        void onReceiveValidateResultSuccess(RealNameValidateEntity entity);
    }

    /**
     * ret_code
     * 0是未实名
     * UnAudit,
     * 1是已实名
     * Audited,
     * 2是审核中
     * Auditing,
     * 3是审核失败
     * Failure
     */
    interface Actions extends IBaseModel {
        void getValidateResult(String patientId, Callback callback);
    }
}
