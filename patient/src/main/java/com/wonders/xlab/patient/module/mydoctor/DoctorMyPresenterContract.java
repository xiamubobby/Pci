package com.wonders.xlab.patient.module.mydoctor;

import com.wonders.xlab.patient.module.mydoctor.adapter.MyDoctorItemBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/3/17.
 */
public interface DoctorMyPresenterContract {
    interface ViewListener extends BasePagePresenterListener{
        void showMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);

        void appendMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);
    }

    interface Actions extends IBasePresenter{
        void getMyDoctors(String patientId, boolean isRefresh);
    }
}
