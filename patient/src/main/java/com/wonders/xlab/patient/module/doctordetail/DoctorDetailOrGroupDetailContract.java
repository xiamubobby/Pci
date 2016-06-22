package com.wonders.xlab.patient.module.doctordetail;

import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorBasicInfoBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.mvp.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.mvp.entity.DoctorGroupDetailEntity;
import com.wonders.xlab.patient.mvp.entity.GenerateOrderPaymentEntity;

import java.util.ArrayList;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/22.
 */
public interface DoctorDetailOrGroupDetailContract {

    interface Callback extends BaseModelListener {
        void onReceiveDoctorDetailSuccess(DoctorDetailEntity doctorDetailEntity);

        void onReceiveDoctorGroupDetailSuccess(DoctorGroupDetailEntity groupDetailEntity);

        void onOrderPackageServiceSuccess(GenerateOrderPaymentEntity paymentEntity);
    }

    interface Model extends IBaseModel {
        void getDoctorDetailInfo(String patientId, String doctorId,Callback callback);

        void getDoctorGroupDetailInfo(String patientId, String ownerId,Callback callback);

        void orderPackage(String patientId, String packageId, String paymentChannel,Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void showBasicInfo(DoctorBasicInfoBean basicInfoBean);

        void showPackageList(ArrayList<DoctorDetailPackageBean> packageList);

        void showGroupMemberList(ArrayList<DoctorDetailGroupMemberBean> groupMemberList);

        void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList);

        void showMemberOrGroupOfDoctorRV();

        void hideMemberOrGroupOfDoctorRV();

        void buyFreeSuccess();

        void startPayment(String charge);

        void refreshView();

        /**
         * 小组成员有变动，服务过期，需要重新获取列表，然后进入重新购买
         *
         * @param message
         */
        void doctorGroupExpired(String message);
    }

    interface Presenter extends IBasePresenter {
        void fetchDoctorDetailInfo(String patientId, String doctorId);

        void orderPackage(String patientId, String packageId, String paymentChannel);

        void fetchDoctorGroupDetailInfo(String patientId, String ownerId);

    }
}
