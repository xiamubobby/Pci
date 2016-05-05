package com.wonders.xlab.patient.mvp.model;

import java.util.Map;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by WZH on 16/5/5.
 */
public interface MedicineRemindEditEditModelContract {

    interface Callback extends BaseModelListener {

        void editSuccess();
    }

    interface Actions extends IBaseModel {

        void edit(Map<String, Object> ext, Callback callback);
    }
}
