package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.main.home.dailyreport.adapter.bean.BPReportBean;
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

        RealmQuery<BPReportBean> query = XApplication.realm.where(BPReportBean.class)
                .equalTo("patientId", patientId)
                .between("recordTimeInMill", DateUtil.getStartTimeInMillOfToday(), DateUtil.getEndTimeInMillOfToday());

        RealmResults<BPReportBean> results = query.findAll();
        results.sort("recordTimeInMill", Sort.DESCENDING);

        if (results.size() <= 0) {
            listener.showEmptyView();
            return;
        }
        List<BPReportBean> bpReportBeanList = new ArrayList<>();
        for (BPReportBean bean : results) {
            bpReportBeanList.add(bean);
        }

        listener.showBPList(bpReportBeanList);
    }

    public interface BPReportCachePresenterListener extends BasePresenterListener {
        void showBPList(List<BPReportBean> beanList);

        void showEmptyView();
    }
}
