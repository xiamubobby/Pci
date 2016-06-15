package com.wonders.xlab.patient.module.mydoctor;

import com.wonders.xlab.patient.module.mydoctor.adapter.MyDoctorItemBean;
import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;

import java.util.ArrayList;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by WZH on 16/6/15.
 */
public interface DoctorMyContract {
    interface Callback extends BaseModelListener {
        void onReceiveMyDoctorListSuccess(DoctorMyEntity.RetValuesEntity valuesEntity);
    }

    interface Model extends IBaseModel {
        void getMyDoctors(String patientId, int page, int pageSize, Callback callback);
    }

    interface ViewListener extends BasePagePresenterListener {
        void showMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);

        void appendMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);
    }

    interface Presenter extends IBasePresenter {
        void getMyDoctors(String patientId, boolean isRefresh);
    }
}
