package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/11.
 */
public interface HealthRecordPresenterContract {
    interface ViewListener extends BasePresenterListener {
        void showResultMessage(String message);

        void showValidateButton(boolean validate);
    }

    interface Actions extends IBasePresenter {
        void getValidateResult();
    }
}
