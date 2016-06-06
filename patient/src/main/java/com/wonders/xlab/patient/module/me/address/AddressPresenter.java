package com.wonders.xlab.patient.module.me.address;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.EmptyValueEntity;
import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/5.
 */
public class AddressPresenter extends BasePresenter implements AddressContract.Presenter {
    @Inject
    AIManager mAIManager;
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
        mAddressModel.saveAddress(body, mAIManager.getPatientId(), new AddressContract.Callback() {
            @Override
            public void saveAddressSuccess(EmptyValueEntity entity) {
                mViewListener.hideLoading();
                mViewListener.saveAddressSuccess("保存成功!");
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mViewListener, code, message);
            }
        });
    }
}
