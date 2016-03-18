package com.wonders.xlab.patient.mvp.presenter.impl;

import android.text.TextUtils;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.module.doctors.detail.bean.DoctorBasicInfoBean;
import com.wonders.xlab.patient.mvp.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorDetailModel;
import com.wonders.xlab.patient.mvp.model.impl.DoctorDetailModel;
import com.wonders.xlab.patient.mvp.presenter.IDoctorDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/16.
 */
public class DoctorDetailPresenter extends BasePresenter implements IDoctorDetailPresenter, DoctorDetailModel.DoctorDetailModelListener {

    private DoctorDetailPresenterListener mDoctorDetailListener;
    private IDoctorDetailModel mDoctorDetailModel;

    public DoctorDetailPresenter(DoctorDetailPresenterListener doctorDetailListener) {
        mDoctorDetailListener = doctorDetailListener;

        mDoctorDetailModel = new DoctorDetailModel(this);
        addModel(mDoctorDetailModel);
    }

    @Override
    public void fetchDoctorDetailInfo(String doctorId) {
        mDoctorDetailModel.getDoctorDetailInfo(doctorId);
    }

    @Override
    public void onReceiveDoctorDetailSuccess(DoctorDetailEntity.RetValuesEntity valuesEntity) {
        mDoctorDetailListener.hideLoading();

        List<DoctorDetailEntity.RetValuesEntity.SPackageEntity> sPackage = valuesEntity.getSPackage();
        /**
         * 基本信息
         */
        DoctorBasicInfoBean basicInfoBean = new DoctorBasicInfoBean();
        basicInfoBean.ownerName.set(valuesEntity.getOwnerName());
        basicInfoBean.isMulti.set(false);
        basicInfoBean.groupAvatar.set(valuesEntity.getDoctorAvatar());
        basicInfoBean.description.set(valuesEntity.getDescription());
        basicInfoBean.jobTitle.set(valuesEntity.getJobTitle());
        basicInfoBean.department.set(valuesEntity.getDepartment());
        basicInfoBean.hospital.set(valuesEntity.getHospital());
        basicInfoBean.servedPeopleCount.set(valuesEntity.getServedPeopleCount());
        basicInfoBean.servingPeople.set(valuesEntity.getServingPeople());
        basicInfoBean.hasServicePackage.set(null != sPackage && sPackage.size() > 0);

        mDoctorDetailListener.showBasicInfo(basicInfoBean);

        /**
         * 提供套餐
         */
        setupPackageList(sPackage);

        List<DoctorDetailEntity.RetValuesEntity.BelongGroupEntity> belongGroup = valuesEntity.getBelongGroup();
        ArrayList<DoctorDetailGroupOfDoctorBean> memberBeanList = new ArrayList<>();
        for (DoctorDetailEntity.RetValuesEntity.BelongGroupEntity entity : belongGroup) {
            DoctorDetailGroupOfDoctorBean bean = new DoctorDetailGroupOfDoctorBean();
            bean.name.set(entity.getName());
            bean.groupId.set(entity.getDoctorGroupId());
            List<String> avatars = entity.getAvatars();
            bean.groupPortraitUrl.set(null != avatars && avatars.size() > 0 && !TextUtils.isEmpty(avatars.get(0)) ? avatars.get(0) : Constant.DEFAULT_PORTRAIT);

            memberBeanList.add(bean);
        }
        mDoctorDetailListener.showGroupOfDoctorList(memberBeanList);


    }

    private void setupPackageList(List<DoctorDetailEntity.RetValuesEntity.SPackageEntity> packageEntityList) {
        ArrayList<DoctorDetailPackageBean> packageList = new ArrayList<>();

        for (DoctorDetailEntity.RetValuesEntity.SPackageEntity entity : packageEntityList) {
            DoctorDetailPackageBean bean = new DoctorDetailPackageBean();
            bean.packageId.set(entity.getDPackageId());
            bean.name.set(entity.getName());
            bean.description.set(entity.getDescription());
            bean.priceStr.set(entity.getPrice() + entity.getUnit());
            bean.iconUrl.set(entity.getIconUrl());

            packageList.add(bean);
        }
        mDoctorDetailListener.showPackageList(packageList);
    }

    @Override
    public void onReceiveFailed(String message) {
        mDoctorDetailListener.hideLoading();
        mDoctorDetailListener.showError(message);
    }

    public interface DoctorDetailPresenterListener extends BasePresenterListener {
        void showBasicInfo(DoctorBasicInfoBean basicInfoBean);

        void showPackageList(ArrayList<DoctorDetailPackageBean> packageList);

        void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList);
    }
}
