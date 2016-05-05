package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.alldoctor.adapter.AllDoctorItemBean;
import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;
import com.wonders.xlab.patient.mvp.model.AllDoctorModel;
import com.wonders.xlab.patient.mvp.model.AllDoctorModelContract;

import java.util.ArrayList;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by hua on 16/3/15.
 */
public class AllDoctorPresenter extends BasePagePresenter implements AllDoctorPresenterContract.Actions {

    private AllDoctorPresenterContract.ViewListener mDoctorAllListener;
    private AllDoctorModelContract.Actions mDoctorAllModel;

    @Inject
    AllDoctorPresenter(AllDoctorPresenterContract.ViewListener doctorAllListener, AllDoctorModel allDoctorModel) {
        mDoctorAllListener = doctorAllListener;
        mDoctorAllModel = allDoctorModel;
        addModel(mDoctorAllModel);
    }

    @Override
    public void getAllDoctors(String patientId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        mDoctorAllModel.getAllDoctorList(patientId, getNextPageIndex(), DEFAULT_PAGE_SIZE, new AllDoctorModelContract.Callback() {
            @Override
            public void onReceiveAllDoctorListSuccess(DoctorAllEntity.RetValuesEntity valuesEntity) {
                mDoctorAllListener.hideLoading();

                updatePageInfo(valuesEntity.getPage());

                ArrayList<AllDoctorItemBean> doctorItemBeanArrayList = new ArrayList<>();

                for (DoctorAllEntity.RetValuesEntity.ResultEntity entity : valuesEntity.getResult()) {
                    AllDoctorItemBean itemBean = new AllDoctorItemBean();

                    itemBean.setPersonal(!entity.isMulti());
                    itemBean.setDoctorGroupName(entity.getGroupName());
                    itemBean.setTagStr(entity.getOrderStatus());
                    itemBean.setTagColor(entity.getStatusColor());
                    itemBean.setOwnerId(entity.getOwnerId());

                    itemBean.setAdminName(entity.getOwnerName());
                    itemBean.setTitle(entity.getJobTitle());
                    itemBean.setDepartment(entity.getDepartment());
                    itemBean.setHospital(entity.getHospitalName());
                    itemBean.setPortraitUrl(entity.getAvatars());

                    ArrayList<String> serviceIconUrlList = new ArrayList<>();
                    for (String packageUrl : entity.getPackageUrls()) {
                        serviceIconUrlList.add(packageUrl);
                    }
                    itemBean.setServiceIconUrl(serviceIconUrlList);

                    doctorItemBeanArrayList.add(itemBean);
                }

                if (shouldAppend()) {
                    if (doctorItemBeanArrayList.size() <= 0) {
                        mCurrentIndex--;
                        mDoctorAllListener.showReachTheLastPageNotice("");
                        return;
                    }
                    mDoctorAllListener.appendAllDoctorList(doctorItemBeanArrayList);
                } else {
                    if (doctorItemBeanArrayList.size() <= 0) {
                        mDoctorAllListener.showEmptyView("");
                        return;
                    }
                    mDoctorAllListener.showAllDoctorList(doctorItemBeanArrayList);
                }
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                mDoctorAllListener.hideLoading();
                mDoctorAllListener.showNetworkError(message);
            }
        });
    }

}
