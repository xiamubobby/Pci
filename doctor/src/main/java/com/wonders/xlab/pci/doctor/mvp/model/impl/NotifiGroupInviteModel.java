package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.NotificationAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.NotifiGroupInviteEntity;
import com.wonders.xlab.pci.doctor.mvp.model.INotifiGroupInviteModel;

import java.util.List;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/18.
 */
public class NotifiGroupInviteModel extends DoctorBaseModel<NotifiGroupInviteEntity> implements INotifiGroupInviteModel {
    private NotifiGroupInviteModelListener mListener;
    private NotificationAPI mAPI;

    public NotifiGroupInviteModel(NotifiGroupInviteModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(NotificationAPI.class);
    }

    @Override
    protected void onSuccess(NotifiGroupInviteEntity response) {
        mListener.onReceiveGroupInviteNotifiSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void getGroupInviteNotifications(String doctorId, int page, int size) {
        request(mAPI.getGroupInviteNotifications(doctorId, page, size), true);
    }

    public interface NotifiGroupInviteModelListener extends BaseModelListener {
        void onReceiveGroupInviteNotifiSuccess(List<NotifiGroupInviteEntity.RetValuesEntity> valuesEntityList);
    }
}
