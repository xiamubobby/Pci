package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/9.
 */
public interface MedicineStateModifyModelContract {
    interface Callback extends BaseModelListener {
        void onChangeRemindStateSuccess(String message);
    }

    interface Actions extends IBaseModel {
        void changeRemindState(String remindersRecordId, boolean manualCloseReminder, Callback callback);
    }
}
