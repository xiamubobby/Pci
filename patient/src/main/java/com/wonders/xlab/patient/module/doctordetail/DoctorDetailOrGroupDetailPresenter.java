package com.wonders.xlab.patient.module.doctordetail;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorBasicInfoBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.mvp.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.mvp.entity.DoctorGroupDetailEntity;
import com.wonders.xlab.patient.mvp.entity.GenerateOrderPaymentEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by WZH on 16/6/22.
 */
public class DoctorDetailOrGroupDetailPresenter extends BasePresenter implements DoctorDetailOrGroupDetailContract.Presenter, DoctorDetailOrGroupDetailContract.Callback {

    private DoctorDetailOrGroupDetailContract.ViewListener mViewListener;
    private DoctorDetailOrGroupDetailContract.Model mDoctorDetailOrGroupDetailModel;
    private Gson gson = new Gson();

    @Inject
    public DoctorDetailOrGroupDetailPresenter(DoctorDetailOrGroupDetailContract.ViewListener viewListener, DoctorDetailOrGroupDetailModel doctorDetailOrGroupDetailModel) {
        mViewListener = viewListener;
        mDoctorDetailOrGroupDetailModel = doctorDetailOrGroupDetailModel;
        addModel(mDoctorDetailOrGroupDetailModel);
    }

    @Override
    public void fetchDoctorDetailInfo(String patientId, String doctorId) {
        mDoctorDetailOrGroupDetailModel.getDoctorDetailInfo(patientId, doctorId, this);
    }


    @Override
    public void orderPackage(String patientId, String packageId, String paymentChannel) {
        mDoctorDetailOrGroupDetailModel.orderPackage(patientId, packageId, paymentChannel,this);
    }

    @Override
    public void fetchDoctorGroupDetailInfo(String patientId, String ownerId) {
        mDoctorDetailOrGroupDetailModel.getDoctorGroupDetailInfo(patientId, ownerId, this);
    }

    @Override
    public void onReceiveDoctorDetailSuccess(DoctorDetailEntity doctorDetailEntity) {
        mViewListener.hideLoading();
        if (doctorDetailEntity.getRet_code() == 1) {
            mViewListener.doctorGroupExpired(doctorDetailEntity.getMessage());
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

        mViewListener.showBasicInfo(basicInfoBean);

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
            mViewListener.showMemberOrGroupOfDoctorRV();
            mViewListener.showGroupOfDoctorList(memberBeanList);
        } else {
            mViewListener.hideMemberOrGroupOfDoctorRV();
        }
    }

    @Override
    public void onReceiveDoctorGroupDetailSuccess(DoctorGroupDetailEntity groupDetailEntity) {
        mViewListener.hideLoading();
        DoctorGroupDetailEntity.RetValuesEntity valuesEntity = groupDetailEntity.getRet_values();

        if (groupDetailEntity.getRet_code() == 1) {
            //小组成员有变动，服务过期，需要重新获取列表，然后进入重新购买
            mViewListener.doctorGroupExpired(groupDetailEntity.getMessage());
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

        mViewListener.showBasicInfo(basicInfoBean);

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
            mViewListener.showPackageList(packageList);

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
                mViewListener.showMemberOrGroupOfDoctorRV();
                mViewListener.showGroupMemberList(memberBeanList);
            } else {
                mViewListener.hideMemberOrGroupOfDoctorRV();
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
                mViewListener.showMemberOrGroupOfDoctorRV();
                mViewListener.showGroupOfDoctorList(godList);
            } else {
                mViewListener.hideMemberOrGroupOfDoctorRV();
            }
        }
    }

    @Override
    public void onOrderPackageServiceSuccess(GenerateOrderPaymentEntity paymentEntity) {
        GenerateOrderPaymentEntity.RetValuesEntity retValues = paymentEntity.getRet_values();
        if (null == retValues) {
            mViewListener.hideLoading();
            mViewListener.showToast(paymentEntity.getMessage());
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
            mViewListener.hideLoading();
            mViewListener.showToast(paymentEntity.getMessage());
            mViewListener.buyFreeSuccess();
            mViewListener.refreshView();
        } else {
            mViewListener.hideLoading();
            mViewListener.startPayment(charge);
        }
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
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
        mViewListener.showPackageList(packageList);
    }
}
