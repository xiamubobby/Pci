package com.wonders.xlab.patient.mvp.presenter;


import com.wonders.xlab.patient.module.healthrecord.testindicator.adapter.bean.TestIndicatorBean;
import com.wonders.xlab.patient.mvp.entity.TestIndicatorEntity;
import com.wonders.xlab.patient.mvp.model.TestIndicatorModel;
import com.wonders.xlab.patient.mvp.model.TestIndicatorModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by jimmy on 16/4/27.
 */
public class TestIndicatorPresenter extends BasePagePresenter implements TestIndicatorPresenterContract.Actions {

    public TestIndicatorPresenterContract.ViewListener mTestIndicatorPresenter;
    public TestIndicatorModelContract.Actions mTestIndicatorModel;

    @Inject
    public TestIndicatorPresenter(TestIndicatorPresenterContract.ViewListener iTestIndicatorPresenter, TestIndicatorModel testIndicatorModel) {
        this.mTestIndicatorPresenter = iTestIndicatorPresenter;
        this.mTestIndicatorModel = testIndicatorModel;
        addModel(mTestIndicatorModel);
    }

    @Override
    public void getTestIndicatorList(String patientId, boolean isRefresh) {
        mTestIndicatorPresenter.showLoading("");
        if (isRefresh) {
            mCurrentIndex = 0;
            mIsFirst = true;
            mIsLast = false;
        }

        mTestIndicatorModel.getTestIndicatorList(patientId, getNextPageIndex(), new TestIndicatorModelContract.Callback() {
            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mTestIndicatorPresenter, code, message);
            }

            @Override
            public void getTestIndicatorListSuccess(TestIndicatorEntity entity) {
                mTestIndicatorPresenter.hideLoading();
                TestIndicatorEntity.RetValuesBean.DataBean data = entity.getRet_values().getData();
                updatePageInfo(Integer.parseInt(data.getMore_params().getFlag()), data.isMore(), !data.isMore());

                List<TestIndicatorEntity.RetValuesBean.DataBean.ContentBean> contentBean = entity.getRet_values().getData().getContent();
                List<TestIndicatorBean> testIndicatorBeanList = new ArrayList<>();
                for (TestIndicatorEntity.RetValuesBean.DataBean.ContentBean content : contentBean) {
                    TestIndicatorBean bean = new TestIndicatorBean();
                    bean.setTestTimeInStr(content.getDate());
                    bean.setHospitalName(content.getHospital_name());
                    bean.setDepartmentsName(content.getOffice_type());
                    List<TestIndicatorBean.IndicatorBean> indicatorList = new ArrayList<>();
                    for (TestIndicatorEntity.RetValuesBean.DataBean.ContentBean.ListBean list : content.getList()) {
                        TestIndicatorBean.IndicatorBean itemBean = new TestIndicatorBean.IndicatorBean();
                        itemBean.setItem(list.getItem_name());
                        itemBean.setResult(list.getItem_value());
                        itemBean.setReferenceValue(list.getRefer_value());
                        indicatorList.add(itemBean);
                    }
                    bean.setIndicatorList(indicatorList);
                    testIndicatorBeanList.add(bean);
                }
                if (mCurrentIndex > 1) {
                    if (testIndicatorBeanList.size() == 0) {
                        mTestIndicatorPresenter.showReachTheLastPageNotice("");
                        return;
                    }
                    mTestIndicatorPresenter.appendTestIndicatorList(testIndicatorBeanList);
                    return;
                }
                mTestIndicatorPresenter.showTestIndicatorList(testIndicatorBeanList);

            }
        });
    }

}
