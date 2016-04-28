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
        List<TestIndicatorBean.IndicatorBean> indicatorList1 = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            TestIndicatorBean.IndicatorBean bean = new TestIndicatorBean.IndicatorBean();
            switch (i) {
                case 0:
                    bean.setItem("餐后血糖120分钟");
                    bean.setResult("9.43");
                    bean.setReferenceValue("3.9～7.8mmol/L");
                    break;
            }

            indicatorList.add(bean);
        }
        for (int i = 0; i < 3; i++) {
            TestIndicatorBean.IndicatorBean bean = new TestIndicatorBean.IndicatorBean();
            switch (i) {
                case 0:
                    bean.setItem("血清铁");
                    bean.setResult("3.3");
                    bean.setReferenceValue("9.0~27.0umol/L");
                    break;
                case 1:
                    bean.setItem("铁饱和度");
                    bean.setResult("21.3");
                    bean.setReferenceValue("20~50%");
                    break;
                case 2:
                    bean.setItem("总铁结合力");
                    bean.setResult("105.1");
                    bean.setReferenceValue("46.7~85.6umol/L");
                    break;
            }

            indicatorList1.add(bean);
        }
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 5; i++) {
            TestIndicatorBean bean = new TestIndicatorBean();
            bean.getHospitalName().set("中山医院");
            if (i == 0) {
                bean.getIndicatorList().set(indicatorList);
                bean.getDepartmentsName().set("内分泌代谢科");
            } else {
                bean.getIndicatorList().set(indicatorList1);
                bean.getDepartmentsName().set("血液检测");
            }
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            bean.getTestTimeInStr().set(DateUtil.format(calendar.getTimeInMillis(), "yyyy-MM-dd"));
            testIndicatorBeanList.add(bean);

        }
        mListener.showTestIndicatorList(testIndicatorBeanList);
    }

    public interface TestIndicatorPresenterListener extends BasePagePresenterListener {
        void showTestIndicatorList(List<TestIndicatorBean> testIndicatorBeanList);
    }
}
