package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;
import com.wonders.xlab.patient.mvp.model.MedicineRemindEditEditModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by WZH on 16/5/5.
 */
public class MedicineRemindEditEditPresenter extends BasePresenter implements MedicineRemindEditEditPresenterContract.Actions {

    private MedicineRemindEditEditPresenterContract.ViewListener mMedicineRemindEditPresenterListener;
    private MedicineRemindEditEditModelContract.Actions mMedicineRemidEditModel;

    @Inject
    public MedicineRemindEditEditPresenter(MedicineRemindEditEditPresenterContract.ViewListener medicineRemindEditPresenterListener, MedicineRemindEditEditModelContract.Actions medicineRemindEditModel) {
        mMedicineRemindEditPresenterListener = medicineRemindEditPresenterListener;
        mMedicineRemidEditModel = medicineRemindEditModel;

    }


    @Override
    public void edit(MedicineRemindEditBody body) {

    }
}
