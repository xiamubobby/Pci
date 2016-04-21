package com.wonders.xlab.pci.doctor.data.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.data.entity.GroupMembersEntity;
import com.wonders.xlab.pci.doctor.data.model.IGroupMemberModel;

import java.util.List;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/13.
 */
public class GroupMemberModel extends DoctorBaseModel<GroupMembersEntity> implements IGroupMemberModel {
    private GroupMemberModelListener mListener;
    private GroupManagerAPI mAPI;

    public GroupMemberModel(GroupMemberModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupManagerAPI.class);
    }

    @Override
    protected void onSuccess(GroupMembersEntity response) {
        mListener.onReceiveGroupMemberSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void getMemberList(String doctorId, String ownerId) {
        request(mAPI.getMemberList(doctorId, ownerId),true);
    }

    public interface GroupMemberModelListener extends BaseModelListener {
        void onReceiveGroupMemberSuccess(List<GroupMembersEntity.RetValuesEntity> valuesEntityList);
    }
}
