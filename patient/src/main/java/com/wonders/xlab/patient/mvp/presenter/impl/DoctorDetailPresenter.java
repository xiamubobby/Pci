package com.wonders.xlab.patient.mvp.presenter.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorBasicInfoBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.mvp.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.mvp.entity.GenerateOrderPaymentEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorDetailModel;
import com.wonders.xlab.patient.mvp.model.IOrderPackageServiceModel;
import com.wonders.xlab.patient.mvp.model.impl.DoctorDetailModel;
import com.wonders.xlab.patient.mvp.model.impl.OrderPackageServiceModel;
import com.wonders.xlab.patient.mvp.presenter.DoctorDetailContract;
import com.wonders.xlab.patient.mvp.presenter.IDoctorDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public class DoctorDetailPresenter extends BasePresenter implements IDoctorDetailPresenter, DoctorDetailModel.DoctorDetailModelListener, OrderPackageServiceModel.OrderPackageServiceModelListener {

    private DoctorDetailContract.ViewListener mDoctorDetailListener;
    private IDoctorDetailModel mDoctorDetailModel;
    private IOrderPackageServiceModel mOrderPackageServiceModel;
    private Gson gson = new Gson();

    public DoctorDetailPresenter(DoctorDetailContract.ViewListener doctorDetailListener) {
        mDoctorDetailListener = doctorDetailListener;

        mDoctorDetailModel = new DoctorDetailModel(this);
        mOrderPackageServiceModel = new OrderPackageServiceModel(this);
        addModel(mOrderPackageServiceModel);
        addModel(mDoctorDetailModel);
    }

    @Override
    public void fetchDoctorDetailInfo(String patientId, String doctorId) {
        mDoctorDetailModel.getDoctorDetailInfo(patientId, doctorId);
    }

    @Override
    public void orderPackage(String patientId, String packageId, String paymentChannel) {
        mOrderPackageServiceModel.orderPackage(patientId, packageId, paymentChannel);
    }

    @Override
    public void onReceiveDoctorDetailSuccess(DoctorDetailEntity doctorDetailEntity) {
        mDoctorDetailListener.hideLoading();

        if (doctorDetailEntity.getRet_code() == 1) {
            mDoctorDetailListener.doctorGroupExpired(doctorDetailEntity.getMessage());
            return;
        }
        DoctorDetailEntity.RetValuesEntity valuesEntity = doctorDetailEntity.getRet_values();

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
            bean.ownerId.set(entity.getOwnerId());
            bean.groupPortraitUrls.set(entity.getAvatars());

            memberBeanList.add(bean);
        }
        if (memberBeanList.size() > 0) {
            mDoctorDetailListener.showMemberOrGroupOfDoctorRV();
            mDoctorDetailListener.showGroupOfDoctorList(memberBeanList);
        } else {
            mDoctorDetailListener.hideMemberOrGroupOfDoctorRV();
        }


    }

    private void setupPackageList(List<DoctorDetailEntity.RetValuesEntity.SPackageEntity> packageEntityList) {
        ArrayList<DoctorDetailPackageBean> packageList = new ArrayList<>();

        for (DoctorDetailEntity.RetValuesEntity.SPackageEntity entity : packageEntityList) {
            DoctorDetailPackageBean bean = new DoctorDetailPackageBean();
            bean.packageId.set(entity.getDPackageId());
            bean.name.set(entity.getName());
            bean.orderStatus.set(entity.getOrderStatus());
            bean.description.set(entity.getDescription());
            bean.showPayment.set(entity.getPrice() > 0);
            bean.valueStr.set(entity.getValue() + entity.getUnit());
            bean.iconUrl.set(entity.getIconUrl());

            packageList.add(bean);
        }
        mDoctorDetailListener.showPackageList(packageList);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mDoctorDetailListener, code, message);
    }

    @Override
    public void onOrderPackageServiceSuccess(GenerateOrderPaymentEntity paymentEntity) {
        GenerateOrderPaymentEntity.RetValuesEntity retValues = paymentEntity.getRet_values();
        if (null == retValues) {
            mDoctorDetailListener.hideLoading();
            mDoctorDetailListener.showToast(paymentEntity.getMessage());
            return;
        }
        String charge;
        Object chargeEntity = retValues.getCharge();
        if (null == chargeEntity) {
            charge = "";
        } else {
            charge = gson.toJson(chargeEntity);
        }
        if (TextUtils.isEmpty(charge)) {
            mDoctorDetailListener.hideLoading();
            mDoctorDetailListener.showToast("购买成功");
            mDoctorDetailListener.refreshView();
        } else {
            mDoctorDetailListener.hideLoading();
            mDoctorDetailListener.startPayment(charge);
        }
    }
}
