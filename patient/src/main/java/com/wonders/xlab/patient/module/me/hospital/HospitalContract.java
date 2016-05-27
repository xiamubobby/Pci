package com.wonders.xlab.patient.module.me.hospital;

import com.wonders.xlab.patient.module.me.hospital.adapter.HospitalBean;
import com.wonders.xlab.patient.mvp.entity.HospitalAllEntity;

import java.util.List;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/27.
 */
public interface HospitalContract {
    interface Callback extends BaseModelListener {
        void onReceiveHospitalsSuccess(HospitalAllEntity entity);
    }

    interface Model extends IBaseModel {
        void getAllHospitals(Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void showHospitalList(List<HospitalBean> dicEntityList);
    }

    interface Presenter extends IBasePresenter {
        void getAllHospitals();
    }
}
