package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.healthrecord.medicalrecords.adapter.bean.MedicalRecordsBean;
import com.wonders.xlab.patient.mvp.entity.MedicalRecordsEntity;
import com.wonders.xlab.patient.mvp.model.MedicalRecordsModel;
import com.wonders.xlab.patient.mvp.model.MedicalRecordsModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by jimmy on 16/5/11.
 */
public class MedicalRecordsPresenter extends BasePagePresenter implements MedicalRecordsPresenterContract.Actions {

    private MedicalRecordsPresenterContract.ViewListener mMedicalRecordsPresenter;

    private MedicalRecordsModelContract.Actions mMedicalRecordsModel;

    @Inject
    public MedicalRecordsPresenter(MedicalRecordsPresenterContract.ViewListener medicalRecordsPresenter, MedicalRecordsModel medicalRecordsModel) {
        this.mMedicalRecordsPresenter = medicalRecordsPresenter;
        this.mMedicalRecordsModel = medicalRecordsModel;
        addModel(mMedicalRecordsModel);
    }

    @Override
    public void getMedicalRecordsList(String patientId, boolean isRefresh) {
        if (isRefresh) {
            mCurrentIndex = -1;
            mIsFirst = true;
            mIsLast = false;
        }
        if (mIsLast) {
            mMedicalRecordsPresenter.showReachTheLastPageNotice("没有更多数据了");
            return;
        }
        mMedicalRecordsModel.getMedicalRecordsList(patientId, getNextPageIndex(), DEFAULT_PAGE_SIZE, new MedicalRecordsModelContract.Callback() {

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mMedicalRecordsPresenter, code, message);
            }
            @Override
            public void onReceiveMedicalRecordsSuccess(MedicalRecordsEntity valuesEntity) {
                mMedicalRecordsPresenter.hideLoading();
                MedicalRecordsEntity.RetValuesEntity.DataEntity dataEntity = valuesEntity.getRet_values().getData();

                updatePageInfo(dataEntity.getMore_params().getFlag(), dataEntity.isMore(), !dataEntity.isMore());

                Observable.from(dataEntity.getContent())
                        .map(new Func1<MedicalRecordsEntity.RetValuesEntity.DataEntity.ContentEntity, MedicalRecordsBean>() {
                            @Override
                            public MedicalRecordsBean call(MedicalRecordsEntity.RetValuesEntity.DataEntity.ContentEntity contentEntity) {
                                MedicalRecordsBean bean = new MedicalRecordsBean();
                                bean.setTime(contentEntity.getDate());
                                bean.setOfficeType(contentEntity.getOffice_type());
                                bean.setHospitalName(contentEntity.getHospital_name());
                                bean.setDepartmentName(contentEntity.getOffice_name());
                                bean.setMedicalResult(contentEntity.getDiagnose_result());
                                return bean;
                            }
                        })
                        .subscribe(new Subscriber<MedicalRecordsBean>() {
                            List<MedicalRecordsBean> medicalRecordsBeanList = new ArrayList<>();

                            @Override
                            public void onCompleted() {
                                if (shouldAppend()) {
                                    mMedicalRecordsPresenter.appendMedicalRecordsList(medicalRecordsBeanList);
                                } else {
                                    mMedicalRecordsPresenter.showMedicalRecordsList(medicalRecordsBeanList);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                mMedicalRecordsPresenter.showToast("获取数据出错！");
                            }

                            @Override
                            public void onNext(MedicalRecordsBean medicalRecordsBean) {
                                medicalRecordsBeanList.add(medicalRecordsBean);
                            }
                        });
            }
        });
    }
}
