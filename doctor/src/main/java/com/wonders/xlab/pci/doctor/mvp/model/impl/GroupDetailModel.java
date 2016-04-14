package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupDetailEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupDetailModel;

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
    public void getGroupDetail(String doctorId, String doctorGroupId) {
        request(mAPI.getGroupDetail(doctorId, doctorGroupId), true);
    }

    public interface GroupDetailModelListener extends BaseModelListener {
        void onReceiveGroupDetailSuccess(GroupDetailEntity.RetValuesEntity valuesEntity);

        void cannotCreateGroup(String message);
    }
}
