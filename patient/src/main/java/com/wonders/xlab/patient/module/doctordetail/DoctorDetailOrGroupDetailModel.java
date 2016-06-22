package com.wonders.xlab.patient.module.doctordetail;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.api.OrderPackageServiceAPI;
import com.wonders.xlab.patient.mvp.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.mvp.entity.DoctorGroupDetailEntity;
import com.wonders.xlab.patient.mvp.entity.GenerateOrderPaymentEntity;

import javax.inject.Inject;

/**
 * Created by WZH on 16/6/22.
 */
public class DoctorDetailOrGroupDetailModel extends PatientBaseModel implements DoctorDetailOrGroupDetailContract.Model {
    private DoctorAPI mDoctorAPI;
    private OrderPackageServiceAPI mPackageServiceAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public DoctorDetailOrGroupDetailModel(DoctorAPI doctorAPI, OrderPackageServiceAPI packageServiceAPI) {
        mDoctorAPI = doctorAPI;
        mPackageServiceAPI = packageServiceAPI;
    }

    @Override
    public void getDoctorDetailInfo(String patientId, String doctorId, final DoctorDetailOrGroupDetailContract.Callback callback) {
        request(mDoctorAPI.getDoctorDetailInfo(patientId, doctorId), new Callback<DoctorDetailEntity>() {
            @Override
            public void onSuccess(DoctorDetailEntity response) {
                callback.onReceiveDoctorDetailSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }

    @Override
    public void getDoctorGroupDetailInfo(String patientId, String ownerId, final DoctorDetailOrGroupDetailContract.Callback callback) {
        request(mDoctorAPI.getDoctorGroupDetailInfo(patientId, ownerId), new Callback<DoctorGroupDetailEntity>() {
            @Override
            public void onSuccess(DoctorGroupDetailEntity response) {
                callback.onReceiveDoctorGroupDetailSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }

    @Override
    public void orderPackage(String patientId, String packageId, String paymentChannel, final DoctorDetailOrGroupDetailContract.Callback callback) {
        request(mPackageServiceAPI.generateOrderPayment(patientId, packageId, paymentChannel), new Callback<GenerateOrderPaymentEntity>() {
            @Override
            public void onSuccess(GenerateOrderPaymentEntity response) {
                callback.onOrderPackageServiceSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
