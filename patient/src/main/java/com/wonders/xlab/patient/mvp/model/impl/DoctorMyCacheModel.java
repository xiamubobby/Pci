package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.mvp.model.IDoctorMyModel;
import com.wonders.xlab.patient.data.realm.MyDoctorRealm;

import im.hua.library.base.mvp.listener.BaseModelListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorMyCacheModel implements IDoctorMyModel {

    private DoctorMyCacheModelListener mDoctorMyCacheModelListener;

    public DoctorMyCacheModel(DoctorMyCacheModelListener doctorMyCacheModelListener) {
        mDoctorMyCacheModelListener = doctorMyCacheModelListener;
    }

    @Override
    public void getMyDoctors(String patientId, int page, int pageSize) {

        RealmResults<MyDoctorRealm> realmResults = XApplication.realm.where(MyDoctorRealm.class)
                .equalTo("patientId", patientId).findAll();
        realmResults.sort("recordTimeInMill", Sort.DESCENDING);

        mDoctorMyCacheModelListener.onReceiveMyDoctorCacheSuccess(realmResults);
    }

    @Override
    public void cancel() {

    }

    public interface DoctorMyCacheModelListener extends BaseModelListener {
        void onReceiveMyDoctorCacheSuccess(RealmResults<MyDoctorRealm> myDoctorRealms);
    }
}
