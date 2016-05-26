package com.wonders.xlab.patient.mvp.presenter;


import com.wonders.xlab.patient.module.healthrecord.prescription.adapter.bean.PrescriptionBean;
import com.wonders.xlab.patient.mvp.entity.PrescriptionEntity;
import com.wonders.xlab.patient.mvp.model.PrescriptionModel;
import com.wonders.xlab.patient.mvp.model.PrescriptionModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by jimmy on 16/5/5.
 */
public class PrescriptionPresenter extends BasePagePresenter implements PrescriptionPresenterContract.Actions {

    private PrescriptionPresenterContract.ViewListener mViewListener;

    private PrescriptionModelContract.Actions mPrescriptionModal;

    @Inject
    public PrescriptionPresenter(PrescriptionPresenterContract.ViewListener mPrescriptionPresenter, PrescriptionModel mPrescriptionModel) {
        this.mViewListener = mPrescriptionPresenter;
        this.mPrescriptionModal = mPrescriptionModel;
        addModel(mPrescriptionModel);
    }


    @Override
    public void getPrescriptionList(String patientId, boolean isRefresh) {
        mViewListener.showLoading("");
        if (isRefresh) {
            resetPageInfo();
        }
        mViewListener.showLoading("");
        mPrescriptionModal.getPrescriptionList(patientId, getNextPageIndex(), new PrescriptionModelContract.Callback() {
            @Override
            public void getPrescriptionListSuccess(PrescriptionEntity entity) {
                mViewListener.hideLoading();
                List<PrescriptionBean> prescriptionBeanList = new ArrayList<>();
                PrescriptionEntity.RetValuesBean retValues = entity.getRet_values();
                if (null == retValues) {
                    mViewListener.showEmptyView("");
                    return;
                }

                PrescriptionEntity.RetValuesBean.DataBean dataBean = retValues.getData();
                if (null == dataBean) {
                    mViewListener.showEmptyView("");
                    return;
                }

                List<PrescriptionEntity.RetValuesBean.DataBean.ContentBean> entityContent = dataBean.getContent();
                if (null == entityContent) {
                    mViewListener.showEmptyView("");
                    return;
                }

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
                    mViewListener.appendPrescriptionList(prescriptionBeanList);
                }
                mViewListener.showPrescriptionList(prescriptionBeanList);

            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mViewListener, code, message);
            }

        });
    }
}
