package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupCreateEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupCreateBody;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupCreateModel;

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
        mListener.onGroupCreateSuccess(response.getMessage());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void createGroup(String doctorId, GroupCreateBody body) {
        fetchData(mAPI.createGroup(doctorId, body),true);
    }

    public interface GroupCreateModelListener extends BaseModelListener {
        void onGroupCreateSuccess(String message);
    }
}
