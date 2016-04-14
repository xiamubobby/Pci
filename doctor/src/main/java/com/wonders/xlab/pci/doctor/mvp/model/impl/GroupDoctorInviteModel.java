package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupDoctorUpdateMemberEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupUpdateMemberBody;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupDoctorInviteModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/14.
 */
public class GroupDoctorInviteModel extends DoctorBaseModel<GroupDoctorUpdateMemberEntity> implements IGroupDoctorInviteModel {
    private GroupInviteDoctorModelListener mListener;
    private GroupManagerAPI mAPI;

    public GroupDoctorInviteModel(GroupInviteDoctorModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupManagerAPI.class);
    }

    @Override
    protected void onSuccess(GroupDoctorUpdateMemberEntity response) {
        mListener.onInviteDoctorSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void inviteDoctors(String doctorId, GroupUpdateMemberBody body) {
        request(mAPI.inviteDoctorToGroup(doctorId, body), true);
    }

    public interface GroupInviteDoctorModelListener extends BaseModelListener {
        void onInviteDoctorSuccess(GroupDoctorUpdateMemberEntity.RetValuesEntity valuesEntity);
    }
}
