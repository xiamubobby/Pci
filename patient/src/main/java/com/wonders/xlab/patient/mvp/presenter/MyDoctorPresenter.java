package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.MyDoctorItemBean;
import com.wonders.xlab.patient.mvp.presenter.impl.IMyDoctorPresenter;

import java.util.ArrayList;
import java.util.Calendar;

import im.hua.library.base.mvp.BasePresenter;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/3/14.
 */
public class MyDoctorPresenter extends BasePresenter {
    private IMyDoctorPresenter mIMyDoctorPresenter;

    public MyDoctorPresenter(IMyDoctorPresenter iMyDoctorPresenter) {
        mIMyDoctorPresenter = iMyDoctorPresenter;
    }

    public void getMyDoctorList() {

        ArrayList<MyDoctorItemBean> doctorItemBeanArrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MyDoctorItemBean itemBean = new MyDoctorItemBean();
            if (i < 3) {
                itemBean.setHeaderId(MyDoctorItemBean.HEADER_ID_IN_SERVICE);
            } else {
                itemBean.setHeaderId(MyDoctorItemBean.HEADER_ID_OUT_OF_SERVICE);
            }
            itemBean.setDoctorGroupName("刘" + i);
            itemBean.setLatestChatMessage("可以复检了。");
            itemBean.setTimeStr(DateUtil.format(Calendar.getInstance().getTimeInMillis(), "HH:mm"));
            itemBean.setPortraitUrl(Constant.DEFAULT_PORTRAIT);

            doctorItemBeanArrayList.add(itemBean);
        }

        mIMyDoctorPresenter.showMyDoctorList(doctorItemBeanArrayList);
    }
}
