package com.wonders.xlab.pci.doctor.data.presenter.impl;

import com.wonders.xlab.pci.doctor.data.presenter.IGroupInvitePresenter;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/4/14.
 */
public class GroupInvitePresenter extends BasePagePresenter implements IGroupInvitePresenter {
    private GroupInvitePresenterListener mListener;

    public GroupInvitePresenter(GroupInvitePresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getInviteNotification(String doctorId) {
        mListener.hideLoading();
    }

    public interface GroupInvitePresenterListener extends BasePagePresenterListener{
        void showInviteNotificationList();
    }
}
