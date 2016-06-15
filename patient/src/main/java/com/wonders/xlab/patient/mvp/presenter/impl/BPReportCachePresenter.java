package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportRealmBean;
import com.wonders.xlab.patient.mvp.presenter.IBPReportPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by hua on 16/3/21.
 */
public class BPReportCachePresenter extends BasePresenter implements IBPReportPresenter {
    private BPReportCachePresenterListener listener;

    public BPReportCachePresenter(BPReportCachePresenterListener listener) {
        this.listener = listener;
    }

    @Override
    public void getBPCacheList(String patientId) {

        RealmQuery<BPReportRealmBean> query = XApplication.realm.where(BPReportRealmBean.class)
                .equalTo("patientId", patientId)
                .between("recordTimeInMill", DateUtil.getStartTimeInMillOfToday(), DateUtil.getEndTimeInMillOfToday());

        RealmResults<BPReportRealmBean> results = query.findAll().sort("recordTimeInMill", Sort.DESCENDING);

        if (results.size() <= 0) {
            listener.showEmptyView();
            return;
        }
        List<BPReportRealmBean> bpReportRealmBeanList = new ArrayList<>();
        for (BPReportRealmBean bean : results) {
            bpReportRealmBeanList.add(bean);
        }

        listener.showBPList(bpReportRealmBeanList);
    }

    public interface BPReportCachePresenterListener extends BasePresenterListener {
        void showBPList(List<BPReportRealmBean> beanList);

        void showEmptyView();
    }
}
