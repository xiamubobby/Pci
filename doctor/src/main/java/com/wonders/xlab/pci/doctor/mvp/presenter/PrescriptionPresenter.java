package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.patientinfo.prescription.adapter.bean.PrescriptionBean;
import com.wonders.xlab.pci.doctor.mvp.entity.PrescriptionEntity;
import com.wonders.xlab.pci.doctor.mvp.model.PrescriptionModal;
import com.wonders.xlab.pci.doctor.mvp.model.PrescriptionModalContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by jimmy on 16/5/5.
 */
public class PrescriptionPresenter extends BasePagePresenter implements PrescriptionPresenterContract.Actions {

    private PrescriptionPresenterContract.ViewListener mPrescriptionPresenter;

    private PrescriptionModalContract.Actions mPrescriptionModal;

    @Inject
    public PrescriptionPresenter(PrescriptionPresenterContract.ViewListener mPrescriptionPresenter, PrescriptionModal mPrescriptionModal) {
        this.mPrescriptionPresenter = mPrescriptionPresenter;
        this.mPrescriptionModal = mPrescriptionModal;
        addModel(mPrescriptionModal);
    }


    @Override
    public void getPrescriptionList(String patientId, boolean isRefresh) {
        mPrescriptionPresenter.showLoading("");
        if (isRefresh) {
            resetPageInfo();
        }
        mPrescriptionModal.getPrescriptionList(patientId, getNextPageIndex(), new PrescriptionModalContract.Callback() {
            @Override
            public void getPrescriptionListSuccess(PrescriptionEntity entity) {
                mPrescriptionPresenter.hideLoading();
                List<PrescriptionBean> prescriptionBeanList = new ArrayList<>();
                List<PrescriptionEntity.RetValuesBean.DataBean.ContentBean> entityContent = entity.getRet_values().getData().getContent();
                for (PrescriptionEntity.RetValuesBean.DataBean.ContentBean content : entityContent) {
                    PrescriptionBean bean = new PrescriptionBean();
                    bean.setRecordTime(content.getPrint_time());
                    bean.setHospitalName(content.getHospital_name());
                    List<String> medicineNames = content.getMedicine_names();
                    List<String> medicineList = new ArrayList<>();
                    for (String medicine : medicineNames) {
                        medicineList.add(medicine);
                    }
                    bean.setMedicineList(medicineList);
                    prescriptionBeanList.add(bean);
                }
                if (shouldAppend()) {
                    mPrescriptionPresenter.appendPrescriptionList(prescriptionBeanList);
                }
                mPrescriptionPresenter.showPrescriptionList(prescriptionBeanList);

            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mPrescriptionPresenter, code, message);
            }

        });
    }
}
