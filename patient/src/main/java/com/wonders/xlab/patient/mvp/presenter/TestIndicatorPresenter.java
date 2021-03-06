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

    public TestIndicatorPresenterContract.ViewListener mViewListener;
    public TestIndicatorModelContract.Actions mTestIndicatorModel;

    @Inject
    public TestIndicatorPresenter(TestIndicatorPresenterContract.ViewListener iTestIndicatorPresenter, TestIndicatorModel testIndicatorModel) {
        this.mViewListener = iTestIndicatorPresenter;
        this.mTestIndicatorModel = testIndicatorModel;
        addModel(mTestIndicatorModel);
    }

    @Override
    public void getTestIndicatorList(String patientId, boolean isRefresh) {
        /*if (isRefresh) {
            mCurrentIndex = 0;
            mIsFirst = true;
            mIsLast = false;
        }*/

        mViewListener.showLoading("");
        mTestIndicatorModel.getTestIndicatorList(patientId, 0, new TestIndicatorModelContract.Callback() {
            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mViewListener, code, message);
            }

            @Override
            public void getTestIndicatorListSuccess(TestIndicatorEntity entity) {
                mViewListener.hideLoading();
                TestIndicatorEntity.RetValuesBean retValues = entity.getRet_values();
                if (null == retValues) {
                    mViewListener.showEmptyView("");
                    return;
                }
                TestIndicatorEntity.RetValuesBean.DataBean data = retValues.getData();
                if (null == data) {
                    mViewListener.showEmptyView("");
                    return;
                }

                List<TestIndicatorEntity.RetValuesBean.DataBean.ContentBean> contentBeanList =  data.getContent();
                if (null == contentBeanList) {
                    mViewListener.showEmptyView("");
                    return;
                }
                List<TestIndicatorBean> testIndicatorBeanList = new ArrayList<>();
                for (TestIndicatorEntity.RetValuesBean.DataBean.ContentBean content : contentBeanList) {
                    TestIndicatorBean bean = new TestIndicatorBean();
                    bean.setTestTimeInStr(content.getDate());
                    bean.setHospitalName(content.getHospital_name());
                    bean.setDepartmentsName(content.getReport_type());
                    bean.setOfficeType(content.getOffice_type());
                    List<TestIndicatorBean.IndicatorBean> indicatorList = new ArrayList<>();
                    for (TestIndicatorEntity.RetValuesBean.DataBean.ContentBean.ListBean list : content.getList()) {
                        TestIndicatorBean.IndicatorBean itemBean = new TestIndicatorBean.IndicatorBean();
                        itemBean.setItem(list.getItem_name());
                        itemBean.setStatus(list.getItem_status());
                        itemBean.setResult(list.getItem_value());
                        itemBean.setReferenceValue(list.getRefer_value());
                        indicatorList.add(itemBean);
                    }
                    bean.setIndicatorList(indicatorList);
                    testIndicatorBeanList.add(bean);
                }
                if (mCurrentIndex > 1) {
                    if (testIndicatorBeanList.size() == 0) {
                        mViewListener.showReachTheLastPageNotice("");
                        return;
                    }
                    mViewListener.appendTestIndicatorList(testIndicatorBeanList);
                    return;
                }
                mViewListener.showTestIndicatorList(testIndicatorBeanList);

            }
        });
    }

}
