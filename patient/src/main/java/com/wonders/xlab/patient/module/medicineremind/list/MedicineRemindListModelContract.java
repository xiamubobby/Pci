package com.wonders.xlab.patient.module.medicineremind.list;

import com.wonders.xlab.patient.mvp.entity.MedicineRemindListEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/9.
 */
public interface MedicineRemindListModelContract {
    interface Callback extends BaseModelListener{
        void onReceiveMedicineRemindListSuccess(MedicineRemindListEntity.RetValuesEntity<MedicineRemindListEntity.ContentEntity> valuesEntity);
    }

    interface Actions extends IBaseModel{
        void getMedicineRemindList(String patientId, int page, int size, Callback callback);
    }
}
