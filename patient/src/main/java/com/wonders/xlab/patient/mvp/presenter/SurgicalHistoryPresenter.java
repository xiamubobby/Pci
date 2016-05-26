package com.wonders.xlab.patient.mvp.presenter;



import com.wonders.xlab.patient.module.healthrecord.surgicalhistory.adapter.bean.SurgicalHistoryBean;
import com.wonders.xlab.patient.mvp.entity.SurgicalHistoryEntity;
import com.wonders.xlab.patient.mvp.model.SurgicalHistoryModel;
import com.wonders.xlab.patient.mvp.model.SurgicalHistoryModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


/**
 * Created by hua on 16/5/5.
 */
public class SurgicalHistoryPresenter extends BasePagePresenter implements SurgicalHistoryPresenterContract.Actions, SurgicalHistoryModelContract.Callback {
    private SurgicalHistoryPresenterContract.ViewListener mViewListener;
    private SurgicalHistoryModelContract.Actions mSurgicalHistoryModel;

    @Inject
    public SurgicalHistoryPresenter(SurgicalHistoryPresenterContract.ViewListener viewListener, SurgicalHistoryModel surgicalHistoryModel) {
        mViewListener = viewListener;
        mSurgicalHistoryModel = surgicalHistoryModel;
    }

    @Override
    public void getSurgicalHistory(String patientId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mViewListener.showReachTheLastPageNotice("没有数据了");
            return;
        }
        mViewListener.showLoading("");
        mSurgicalHistoryModel.getSurgicalHistory(patientId, getNextPageIndex(), this);
    }

    @Override
    public void onReceiveSurgicalHistorySuccess(SurgicalHistoryEntity entity) {
        mViewListener.hideLoading();
        SurgicalHistoryEntity.RetValuesEntity retValues = entity.getRet_values();
        if (null == retValues) {
            mViewListener.showEmptyView("");
            return;
        }
        SurgicalHistoryEntity.RetValuesEntity.DataEntity dataEntity = retValues.getData();
        if (null == dataEntity) {
            mViewListener.showEmptyView("");
            return;
        }
        List<SurgicalHistoryEntity.RetValuesEntity.DataEntity.ContentEntity> contentEntityList = dataEntity.getContent();
        if (null == contentEntityList) {
            mViewListener.showEmptyView("");
            return;
        }
        Observable.from(contentEntityList)
                .flatMap(new Func1<SurgicalHistoryEntity.RetValuesEntity.DataEntity.ContentEntity, Observable<SurgicalHistoryBean>>() {
                    @Override
                    public Observable<SurgicalHistoryBean> call(SurgicalHistoryEntity.RetValuesEntity.DataEntity.ContentEntity contentEntity) {
                        SurgicalHistoryBean bean = new SurgicalHistoryBean();
                        bean.setDepartmentName(contentEntity.getOffice_name());
                        bean.setDoctorSuggestion(contentEntity.getDoctor_suggest());
                        bean.setHospitalName(contentEntity.getHospital_name());
                        bean.setLeaveHospitalDiagnostics(contentEntity.getDiagnose_result());
                        bean.setSurgicalHistoryTime(contentEntity.getHospitalized_time());
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<SurgicalHistoryBean>() {
                    List<SurgicalHistoryBean> mSurgicalHistoryBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        if (shouldAppend()) {
                            mViewListener.appendSurgicalHistoryList(mSurgicalHistoryBeanList);
                        } else {
                            mViewListener.showSurgicalHistoryList(mSurgicalHistoryBeanList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewListener.showToast("获取数据出错");
                    }

                    @Override
                    public void onNext(SurgicalHistoryBean surgicalHistoryBean) {
                        mSurgicalHistoryBeanList.add(surgicalHistoryBean);
                    }
                });
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }
}
