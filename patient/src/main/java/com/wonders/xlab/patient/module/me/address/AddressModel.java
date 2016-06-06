package com.wonders.xlab.patient.module.me.address;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.UserInfoAPI;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.EmptyValueEntity;

/**
 * Created by wzh 16/6/3.
 */
public class AddressModel extends PatientBaseModel implements AddressContract.Model {

    private UserInfoAPI mAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public AddressModel(UserInfoAPI userInfoAPI) {
        mAPI = userInfoAPI;
    }


    @Override
    public void saveAddress(UserInfoBody body, String patientId, final AddressContract.Callback callback) {
        request(mAPI.modifyUserInfo(body, patientId), new Callback<EmptyValueEntity>() {


            @Override
            public void onSuccess(EmptyValueEntity response) {
                callback.saveAddressSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
