package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/6.
 */
public interface MedicineRemindEditPresenterContract {
    interface ViewListener extends BasePresenterListener {
        void showMedicineRemindInfo(int hour, int minutes, long startDate, long endDate, String message, List<MedicineRealmBean> beanList);

        void saveSuccess(String message);
    }

    interface Actions extends IBasePresenter {
        void getMedicineRemindInfoById(String medicineRemindId);

        /**
         * 添加或者修改
         * 如果是修改，则需要设置id
         * @param body
         */
        void addOrModify(MedicineRemindEditBody body);
    }
}
