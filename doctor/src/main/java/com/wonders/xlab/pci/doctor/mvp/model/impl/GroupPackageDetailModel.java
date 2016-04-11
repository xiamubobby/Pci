package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupPackageAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupPackageDetailEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupPackageDetailModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/11.
 */
public class GroupPackageDetailModel extends DoctorBaseModel<GroupPackageDetailEntity> implements IGroupPackageDetailModel {
    private GroupPackageDetailModelListener mListener;
    private GroupPackageAPI mPackageAPI;

    public GroupPackageDetailModel(GroupPackageDetailModelListener listener) {
        mListener = listener;
        mPackageAPI = mRetrofit.create(GroupPackageAPI.class);
    }

    @Override
    protected void onSuccess(GroupPackageDetailEntity response) {
        mListener.onReceivePackageDetailSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(String message) {
        mListener.onReceiveFailed(message);
    }

    @Override
    public void getPackageDetail(String doctorGroupId, String servicePackageId, boolean published) {
        fetchData(mPackageAPI.getPackageDetail(doctorGroupId, servicePackageId, published), true);
    }

    public interface GroupPackageDetailModelListener extends BaseModelListener {
        void onReceivePackageDetailSuccess(GroupPackageDetailEntity.RetValuesEntity valuesEntity);
    }
}
