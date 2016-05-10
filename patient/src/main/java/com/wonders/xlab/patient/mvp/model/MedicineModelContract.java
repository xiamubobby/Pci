package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.MedicineListEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/10.
 */
public interface MedicineModelContract {
    interface Callback extends BaseModelListener{
        void onReceiveMedicinesSuccess(MedicineListEntity entity);
    }

    interface Actions extends IBaseModel{
        void search(String searchKey, Callback callback);

        void getAllMedicines(Callback callback);
    }
}
