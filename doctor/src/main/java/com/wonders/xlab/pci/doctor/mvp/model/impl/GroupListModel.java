package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupListEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupListModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/11.
 */
public class GroupListModel extends DoctorBaseModel<GroupListEntity> implements IGroupListModel {
    private GroupListModelListener mListener;
    private GroupManagerAPI mAPI;

    public GroupListModel(GroupListModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupManagerAPI.class);
    }

    @Override
    protected void onSuccess(GroupListEntity response) {
        /**
         * ret_code=1 不能再创建诊所
         */
        mListener.canCreateMore(!(response.getRet_code() == 1));
        mListener.onReceiveGroupListSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int retCode, String message) {
        mListener.onReceiveFailed(message);
    }

    @Override
    public void getGroupList(String doctorId, int page, int size) {
        fetchData(mAPI.getGroupList(doctorId, page, size), true);
    }

    public interface GroupListModelListener extends BaseModelListener {
        void onReceiveGroupListSuccess(GroupListEntity.RetValuesEntity valuesEntity);

        void canCreateMore(boolean canCreate);
    }
}
