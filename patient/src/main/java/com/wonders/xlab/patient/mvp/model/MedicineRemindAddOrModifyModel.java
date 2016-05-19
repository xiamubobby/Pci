package com.wonders.xlab.patient.mvp.model;

import android.text.TextUtils;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.EmptyValueEntity;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by WZH on 16/5/5.
 */
public class MedicineRemindAddOrModifyModel extends PatientBaseModel implements MedicineRemindAddOrModifyModelContract.Actions {

    private MedicineRemindAPI mMedicineRemindAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public MedicineRemindAddOrModifyModel(MedicineRemindAPI medicineRemindAPI) {
        mMedicineRemindAPI = medicineRemindAPI;
    }


    @Override
    public void addOrModify(String patientId, MedicineRemindEditBody body, final MedicineRemindAddOrModifyModelContract.Callback callback) {
        Observable<Response<EmptyValueEntity>> observable;
        if (TextUtils.isEmpty(body.getId())) {
            observable = mMedicineRemindAPI.createMedicineRemind(patientId,body);
        } else {
            observable = mMedicineRemindAPI.modifyMedicineRemind(body);
        }
        request(observable, new Callback<EmptyValueEntity>() {
            @Override
            public void onSuccess(EmptyValueEntity response) {
                callback.addOrModifySuccess(response.getMessage());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
