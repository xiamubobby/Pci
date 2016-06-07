package com.wonders.xlab.patient.mvp.presenter.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorBasicInfoBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.mvp.entity.DoctorGroupDetailEntity;
import com.wonders.xlab.patient.mvp.entity.GenerateOrderPaymentEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorGroupDetailModel;
import com.wonders.xlab.patient.mvp.model.IOrderPackageServiceModel;
import com.wonders.xlab.patient.mvp.model.impl.DoctorGroupDetailModel;
import com.wonders.xlab.patient.mvp.model.impl.OrderPackageServiceModel;
import com.wonders.xlab.patient.mvp.presenter.DoctorDetailContract;
import com.wonders.xlab.patient.mvp.presenter.IDoctorGroupDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public class DoctorGroupDetailPresenter extends BasePresenter implements IDoctorGroupDetailPresenter, DoctorGroupDetailModel.DoctorGroupDetailModelListener, OrderPackageServiceModel.OrderPackageServiceModelListener {

    private DoctorDetailContract.ViewListener mDoctorDetailListener;
    private IDoctorGroupDetailModel mDoctorDetailModel;
    private IOrderPackageServiceModel mOrderPackageServiceModel;

    private Gson gson = new Gson();

    public DoctorGroupDetailPresenter(DoctorDetailContract.ViewListener doctorDetailListener) {
        mDoctorDetailListener = doctorDetailListener;

        mDoctorDetailModel = new DoctorGroupDetailModel(this);
        mOrderPackageServiceModel = new OrderPackageServiceModel(this);
        addModel(mOrderPackageServiceModel);
        addModel(mDoctorDetailModel);
    }

    @Override
    public void fetchDoctorGroupDetailInfo(String patientId, String ownerId) {
        mDoctorDetailModel.getDoctorGroupDetailInfo(patientId, ownerId);
    }

    @Override
    public void orderPackage(String patientId, String packageId, String paymentChannel) {
        mOrderPackageServiceModel.orderPackage(patientId, packageId, paymentChannel);
    }

    @Override
    public void onReceiveDoctorGroupDetailSuccess(DoctorGroupDetailEntity groupDetailEntity) {
        mDoctorDetailListener.hideLoading();

        DoctorGroupDetailEntity.RetValuesEntity valuesEntity = groupDetailEntity.getRet_values();

        if (groupDetailEntity.getRet_code() == 1) {
            //小组成员有变动，服务过期，需要重新获取列表，然后进入重新购买
            mDoctorDetailListener.doctorGroupExpired(groupDetailEntity.getMessage());
            return;
        }

        List<DoctorGroupDetailEntity.RetValuesEntity.SPackageEntity> sPackage = valuesEntity.getSPackage();
        /**
         * 基本信息
         */
        DoctorBasicInfoBean basicInfoBean = new DoctorBasicInfoBean();
        basicInfoBean.ownerName.set(valuesEntity.getOwnerName());
        basicInfoBean.isMulti.set(valuesEntity.isMulti());
        List<String> groupAvatar = valuesEntity.getGroupAvatar();
        basicInfoBean.groupAvatar.set(null != groupAvatar && groupAvatar.size() > 0 && !TextUtils.isEmpty(groupAvatar.get(0)) ? groupAvatar.get(0) : Constant.DEFAULT_PORTRAIT);
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
        ArrayList<DoctorDetailPackageBean> packageList = new ArrayList<>();

        if (null != sPackage) {
            for (DoctorGroupDetailEntity.RetValuesEntity.SPackageEntity entity : sPackage) {
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

        if (valuesEntity.isMulti()) {
            //医生小组
            List<DoctorGroupDetailEntity.RetValuesEntity.MembersEntity> members = valuesEntity.getMembers();
            ArrayList<DoctorDetailGroupMemberBean> memberBeanList = new ArrayList<>();
            for (DoctorGroupDetailEntity.RetValuesEntity.MembersEntity entity : members) {
                DoctorDetailGroupMemberBean bean = new DoctorDetailGroupMemberBean();
                bean.name.set(entity.getName());
                bean.doctorId.set(entity.getDoctorId());
                bean.portraitUrl.set(TextUtils.isEmpty(entity.getAvatarUrl()) ? Constant.DEFAULT_PORTRAIT : entity.getAvatarUrl());
                bean.title.set(entity.getJobTitle());

                memberBeanList.add(bean);
            }
            if (members.size() > 0) {
                mDoctorDetailListener.showMemberOrGroupOfDoctorRV();
                mDoctorDetailListener.showGroupMemberList(memberBeanList);
            } else {
                mDoctorDetailListener.hideMemberOrGroupOfDoctorRV();
            }
        } else {
            //个人医生
            List<DoctorGroupDetailEntity.RetValuesEntity.BelongGroupEntity> belongGroup = valuesEntity.getBelongGroup();
            ArrayList<DoctorDetailGroupOfDoctorBean> godList = new ArrayList<>();
            for (DoctorGroupDetailEntity.RetValuesEntity.BelongGroupEntity entity : belongGroup) {
                DoctorDetailGroupOfDoctorBean bean = new DoctorDetailGroupOfDoctorBean();
                bean.name.set(entity.getName());
                bean.groupId.set(entity.getDoctorGroupId());
                bean.ownerId.set(entity.getOwnerId());
                bean.groupPortraitUrls.set(entity.getAvatars());

                godList.add(bean);
            }
            if (godList.size() > 0) {
                mDoctorDetailListener.showMemberOrGroupOfDoctorRV();
                mDoctorDetailListener.showGroupOfDoctorList(godList);
            } else {
                mDoctorDetailListener.hideMemberOrGroupOfDoctorRV();
            }
        }

    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mDoctorDetailListener, code, message);
    }

    @Override
    public void onOrderPackageServiceSuccess(GenerateOrderPaymentEntity paymentEntity) {
        GenerateOrderPaymentEntity.RetValuesEntity retValues = paymentEntity.getRet_values();
        if (null == retValues) {
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
            mDoctorDetailListener.showToast(paymentEntity.getMessage());
            mDoctorDetailListener.refreshView();
        } else {
            mDoctorDetailListener.hideLoading();
            mDoctorDetailListener.startPayment(charge);
        }
    }
}
