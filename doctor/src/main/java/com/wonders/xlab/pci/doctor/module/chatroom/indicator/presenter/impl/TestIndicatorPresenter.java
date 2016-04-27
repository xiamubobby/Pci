package com.wonders.xlab.pci.doctor.module.chatroom.indicator.presenter.impl;

import com.wonders.xlab.pci.doctor.module.chatroom.indicator.adapter.bean.TestIndicatorBean;
import com.wonders.xlab.pci.doctor.module.chatroom.indicator.presenter.ITestIndicatorPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;
import im.hua.utils.DateUtil;

/**
 * Created by jimmy on 16/4/27.
 */
public class TestIndicatorPresenter extends BasePagePresenter implements ITestIndicatorPresenter {

    public TestIndicatorPresenterListener mListener;

    public TestIndicatorPresenter(TestIndicatorPresenterListener listener) {
        this.mListener = listener;
    }

    @Override
    public void getTestIndicatorList(String patientId, String doctorId) {
        List<TestIndicatorBean> testIndicatorBeanList = new ArrayList<>();
        List<TestIndicatorBean.IndicatorBean> indicatorList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            TestIndicatorBean.IndicatorBean bean = new TestIndicatorBean.IndicatorBean();
            bean.setItem("餐后血糖120分钟");
            bean.setResult("9.43");
            bean.setReferenceValue("3.9～7.8mmol/L");
            indicatorList.add(bean);
        }
        for (int i = 0; i < 5; i++) {
            TestIndicatorBean bean = new TestIndicatorBean();
            bean.getHospitalName().set("瑞金医院");
            bean.getDepartmentsName().set("心内科");
            bean.getTestTimeInStr().set(DateUtil.format(Calendar.getInstance().getTimeInMillis(), "yyyy-MM-dd"));
            bean.getIndicatorList().set(indicatorList);
            testIndicatorBeanList.add(bean);

        }
        mListener.showTestIndicatorList(testIndicatorBeanList);
    }

    public interface TestIndicatorPresenterListener extends BasePagePresenterListener {
        void showTestIndicatorList(List<TestIndicatorBean> testIndicatorBeanList);
    }
}
