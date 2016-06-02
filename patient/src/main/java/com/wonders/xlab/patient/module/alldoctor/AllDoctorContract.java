package com.wonders.xlab.patient.module.alldoctor;

import com.wonders.xlab.patient.module.alldoctor.adapter.AllDoctorItemBean;
import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;

import java.util.ArrayList;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/5/5.
 */
public interface AllDoctorContract {
    interface Callback extends BaseModelListener {
        void onReceiveAllDoctorListSuccess(DoctorAllEntity valuesEntity);
    }

    interface Model extends IBaseModel {
        void getAllDoctorList(String patientId, int page, int pageSize, Callback callback);
    }

    interface ViewListener extends BasePagePresenterListener {
        void showAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);

        void appendAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);
    }

    interface Presenter extends IBasePresenter {
        void getAllDoctors(String patientId, boolean isRefresh);
    }
}
