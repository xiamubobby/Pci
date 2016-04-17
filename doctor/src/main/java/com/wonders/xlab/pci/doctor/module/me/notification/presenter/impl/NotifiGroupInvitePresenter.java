package com.wonders.xlab.pci.doctor.module.me.notification.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiGroupInviteBean;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.INotifiGroupInvitePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/4/14.
 */
public class NotifiGroupInvitePresenter extends BasePagePresenter implements INotifiGroupInvitePresenter {
    private NotifiGroupInvitePresenterListener mListener;

    public NotifiGroupInvitePresenter(NotifiGroupInvitePresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getInviteNotifications(String doctorId) {
        mListener.hideLoading();

        List<NotifiGroupInviteBean> inviteBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NotifiGroupInviteBean bean = new NotifiGroupInviteBean();
            bean.setId(String.valueOf(i));
            bean.setOwnerName("刘" + i);
            bean.setOwnerJobTitle("副主任医师");
            bean.setOwnerDepartment("心内科");
            bean.setGroupName("刘" + i + "医师小组");
            bean.setOwnerHospital("万达全程");
            bean.setGroupDesc("简介");
            bean.setRecordTime(Calendar.getInstance().getTimeInMillis());
            inviteBeanList.add(bean);
        }

        mListener.showInviteNotifications(inviteBeanList);
    }

    public interface NotifiGroupInvitePresenterListener extends BasePagePresenterListener {
        void showInviteNotifications(List<NotifiGroupInviteBean> inviteBeanList);
    }
}
