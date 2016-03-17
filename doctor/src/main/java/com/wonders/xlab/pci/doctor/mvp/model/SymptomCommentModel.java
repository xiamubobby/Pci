package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.SymptomAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomCommentEntity;
import com.wonders.xlab.pci.doctor.mvp.model.listener.SymptomCommentModelListener;

/**
 * Created by hua on 16/2/26.
 */
public class SymptomCommentModel extends DoctorBaseModel<SymptomCommentEntity> {
    private SymptomCommentModelListener mISymptomCommentModel;
    private SymptomAPI mSymptomAPI;

    public SymptomCommentModel(SymptomCommentModelListener iSymptomCommentModel) {
        mISymptomCommentModel = iSymptomCommentModel;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void saveComment(String symptomId,String doctorId,String comment,boolean check) {
        fetchData(mSymptomAPI.saveComment(symptomId,doctorId,comment,check), true);
    }

    @Override
    protected void onSuccess(SymptomCommentEntity response) {
        mISymptomCommentModel.onReceiveSymptomCommentSuccess(response);
    }

    @Override
    protected void onFailed(Throwable e) {
        mISymptomCommentModel.onReceiveFailed("保存备注失败，请重试！");
    }
}
