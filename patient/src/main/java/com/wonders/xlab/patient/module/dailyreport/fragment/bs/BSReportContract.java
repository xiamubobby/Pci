package com.wonders.xlab.patient.module.dailyreport.fragment.bs;

import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BSReportRealmBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/16.
 */
public interface BSReportContract {
    interface ViewListener extends BasePresenterListener {
        void showBSList(List<BSReportRealmBean> beanList);

        void showEmptyView();
    }

    interface Presenter extends IBasePresenter {
        /**
         * 查询属于当前患者id的今天的血压数据
         * @param patientId
         */
        void getBSCacheList(String patientId);
    }
}
