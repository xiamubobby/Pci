package com.wonders.xlab.patient.module.home.top;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportRealmBean;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BSReportRealmBean;

import java.util.Calendar;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by hua on 16/5/17.
 */
public class HomeTopPresenter extends BasePresenter implements HomeTopPresenterContract.Actions {
    @Inject
    Realm mRealm;

    private HomeTopPresenterContract.ViewListener mListener;

    @Inject
    public HomeTopPresenter(HomeTopPresenterContract.ViewListener listener) {
        mListener = listener;
    }

    @Override
    public void getRecentBs() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -3);

        RealmResults<BSReportRealmBean> beanRealmResults = mRealm
                .where(BSReportRealmBean.class)
                .greaterThan("recordTimeInMill", calendar.getTimeInMillis())
                .equalTo("patientId", AIManager.getInstance().getPatientId())
                .findAllSorted("recordTimeInMill", Sort.DESCENDING);
        BSReportRealmBean first;
        if (beanRealmResults.size() > 0) {
            first = beanRealmResults.first();
        } else {
            first = null;
        }
        mListener.showRecentBs(first);
    }

    @Override
    public void getRecentBp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -3);

        RealmResults<BPReportRealmBean> beanRealmResults = mRealm
                .where(BPReportRealmBean.class)
                .greaterThan("recordTimeInMill", calendar.getTimeInMillis())
                .equalTo("patientId", AIManager.getInstance().getPatientId())
                .findAllSorted("recordTimeInMill", Sort.DESCENDING);
        BPReportRealmBean first;
        if (beanRealmResults.size() > 0) {
            first = beanRealmResults.first();
        } else {
            first = null;
        }
        mListener.showRecentBp(first);
    }
}
