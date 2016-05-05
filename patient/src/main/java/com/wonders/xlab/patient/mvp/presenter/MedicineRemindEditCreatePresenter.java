package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;
import com.wonders.xlab.patient.mvp.model.MedicineRemindEditCreateModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by WZH on 16/5/5.
 */
public class MedicineRemindEditCreatePresenter extends BasePresenter implements MedicineRemindEditCreatePresenterContract.Actions {

    private MedicineRemindEditCreatePresenterContract.ViewListener mMedicineRemindEditPresenterListener;
    private MedicineRemindEditCreateModelContract.Actions mMedicineRemidEditModel;

    @Inject
    public MedicineRemindEditCreatePresenter(MedicineRemindEditCreatePresenterContract.ViewListener medicineRemindEditPresenterListener, MedicineRemindEditCreateModelContract.Actions medicineRemidEditModel) {
        mMedicineRemindEditPresenterListener = medicineRemindEditPresenterListener;
        mMedicineRemidEditModel = medicineRemidEditModel;

    }


    @Override
    public void edit(MedicineRemindEditBody body) {

    }
}
