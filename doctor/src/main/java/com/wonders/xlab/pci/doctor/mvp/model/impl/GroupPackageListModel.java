package com.wonders.xlab.pci.doctor.mvp.model.impl;

import android.text.TextUtils;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupPackageAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupPackageListEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupPackageListModel;

import java.util.List;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/11.
 */
public class GroupPackageListModel extends DoctorBaseModel<GroupPackageListEntity> implements IGroupPackageListModel {
    private GroupPackageListModelListener mListener;
    private GroupPackageAPI mPackageAPI;

    public GroupPackageListModel(GroupPackageListModelListener listener) {
        mListener = listener;
        mPackageAPI = mRetrofit.create(GroupPackageAPI.class);
    }

    @Override
    protected void onSuccess(GroupPackageListEntity response) {
        mListener.onReceivePackageListSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void getPackageList(String ownerId) {
        request(mPackageAPI.getPackageList(TextUtils.isEmpty(ownerId) ? "0" : ownerId), true);
    }

    public interface GroupPackageListModelListener extends BaseModelListener {
        void onReceivePackageListSuccess(List<GroupPackageListEntity.RetValuesEntity> valuesEntityList);
    }
}
