package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BSReportRealmBean;
import com.wonders.xlab.patient.mvp.presenter.IBSReportPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by hua on 16/3/21.
 */
public class BSReportCachePresenter extends BasePresenter implements IBSReportPresenter {
    private BPReportCachePresenterListener listener;

    private Calendar beginTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();

    public BSReportCachePresenter(BPReportCachePresenterListener listener) {
        this.listener = listener;

        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE, 0);
        beginTime.set(Calendar.SECOND, 0);

        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        endTime.set(Calendar.SECOND, 59);
    }

    @Override
    public void getBSCacheList(String patientId) {

        RealmQuery<BSReportRealmBean> query = XApplication.realm.where(BSReportRealmBean.class)
                .equalTo("patientId", patientId)
                .between("recordTimeInMill", beginTime.getTimeInMillis(), endTime.getTimeInMillis());

        RealmResults<BSReportRealmBean> results = query.findAll().sort("recordTimeInMill", Sort.DESCENDING);//排序单独操作无效，排序操作是异步的
//        results.sort("recordTimeInMill", Sort.DESCENDING);
        if (results.size() <= 0) {
            listener.showEmptyView();
            return;
        }

        List<BSReportRealmBean> BSReportRealmBeanList = new ArrayList<>();
        for (BSReportRealmBean bean : results) {
            BSReportRealmBeanList.add(bean);
        }

        listener.showBSList(BSReportRealmBeanList);
    }

    public interface BPReportCachePresenterListener extends BasePresenterListener {
        void showBSList(List<BSReportRealmBean> beanList);

        void showEmptyView();
    }
}
