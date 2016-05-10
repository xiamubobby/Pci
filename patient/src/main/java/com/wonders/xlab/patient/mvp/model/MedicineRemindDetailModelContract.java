package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.MedicineRemindDetailEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/9.
 */
public interface MedicineRemindDetailModelContract {
    interface Callback extends BaseModelListener{
        void OnReceiveMedicineRemindDetailSuccess(MedicineRemindDetailEntity entity);
    }

    interface Actions extends IBaseModel{
        void getRemindDetail(String remindersRecordId, Callback callback);
    }
}
