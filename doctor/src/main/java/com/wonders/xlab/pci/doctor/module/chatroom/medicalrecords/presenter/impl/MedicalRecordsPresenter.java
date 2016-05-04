package com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.presenter.impl;

import com.wonders.xlab.pci.doctor.data.entity.MedicalRecordsEntity;
import com.wonders.xlab.pci.doctor.data.model.impl.MedicalRecordsModel;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.adapter.bean.MedicalRecordsBean;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.presenter.IMedicalRecordsPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by jimmy on 16/5/3.
 */
public class MedicalRecordsPresenter extends BasePagePresenter implements IMedicalRecordsPresenter, MedicalRecordsModel.MedicalRecordsModelListener {

    private MedicalRecordsPresenterListener mListener;
    private MedicalRecordsModel mMedicalRecordsModel;

    public MedicalRecordsPresenter(MedicalRecordsPresenterListener listener) {
        mListener = listener;
        mMedicalRecordsModel = new MedicalRecordsModel(this);
        addModel(mMedicalRecordsModel);
    }

    @Override
    public void getMedicalRecordsList(String patientId, boolean isRefresh) {
        if (isRefresh) {
            mListener.showLoading("");
            resetPageInfo();
        }
        if (mIsLast) {
            mListener.showReachTheLastPageNotice("没有更多数据了");
            return;
        }
        mMedicalRecordsModel.getMedicalRecordsList(patientId,getNextPageIndex());
    }

    @Override
    public void onReceiveMedicalRecordsSuccess(MedicalRecordsEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();
        MedicalRecordsEntity.RetValuesEntity.DataEntity dataEntity = valuesEntity.getData();

        updatePageInfo(dataEntity.getMore_params().getFlag(),dataEntity.isMore(),!dataEntity.isMore());

        /*
        List<MedicalRecordsBean> medicalRecordsBeanList = new ArrayList<>();
        for (MedicalRecordsEntity.RetValuesEntity.DataEntity.ContentEntity entity :
                dataEntity.getContent()) {
                MedicalRecordsBean bean = new MedicalRecordsBean();
                        bean.setTime(contentEntity.getDate());
                        bean.setTag(contentEntity.getOffice_type()+"");
                        bean.setHospitalName(contentEntity.getHospital_name());
                        bean.setDepartmentName(contentEntity.getOffice_name());
                        bean.setMedicalResult(contentEntity.getDiagnose_result());
                        medicalRecordsBeanList.add(bean);

        }
         if (shouldAppend()) {
                            mListener.appendMedicalRecordsList(medicalRecordsBeanList);
                        } else {
                            mListener.showMedicalRecordsList(medicalRecordsBeanList);
                        }
        */

        Observable.from(dataEntity.getContent())
                .map(new Func1<MedicalRecordsEntity.RetValuesEntity.DataEntity.ContentEntity, MedicalRecordsBean>() {
                    @Override
                    public MedicalRecordsBean call(MedicalRecordsEntity.RetValuesEntity.DataEntity.ContentEntity contentEntity) {
                        MedicalRecordsBean bean = new MedicalRecordsBean();
                        bean.setTime(contentEntity.getDate());
                        bean.setTag(contentEntity.getOffice_type()+"");
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
                            mListener.appendMedicalRecordsList(medicalRecordsBeanList);
                        } else {
                            mListener.showMedicalRecordsList(medicalRecordsBeanList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.showErrorToast("获取数据出错！");
                    }

                    @Override
                    public void onNext(MedicalRecordsBean medicalRecordsBean) {
                        medicalRecordsBeanList.add(medicalRecordsBean);
                    }
                });

    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mListener,code,message);
    }

    public interface MedicalRecordsPresenterListener extends BasePagePresenterListener {
        void showMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList);
        void appendMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList);
    }
}
