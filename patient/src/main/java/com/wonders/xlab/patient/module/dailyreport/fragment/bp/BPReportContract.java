package com.wonders.xlab.patient.module.dailyreport.fragment.bp;

import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportRealmBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/16.
 */
public interface BPReportContract {
    interface Presenter extends IBasePresenter {
        /**
         * 查询属于当前患者id的今天的血压数据
         * @param patientId
         */
        void getBPCacheList(String patientId);
    }

    interface ViewListener extends BasePresenterListener {
        void showBPList(List<BPReportRealmBean> beanList);

        void showEmptyView();
    }
}
