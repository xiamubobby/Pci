package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.alldoctor.adapter.AllDoctorItemBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/5/5.
 */
public interface AllDoctorPresenterContract {
    interface ViewListener extends BasePagePresenterListener{
        void showAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);

        void appendAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);
    }

    interface Actions extends IBasePresenter{
        void getAllDoctors(String patientId, boolean isRefresh);
    }
}
