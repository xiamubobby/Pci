package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.patientinfo.indicator.adapter.bean.TestIndicatorBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by jimmy on 16/5/5.
 */
public interface TestIndicatorPresenterContract {

    interface ViewListener extends BasePagePresenterListener {

        void showTestIndicatorList(List<TestIndicatorBean> testIndicatorBeanList);

        void appendTestIndicatorList(List<TestIndicatorBean> testIndicatorBeanList);
    }

    interface Actions extends IBasePresenter {
        void getTestIndicatorList(String patientId, boolean isRefresh);
    }
}
