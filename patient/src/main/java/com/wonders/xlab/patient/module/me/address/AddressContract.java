package com.wonders.xlab.patient.module.me.address;

import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.entity.EmptyValueEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/5.
 */
public interface AddressContract {
    interface Callback extends BaseModelListener {
        void saveAddressSuccess(EmptyValueEntity entity);
    }

    interface Model extends IBaseModel {
        void saveAddress(UserInfoBody body, String patientId, Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void saveAddressSuccess(String message);
    }

    interface Presenter extends IBasePresenter {
        void saveAddress(UserInfoBody body);
    }
}
