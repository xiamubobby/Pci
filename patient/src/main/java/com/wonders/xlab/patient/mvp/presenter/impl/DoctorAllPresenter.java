package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.AllDoctorItemBean;
import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorAllModel;
import com.wonders.xlab.patient.mvp.model.impl.DoctorAllModel;
import com.wonders.xlab.patient.mvp.presenter.IDoctorAllPresenter;

import java.util.ArrayList;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/3/15.
 */
public class DoctorAllPresenter extends BasePagePresenter implements IDoctorAllPresenter, DoctorAllModel.DoctorAllModelListener {

    private DoctorAllPresenterListener mDoctorAllListener;
    private IDoctorAllModel mDoctorAllModel;

    public DoctorAllPresenter(DoctorAllPresenterListener doctorAllListener) {
        mDoctorAllListener = doctorAllListener;
        mDoctorAllModel = new DoctorAllModel(this);
    }

    public void getAllDoctors(String patientId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        mDoctorAllModel.getAllDoctorList(patientId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

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
            itemBean.setGroupId(entity.getDoctorGroupId());

            itemBean.setAdminName(entity.getOwnerName());
            itemBean.setTitle(entity.getJobTitle());
            itemBean.setDepartment(entity.getDepartment());
            itemBean.setHospital(entity.getHospitalName());
            itemBean.setPortraitUrl(null != entity.getAvatars() && entity.getAvatars().size() > 0 ? entity.getAvatars().get(0) : Constant.DEFAULT_PORTRAIT);

            ArrayList<String> serviceIconUrlList = new ArrayList<>();
            for (String packageUrl : entity.getPackageUrls()) {
                serviceIconUrlList.add(packageUrl);
            }
            itemBean.setServiceIconUrl(serviceIconUrlList);

            doctorItemBeanArrayList.add(itemBean);
        }

        if (shouldAppend()) {
            if (doctorItemBeanArrayList.size() <= 0) {
                mDoctorAllListener.showReachTheLastPageNotice("");
                return;
            }
            mDoctorAllListener.appendAllDoctorList(doctorItemBeanArrayList);
        } else {
            if (doctorItemBeanArrayList.size() <= 0) {
                mDoctorAllListener.showEmptyView();
                return;
            }
            mDoctorAllListener.showAllDoctorList(doctorItemBeanArrayList);
        }
    }

    @Override
    public void onReceiveFailed(String message) {
        mDoctorAllListener.hideLoading();
        mDoctorAllListener.showError(message);
    }

    public interface DoctorAllPresenterListener extends BasePagePresenterListener {
        void showAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);

        void appendAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);
    }
}
