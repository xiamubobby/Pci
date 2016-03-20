package com.wonders.xlab.patient.mvp.presenter.impl;

import android.text.TextUtils;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.module.main.doctors.detail.bean.DoctorBasicInfoBean;
import com.wonders.xlab.patient.mvp.entity.DoctorGroupDetailEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorGroupDetailModel;
import com.wonders.xlab.patient.mvp.model.impl.DoctorGroupDetailModel;
import com.wonders.xlab.patient.mvp.presenter.IDoctorGroupDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/16.
 */
public class DoctorGroupDetailPresenter extends BasePresenter implements IDoctorGroupDetailPresenter, DoctorGroupDetailModel.DoctorGroupDetailModelListener {

    private DoctorDetailPresenterListener mDoctorDetailListener;
    private IDoctorGroupDetailModel mDoctorDetailModel;

    public DoctorGroupDetailPresenter(DoctorDetailPresenterListener doctorDetailListener) {
        mDoctorDetailListener = doctorDetailListener;

        mDoctorDetailModel = new DoctorGroupDetailModel(this);
        addModel(mDoctorDetailModel);
    }

    @Override
    public void fetchDoctorGroupDetailInfo(String doctorGroupId) {
        mDoctorDetailModel.getDoctorGroupDetailInfo(doctorGroupId);
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
                bean.description.set(entity.getDescription());
                bean.priceStr.set(entity.getPrice() + entity.getUnit());
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
            mDoctorDetailListener.showGroupMemberList(memberBeanList);
        } else {
            //个人医生
            List<DoctorGroupDetailEntity.RetValuesEntity.BelongGroupEntity> belongGroup = valuesEntity.getBelongGroup();
            ArrayList<DoctorDetailGroupOfDoctorBean> memberBeanList = new ArrayList<>();
            for (DoctorGroupDetailEntity.RetValuesEntity.BelongGroupEntity entity : belongGroup) {
                DoctorDetailGroupOfDoctorBean bean = new DoctorDetailGroupOfDoctorBean();
                bean.name.set(entity.getName());
                bean.groupId.set(entity.getDoctorGroupId());
                List<String> avatars = entity.getAvatars();
                bean.groupPortraitUrl.set(null != avatars && avatars.size() > 0 && !TextUtils.isEmpty(avatars.get(0)) ? avatars.get(0) : Constant.DEFAULT_PORTRAIT);

                memberBeanList.add(bean);
            }
            mDoctorDetailListener.showGroupOfDoctorList(memberBeanList);
        }


    }

    @Override
    public void onReceiveFailed(String message) {
        mDoctorDetailListener.hideLoading();
        mDoctorDetailListener.showError(message);
    }

    public interface DoctorDetailPresenterListener extends BasePresenterListener {
        void showBasicInfo(DoctorBasicInfoBean basicInfoBean);

        void showPackageList(ArrayList<DoctorDetailPackageBean> packageList);

        void showGroupMemberList(ArrayList<DoctorDetailGroupMemberBean> groupMemberList);

        void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList);
    }
}
