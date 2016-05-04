package com.wonders.xlab.patient.mvp.presenter.impl;

import android.text.TextUtils;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorBasicInfoBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.mvp.entity.DoctorGroupDetailEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorGroupDetailModel;
import com.wonders.xlab.patient.mvp.model.IOrderPackageServiceModel;
import com.wonders.xlab.patient.mvp.model.impl.DoctorGroupDetailModel;
import com.wonders.xlab.patient.mvp.model.impl.OrderPackageServiceModel;
import com.wonders.xlab.patient.mvp.presenter.IDoctorGroupDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/16.
 */
public class DoctorGroupDetailPresenter extends BasePresenter implements IDoctorGroupDetailPresenter, DoctorGroupDetailModel.DoctorGroupDetailModelListener, OrderPackageServiceModel.OrderPackageServiceModelListener {

    private DoctorGroupDetailPresenterListener mDoctorDetailListener;
    private IDoctorGroupDetailModel mDoctorDetailModel;
    private IOrderPackageServiceModel mOrderPackageServiceModel;

    public DoctorGroupDetailPresenter(DoctorGroupDetailPresenterListener doctorDetailListener) {
        mDoctorDetailListener = doctorDetailListener;

        mDoctorDetailModel = new DoctorGroupDetailModel(this);
        mOrderPackageServiceModel = new OrderPackageServiceModel(this);
        addModel(mOrderPackageServiceModel);
        addModel(mDoctorDetailModel);
    }

    @Override
    public void fetchDoctorGroupDetailInfo(String patientId,String ownerId) {
        mDoctorDetailModel.getDoctorGroupDetailInfo(patientId, ownerId);
    }

    @Override
    public void orderPackage(String patientId, String packageId) {
        mOrderPackageServiceModel.orderPackage(patientId, packageId);
    }

    @Override
    public void onReceiveDoctorGroupDetailSuccess(DoctorGroupDetailEntity.RetValuesEntity valuesEntity) {
        mDoctorDetailListener.hideLoading();

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
                bean.priceStr.set(entity.getValue() + entity.getUnit());
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
        mDoctorDetailListener.hideLoading();
        mDoctorDetailListener.showNetworkError(message);
    }

    @Override
    public void onOrderPackageServiceSuccess(String message) {
        mDoctorDetailListener.orderPackageSuccess(message);
    }

    public interface DoctorGroupDetailPresenterListener extends BasePresenterListener {
        void showBasicInfo(DoctorBasicInfoBean basicInfoBean);

        void showPackageList(ArrayList<DoctorDetailPackageBean> packageList);

        void showGroupMemberList(ArrayList<DoctorDetailGroupMemberBean> groupMemberList);

        void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList);

        void showMemberOrGroupOfDoctorRV();

        void hideMemberOrGroupOfDoctorRV();

        void orderPackageSuccess(String message);
    }
}
