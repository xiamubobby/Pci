package com.wonders.xlab.patient.module.home.top;

import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportRealmBean;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BSReportRealmBean;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/5/17.
 */
public interface HomeTopPresenterContract {
    interface ViewListener {
        void showRecentBs(BSReportRealmBean bean);

        void showRecentBp(BPReportRealmBean bean);
    }

    interface Actions extends IBasePresenter {
        void getRecentBs();

        void getRecentBp();
    }
}
