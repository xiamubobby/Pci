package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupPackageAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupPackageCreateBody;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupPackageCreateEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupPackageCreateModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/11.
 */
public class GroupPackageCreateModel extends DoctorBaseModel<GroupPackageCreateEntity> implements IGroupPackageCreateModel {
    private GroupPackageCreateModelListener mListener;
    private GroupPackageAPI mAPI;

    public GroupPackageCreateModel(GroupPackageCreateModelListener listener) {
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
    public void createPackage(GroupPackageCreateBody body) {
        fetchData(mAPI.createPackage(body), true);
    }

    public interface GroupPackageCreateModelListener extends BaseModelListener {
        void onCreatePackageSuccess(String message);
    }
}
