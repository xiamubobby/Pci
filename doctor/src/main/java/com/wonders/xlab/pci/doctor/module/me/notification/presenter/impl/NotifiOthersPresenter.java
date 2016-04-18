package com.wonders.xlab.pci.doctor.module.me.notification.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiOthersBean;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.INotifiOthersPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/4/18.
 */
public class NotifiOthersPresenter extends BasePagePresenter implements INotifiOthersPresenter {
    private NotifiOthersPresenterListener mListener;

    public NotifiOthersPresenter(NotifiOthersPresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getOthersNotifi(String doctorId) {
        mListener.showLoading("");

        List<NotifiOthersBean> notifications = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NotifiOthersBean bean = new NotifiOthersBean();
            bean.recordTimeInMill.set(Calendar.getInstance().getTimeInMillis());
            bean.txtContent.set("desclkjsadlkfjlasdjfklklkasdlfklk卡死的弗兰克就爱上了贷款放假啦开始叫\n对方拉卡拉是快递费");
            notifications.add(bean);
        }

        mListener.hideLoading();
        mListener.showOthersNotifications(notifications);
    }

    public interface NotifiOthersPresenterListener extends BasePagePresenterListener {
        void showOthersNotifications(List<NotifiOthersBean> notifications);
    }
}
