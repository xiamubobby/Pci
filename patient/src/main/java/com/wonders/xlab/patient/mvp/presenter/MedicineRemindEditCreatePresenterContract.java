package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/5/5.
 */
public interface MedicineRemindEditCreatePresenterContract {
    interface ViewListener extends BasePresenterListener {
        void editSuccess();
    }

    interface Actions extends IBasePresenter {

        void edit(MedicineRemindEditBody body);

    }
}
