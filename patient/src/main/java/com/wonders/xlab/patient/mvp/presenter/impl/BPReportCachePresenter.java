package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BPReportBean;
import com.wonders.xlab.patient.mvp.presenter.IBPReportPresenter;

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
public class BPReportCachePresenter extends BasePresenter implements IBPReportPresenter {
    private BPReportCachePresenterListener listener;

    private Calendar beginTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();

    public BPReportCachePresenter(BPReportCachePresenterListener listener) {
        this.listener = listener;

        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE, 0);
        beginTime.set(Calendar.SECOND, 0);

        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        endTime.set(Calendar.SECOND, 59);
    }

    @Override
    public void getBPCacheList(String patientId) {

        RealmQuery<BPReportBean> query = XApplication.realm.where(BPReportBean.class)
                .equalTo("patientId",patientId)
                .between("recordTimeInMill", beginTime.getTimeInMillis(), endTime.getTimeInMillis());

        RealmResults<BPReportBean> results = query.findAll();
        results.sort("recordTimeInMill", Sort.DESCENDING);

        List<BPReportBean> bpReportBeanList = new ArrayList<>();
        for (BPReportBean bean : results) {
            bpReportBeanList.add(bean);
        }

        listener.showBPList(bpReportBeanList);
    }

    public interface BPReportCachePresenterListener extends BasePresenterListener {
        void showBPList(List<BPReportBean> beanList);
    }
}
