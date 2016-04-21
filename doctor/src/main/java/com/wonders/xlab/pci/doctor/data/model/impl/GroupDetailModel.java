package com.wonders.xlab.pci.doctor.data.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.data.entity.GroupDetailEntity;
import com.wonders.xlab.pci.doctor.data.model.IGroupDetailModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/11.
 */
public class GroupDetailModel extends DoctorBaseModel<GroupDetailEntity> implements IGroupDetailModel {
    private GroupDetailModelListener mListener;
    private GroupManagerAPI mAPI;

    public GroupDetailModel(GroupDetailModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupManagerAPI.class);
    }

    @Override
    protected void onSuccess(GroupDetailEntity response) {
        if (response.getRet_code() == 1) {
            mListener.cannotCreateGroup(response.getMessage());
        } else {
            mListener.onReceiveGroupDetailSuccess(response.getRet_values());
        }
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void getGroupDetail(String doctorId, String ownerId) {
        request(mAPI.getGroupDetail(doctorId, ownerId), true);
    }

    public interface GroupDetailModelListener extends BaseModelListener {
        void onReceiveGroupDetailSuccess(GroupDetailEntity.RetValuesEntity valuesEntity);

        void cannotCreateGroup(String message);
    }
}
