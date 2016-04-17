package com.wonders.xlab.pci.doctor.data.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.data.entity.GroupCreateEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateBasicInfoBody;
import com.wonders.xlab.pci.doctor.data.model.IGroupCreateModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/12.
 * 创建小组
 */
public class GroupCreateModel extends DoctorBaseModel<GroupCreateEntity> implements IGroupCreateModel {
    private GroupCreateModelListener mListener;
    private GroupManagerAPI mAPI;

    public GroupCreateModel(GroupCreateModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupManagerAPI.class);
    }

    @Override
    protected void onSuccess(GroupCreateEntity response) {
        mListener.onGroupCreateSuccess(response.getRet_values().getId(), response.getMessage());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void createGroup(String doctorId, GroupUpdateBasicInfoBody body) {
        request(mAPI.updateDoctorGroup(doctorId, body),true);
    }

    public interface GroupCreateModelListener extends BaseModelListener {
        void onGroupCreateSuccess(String newGroupId, String message);
    }
}
