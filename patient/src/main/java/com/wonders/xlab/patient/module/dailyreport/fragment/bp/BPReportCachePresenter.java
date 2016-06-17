package com.wonders.xlab.patient.module.dailyreport.fragment.bp;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportRealmBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.utils.DateUtil;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by hua on 16/3/21.
 */
public class BPReportCachePresenter extends BasePresenter implements BPReportContract.Presenter {
    private BPReportContract.ViewListener mViewListener;
    @Inject
    public BPReportCachePresenter(BPReportContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Override
    public void getBPCacheList(String patientId) {

        RealmQuery<BPReportRealmBean> query = XApplication.realm.where(BPReportRealmBean.class)
                .equalTo("patientId", patientId)
                .between("recordTimeInMill", DateUtil.getStartTimeInMillOfToday(), DateUtil.getEndTimeInMillOfToday());

        RealmResults<BPReportRealmBean> results = query.findAll().sort("recordTimeInMill", Sort.DESCENDING);

        if (results.size() <= 0) {
            mViewListener.showEmptyView();
            return;
        }
        List<BPReportRealmBean> bpReportRealmBeanList = new ArrayList<>();
        for (BPReportRealmBean bean : results) {
            bpReportRealmBeanList.add(bean);
        }

        mViewListener.showBPList(bpReportRealmBeanList);
    }

}
