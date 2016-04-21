package com.wonders.xlab.pci.doctor.data.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.data.entity.GroupAuthMembersEntity;
import com.wonders.xlab.pci.doctor.data.model.IGroupAuthMemberListModel;

import java.util.List;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/13.
 */
public class GroupAuthMemberListListModel extends DoctorBaseModel<GroupAuthMembersEntity> implements IGroupAuthMemberListModel {
    private GroupAuthMemberModelListener mListener;
    private GroupManagerAPI mAPI;

    public GroupAuthMemberListListModel(GroupAuthMemberModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupManagerAPI.class);
    }

    @Override
    protected void onSuccess(GroupAuthMembersEntity response) {
        mListener.onReceiveMemberListSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code,message);
    }

    @Override
    public void getAuthMemberList(String doctorId,String ownerId) {
        request(mAPI.getAuthMemberList(doctorId, ownerId), true);
    }

    public interface GroupAuthMemberModelListener extends BaseModelListener {
        void onReceiveMemberListSuccess(List<GroupAuthMembersEntity.RetValuesEntity> valuesEntityList);
    }

}
