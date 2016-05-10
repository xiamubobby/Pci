package com.wonders.xlab.patient.mvp.model;


import com.wonders.xlab.patient.mvp.entity.TestIndicatorEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by jimmy on 16/5/5.
 */
public interface TestIndicatorModelContract {

    interface Callback extends BaseModelListener {
        void getTestIndicatorListSuccess(TestIndicatorEntity entity);
    }

    interface Actions extends IBaseModel {
        void getTestIndicatorList(String patient, int pageIndex, Callback callback);
    }
}
