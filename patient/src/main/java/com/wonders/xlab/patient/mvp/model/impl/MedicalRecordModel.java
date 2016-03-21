package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicalRecordAPI;
import com.wonders.xlab.patient.mvp.entity.MedicalRecordEntity;

import im.hua.library.base.mvp.listener.BasePageModelListener;

/**
 * Created by hua on 16/2/25.
 */
public class MedicalRecordModel extends PatientBaseModel<MedicalRecordEntity> {
    private MedicalRecordModelListener mIMedicalRecordModel;
    private MedicalRecordAPI mMedicalRecordAPI;

    public MedicalRecordModel(MedicalRecordModelListener medicalRecordModel) {
        mIMedicalRecordModel = medicalRecordModel;
        mMedicalRecordAPI = mRetrofit.create(MedicalRecordAPI.class);
    }

    public void getMedicalRecordList(String userId) {
        if (!isLast()) {
            fetchData(mMedicalRecordAPI.getMedicalRecordList(userId, getPageIndex() + 1, getSize()), true);
        } else {
            mIMedicalRecordModel.noMoreData("没有就诊数据了。");
        }
    }

    @Override
    protected void onSuccess(MedicalRecordEntity response) {
        MedicalRecordEntity.RetValuesEntity ret_values = response.getRet_values();

        if (null == ret_values) {
            mIMedicalRecordModel.onReceiveFailed("获取就诊记录失败，请重试！");
            return;
        }

        setPageIndex(ret_values.getNumber());
        setLast(ret_values.isLast());
        setFirst(ret_values.isFirst());

        mIMedicalRecordModel.onReceiveMedicalRecordSuccess(response);
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        mIMedicalRecordModel.onReceiveFailed("获取就诊记录失败，请重试！");
    }

    public interface MedicalRecordModelListener extends BasePageModelListener {
        void onReceiveMedicalRecordSuccess(MedicalRecordEntity entity);
    }
}
