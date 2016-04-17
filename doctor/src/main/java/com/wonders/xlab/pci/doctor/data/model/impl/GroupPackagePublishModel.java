package com.wonders.xlab.pci.doctor.data.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.GroupPackageAPI;
import com.wonders.xlab.pci.doctor.data.entity.GroupPackageCreateEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupPackagePublishBody;
import com.wonders.xlab.pci.doctor.data.model.IGroupPackagePublishModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/11.
 */
public class GroupPackagePublishModel extends DoctorBaseModel<GroupPackageCreateEntity> implements IGroupPackagePublishModel {
    private GroupPackageCreateModelListener mListener;
    private GroupPackageAPI mAPI;

    public GroupPackagePublishModel(GroupPackageCreateModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupPackageAPI.class);
    }

    @Override
    protected void onSuccess(GroupPackageCreateEntity response) {
        mListener.onCreatePackageSuccess(response.getMessage());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void publishPackage(GroupPackagePublishBody body) {
        request(mAPI.publishDoctorPackage(body), true);
    }

    public interface GroupPackageCreateModelListener extends BaseModelListener {
        void onCreatePackageSuccess(String message);
    }
}
