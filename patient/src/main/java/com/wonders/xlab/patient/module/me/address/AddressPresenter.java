package com.wonders.xlab.patient.module.me.address;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/5.
 */
public class AddressPresenter extends BasePresenter implements AddressContract.Presenter {
    private AddressContract.ViewListener mViewListener;
    private AddressContract.Model mAddressModel;

    @Inject
    public AddressPresenter(AddressContract.ViewListener viewListener, AddressModel addressModel) {
        mViewListener = viewListener;
        mAddressModel = addressModel;
    }


    @Override
    public void saveAddress(UserInfoBody body) {
        mViewListener.showLoading("正在保存，请稍候...");
        mAddressModel.saveAddress(body, AIManager.getInstance().getPatientId(), new AddressContract.Callback() {
            @Override
            public void saveAddressSuccess(String message) {
                mViewListener.hideLoading();
                mViewListener.saveAddressSuccess(message);
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mViewListener, code, message);
            }
        });
    }
}
