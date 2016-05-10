package com.wonders.xlab.patient.mvp.presenter;


import com.wonders.xlab.patient.module.healthrecord.surgicalhistory.adapter.bean.SurgicalHistoryBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/5/5.
 */
public interface SurgicalHistoryPresenterContract {
    interface ViewListener extends BasePagePresenterListener {
        void showSurgicalHistoryList(List<SurgicalHistoryBean> surgicalHistoryBeanList);

        void appendSurgicalHistoryList(List<SurgicalHistoryBean> surgicalHistoryBeanList);
    }

    interface Actions extends IBasePresenter {
        void getSurgicalHistory(String patientId, boolean isRefresh);
    }
}
