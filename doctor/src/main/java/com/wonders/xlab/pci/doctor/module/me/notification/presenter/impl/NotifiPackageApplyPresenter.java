package com.wonders.xlab.pci.doctor.module.me.notification.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiPackageApplyBean;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.INotifiPackageApplyPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/4/17.
 */
public class NotifiPackageApplyPresenter extends BasePagePresenter implements INotifiPackageApplyPresenter {
    private NotifiPackageApplyPresenterListener mListener;

    public NotifiPackageApplyPresenter(NotifiPackageApplyPresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getPackageApplyNotifications(String doctorId) {
        mListener.showLoading("");

        List<NotifiPackageApplyBean> applyBeanList = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            NotifiPackageApplyBean bean = new NotifiPackageApplyBean();
            bean.id.set(String.valueOf(i));
            bean.patientName.set("刘"+i);
            bean.patientGender.set("男");
            bean.patientAge.set("12");
            bean.patientSurgeryHospital.set("瑞金医院");
            bean.applyPackageName.set("免费随访");
            bean.recordTimeInMill.set(Calendar.getInstance().getTimeInMillis());
            applyBeanList.add(bean);
        }

        mListener.hideLoading();
        mListener.showPackageApplyList(applyBeanList);
    }

    public interface NotifiPackageApplyPresenterListener extends BasePagePresenterListener{
        void showPackageApplyList(List<NotifiPackageApplyBean> applyBeanList);
    }
}
