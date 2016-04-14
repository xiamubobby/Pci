package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupAuthorizeEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupAuthorizeBody;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupAuthorizeModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/14.
 */
public class GroupAuthorizeModel extends DoctorBaseModel<GroupAuthorizeEntity> implements IGroupAuthorizeModel {
    private GroupAuthorizeModelListener mListener;
    private GroupManagerAPI mAPI;

    public GroupAuthorizeModel(GroupAuthorizeModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupManagerAPI.class);
    }

    @Override
    protected void onSuccess(GroupAuthorizeEntity response) {
        mListener.authorizeSuccess(response.getMessage());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void authorize(String doctorId, String doctorGroupId, GroupAuthorizeBody body) {
        request(mAPI.doGroupMemberAuthorize(doctorGroupId, doctorId, body), true);
    }

    public interface GroupAuthorizeModelListener extends BaseModelListener {
        void authorizeSuccess(String message);
    }
}
