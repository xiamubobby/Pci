package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.GroupManagerAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupDoctorSaveEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupDoctorSearchModel;

import java.util.List;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/12.
 */
public class GroupDoctorSearchModel extends DoctorBaseModel<GroupDoctorSaveEntity> implements IGroupDoctorSearchModel {
    private GroupDoctorSearchModelListener mListener;
    private GroupManagerAPI mAPI;

    public GroupDoctorSearchModel(GroupDoctorSearchModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(GroupManagerAPI.class);
    }

    @Override
    protected void onSuccess(GroupDoctorSaveEntity response) {
        mListener.onSearchDoctorSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void searchDoctorByTelOrName(String doctorId, String doctorGroupId, String tel, String name) {
        request(mAPI.searchDoctorByTelOrName(doctorId,doctorGroupId, tel, name), true);
    }

    public interface GroupDoctorSearchModelListener extends BaseModelListener {
        void onSearchDoctorSuccess(List<GroupDoctorSaveEntity.RetValuesEntity> valuesEntityList);
    }
}
