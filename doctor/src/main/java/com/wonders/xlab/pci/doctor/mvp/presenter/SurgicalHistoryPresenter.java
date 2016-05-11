package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory.adapter.bean.SurgicalHistoryBean;
import com.wonders.xlab.pci.doctor.mvp.entity.SurgicalHistoryEntity;
import com.wonders.xlab.pci.doctor.mvp.model.SurgicalHistoryModel;
import com.wonders.xlab.pci.doctor.mvp.model.SurgicalHistoryModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;


/**
 * Created by hua on 16/5/5.
 */
public class SurgicalHistoryPresenter extends BasePagePresenter implements SurgicalHistoryPresenterContract.Actions {
    private SurgicalHistoryPresenterContract.ViewListener mSurgicalHistoryPresenter;
    private SurgicalHistoryModelContract.Actions mSurgicalHistoryModel;

    @Inject
    public SurgicalHistoryPresenter(SurgicalHistoryPresenterContract.ViewListener surgicalHistoryPresenter, SurgicalHistoryModel surgicalHistoryModel) {
        mSurgicalHistoryPresenter = surgicalHistoryPresenter;
        mSurgicalHistoryModel = surgicalHistoryModel;
    }

    @Override
    public void getSurgicalHistory(String patientId, boolean isRefresh) {
        mSurgicalHistoryPresenter.showLoading("");
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mSurgicalHistoryPresenter.showReachTheLastPageNotice("没有数据了");
            return;
        }
        mSurgicalHistoryModel.getSurgicalHistoryList(patientId, getNextPageIndex(), new SurgicalHistoryModelContract.Callback() {
            @Override
            public void onReceiveSurgicalHistorySuccess(SurgicalHistoryEntity entity) {
                mSurgicalHistoryPresenter.hideLoading();
                List<SurgicalHistoryBean> mSurgicalHistoryBeanList = new ArrayList<>();
                List<SurgicalHistoryEntity.RetValuesEntity.DataEntity.ContentEntity> contentEntity = entity.getRet_values().getData().getContent();
                for (SurgicalHistoryEntity.RetValuesEntity.DataEntity.ContentEntity content : contentEntity) {
                    SurgicalHistoryBean bean = new SurgicalHistoryBean();
                    bean.setDepartmentName(content.getOffice_name());
                    bean.setDoctorSuggestion(content.getDoctor_suggest());
                    bean.setHospitalName(content.getHospital_name());
                    bean.setLeaveHospitalDiagnostics(content.getDiagnose_result());
                    bean.setSurgicalHistoryTime(content.getHospitalized_time());
                    mSurgicalHistoryBeanList.add(bean);
                }
                if (shouldAppend()) {
                    mSurgicalHistoryPresenter.appendSurgicalHistoryList(mSurgicalHistoryBeanList);
                    return;
                }
                mSurgicalHistoryPresenter.showSurgicalHistoryList(mSurgicalHistoryBeanList);

            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mSurgicalHistoryPresenter, code, message);
            }
        });
    }


//    public void onReceiveSurgicalHistorySuccess(SurgicalHistoryEntity entity) {
//        Observable.from(entity.getRet_values().getData().getContent())
//                .flatMap(new Func1<SurgicalHistoryEntity.RetValuesEntity.DataEntity.ContentEntity, Observable<SurgicalHistoryBean>>() {
//                    @Override
//                    public Observable<SurgicalHistoryBean> call(SurgicalHistoryEntity.RetValuesEntity.DataEntity.ContentEntity contentEntity) {
//                        SurgicalHistoryBean bean = new SurgicalHistoryBean();
//                        bean.setDepartmentName(contentEntity.getOffice_name());
//                        bean.setDoctorSuggestion(contentEntity.getDoctor_suggest());
//                        bean.setHospitalName(contentEntity.getHospital_name());
//                        bean.setLeaveHospitalDiagnostics(contentEntity.getDiagnose_result());
//                        bean.setSurgicalHistoryTime(contentEntity.getHospitalized_time());
//                        return Observable.just(bean);
//                    }
//                })
//                .subscribe(new Subscriber<SurgicalHistoryBean>() {
//                    List<SurgicalHistoryBean> mSurgicalHistoryBeanList = new ArrayList<>();
//
//                    @Override
//                    public void onCompleted() {
//                        if (shouldAppend()) {
//                            mSurgicalHistoryPresenter.appendSurgicalHistoryList(mSurgicalHistoryBeanList);
//                        } else {
//                            mSurgicalHistoryPresenter.showSurgicalHistoryList(mSurgicalHistoryBeanList);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mSurgicalHistoryPresenter.showErrorToast("获取数据出错");
//                    }
//
//                    @Override
//                    public void onNext(SurgicalHistoryBean surgicalHistoryBean) {
//                        mSurgicalHistoryBeanList.add(surgicalHistoryBean);
//                    }
//                });
//    }
}
