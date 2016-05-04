package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.NotificationAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.AgreeJoinDoctorGroupEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IAgreeJoinDoctorGroupModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/19.
 */
public class AgreeJoinDoctorGroupModel extends DoctorBaseModel<AgreeJoinDoctorGroupEntity> implements IAgreeJoinDoctorGroupModel {
    private AgreeJoinDoctorGroupModelListener mListener;
    private NotificationAPI mAPI;

    public AgreeJoinDoctorGroupModel(AgreeJoinDoctorGroupModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(NotificationAPI.class);
    }

    @Override
    protected void onSuccess(AgreeJoinDoctorGroupEntity response) {
        mListener.onAgreeSuccess(response.getRet_values().getDoctorGroupId(), response.getMessage());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void agreeOrRejectJoinDoctorGroup(String doctorId, String ownerId, String inviteStatus) {
        request(mAPI.agreeOrRejectJoinDoctorGroup(doctorId, ownerId, inviteStatus), true);
    }

    public interface AgreeJoinDoctorGroupModelListener extends BaseModelListener {
        void onAgreeSuccess(String ownerId, String message);
    }
}
