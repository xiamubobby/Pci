package com.wonders.xlab.patient.module.medicineremind.edit;

import android.support.annotation.NonNull;

import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import java.util.List;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/13.
 */
public interface MedicineRemindEditContract {
    interface Callback extends BaseModelListener {

        void addOrModifySuccess(String message);
    }

    interface Model extends IBaseModel {

        void addOrModify(String patientId, MedicineRemindEditBody body, @NonNull Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void showMedicineRemindInfo(int hour, int minutes, long startDate, long endDate, String message, List<MedicineRealmBean> beanList);

        void saveSuccess(String message);
    }

    interface Presenter extends IBasePresenter {
        void getMedicineRemindInfoById(String medicineRemindId);

        /**
         * 添加或者修改
         * 如果是修改，则需要设置id
         *
         * @param body
         */
        void saveMedicineRemind(MedicineRemindEditBody body);
    }
}
