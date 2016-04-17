package com.wonders.xlab.pci.doctor.data.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.data.entity.GroupDoctorUpdateMemberEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateMemberBody;
import com.wonders.xlab.pci.doctor.data.model.IGroupDoctorRemoveModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/14.
 */
public class GroupDoctorRemoveModel extends DoctorBaseModel<GroupDoctorUpdateMemberEntity> implements IGroupDoctorRemoveModel {
    private GroupDoctorRemoveModelListener mListener;

    private GroupManagerAPI mAPI;

    public GroupDoctorRemoveModel(GroupDoctorRemoveModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupManagerAPI.class);
    }

    @Override
    protected void onSuccess(GroupDoctorUpdateMemberEntity response) {
        mListener.onRemoveDoctorsSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void removeDoctors(String doctorId, GroupUpdateMemberBody body) {
        request(mAPI.removeDoctorFromGroup(doctorId, body), true);
    }

    public interface GroupDoctorRemoveModelListener extends BaseModelListener {
        void onRemoveDoctorsSuccess(GroupDoctorUpdateMemberEntity.RetValuesEntity valuesEntity);
    }
}
