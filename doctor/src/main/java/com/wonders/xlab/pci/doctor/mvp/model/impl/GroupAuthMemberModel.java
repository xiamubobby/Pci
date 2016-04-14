package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupAuthMembersEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupAuthMemberModel;

import java.util.List;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/13.
 */
public class GroupAuthMemberModel extends DoctorBaseModel<GroupAuthMembersEntity> implements IGroupAuthMemberModel {
    private GroupAuthMemberModelListener mListener;
    private GroupManagerAPI mAPI;

    public GroupAuthMemberModel(GroupAuthMemberModelListener listener) {
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
    public void getAuthMemberList(String doctorId,String doctorGroupId) {
        fetchData(mAPI.getAuthMemberList(doctorId,doctorGroupId), true);
    }

    public interface GroupAuthMemberModelListener extends BaseModelListener {
        void onReceiveMemberListSuccess(List<GroupAuthMembersEntity.RetValuesEntity> valuesEntityList);
    }

}
