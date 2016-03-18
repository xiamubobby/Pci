package com.wonders.xlab.patient.mvp.presenter.impl;

import android.text.TextUtils;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.module.doctors.detail.bean.DoctorGroupBasicInfoBean;
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
    public void fetchDoctorDetailInfo(String doctorGroupId) {
        mDoctorDetailModel.getDoctorDetailInfo(doctorGroupId);
    }

    @Override
    public void onReceiveDoctorDetailSuccess(DoctorDetailEntity.RetValuesEntity valuesEntity) {
        mDoctorDetailListener.hideLoading();

        /**
         * 基本信息
         */
        DoctorGroupBasicInfoBean basicInfoBean = new DoctorGroupBasicInfoBean();
        basicInfoBean.groupName.set(valuesEntity.getOwnerName());
        basicInfoBean.isMulti.set(valuesEntity.isMulti());
        List<String> groupAvatar = valuesEntity.getGroupAvatar();
        basicInfoBean.groupAvatar.set(null != groupAvatar && groupAvatar.size() > 0 && !TextUtils.isEmpty(groupAvatar.get(0)) ? groupAvatar.get(0) : Constant.DEFAULT_PORTRAIT);
        basicInfoBean.description.set(valuesEntity.getDescription());
        basicInfoBean.jobTitle.set(valuesEntity.getJobTitle());
        basicInfoBean.department.set(valuesEntity.getDepartment());
        basicInfoBean.hospital.set(valuesEntity.getHospital());
        basicInfoBean.servedPeopleCount.set(valuesEntity.getServedPeopleCount());
        basicInfoBean.servingPeople.set(valuesEntity.getServingPeople());
        mDoctorDetailListener.showBasicInfo(basicInfoBean);

        /**
         * 提供套餐
         */
        setupPackageList(valuesEntity.getSPackage());

        if (valuesEntity.isMulti()) {
            //医生小组
            List<DoctorDetailEntity.RetValuesEntity.MembersEntity> members = valuesEntity.getMembers();
            ArrayList<DoctorDetailGroupMemberBean> memberBeanList = new ArrayList<>();
            for (DoctorDetailEntity.RetValuesEntity.MembersEntity entity : members) {
                DoctorDetailGroupMemberBean bean = new DoctorDetailGroupMemberBean();
                bean.name.set(entity.getName());
                //TODO 此处应该还有groupId，因为doctorId无法进入详情界面
                bean.groupId.set(entity.getDoctorId());
                bean.portraitUrl.set(TextUtils.isEmpty(entity.getAvatarUrl()) ? Constant.DEFAULT_PORTRAIT : entity.getAvatarUrl());
                bean.title.set(entity.getJobTitle());

                memberBeanList.add(bean);
            }
            mDoctorDetailListener.showGroupMemberList(memberBeanList);
        } else {
            //个人医生
//            setupGroupOfDoctorList();
            List<DoctorDetailEntity.RetValuesEntity.BelongGroupEntity> belongGroup = valuesEntity.getBelongGroup();
            ArrayList<DoctorDetailGroupOfDoctorBean> memberBeanList = new ArrayList<>();
            for (DoctorDetailEntity.RetValuesEntity.BelongGroupEntity entity : belongGroup) {
                DoctorDetailGroupOfDoctorBean bean = new DoctorDetailGroupOfDoctorBean();
                bean.name.set(entity.getName());
                //TODO 此处应该还有groupId，因为doctorId无法进入详情界面
//                bean.groupId.set(entity.());
                List<String> avatars = entity.getAvatars();
                bean.groupPortraitUrl.set(null != avatars && avatars.size() > 0 && !TextUtils.isEmpty(avatars.get(0)) ? avatars.get(0) : Constant.DEFAULT_PORTRAIT);

                memberBeanList.add(bean);
            }
            mDoctorDetailListener.showGroupOfDoctorList(memberBeanList);
        }


    }

    private void setupMemberList() {
        ArrayList<DoctorDetailGroupMemberBean> memberBeanList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            DoctorDetailGroupMemberBean bean = new DoctorDetailGroupMemberBean();
            bean.name.set("刘" + i);
            bean.portraitUrl.set(Constant.DEFAULT_PORTRAIT);
            bean.title.set("主任医师");

            memberBeanList.add(bean);
        }
        mDoctorDetailListener.showGroupMemberList(memberBeanList);
    }

    private void setupGroupOfDoctorList() {
        ArrayList<DoctorDetailGroupOfDoctorBean> memberBeanList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            DoctorDetailGroupOfDoctorBean bean = new DoctorDetailGroupOfDoctorBean();
            bean.name.set("刘" + i + "医师小组");
            bean.groupPortraitUrl.set(Constant.DEFAULT_PORTRAIT);

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
            bean.priceStr.set(entity.getPrice() + entity.getUnit());
            bean.iconUrl.set(entity.getIconUrl());

            packageList.add(bean);
        }
        /*for (int i = 0; i < 3; i++) {
            DoctorDetailPackageBean bean = new DoctorDetailPackageBean();
            switch (i) {
                case 0:
                    bean.name.set("包月套餐");
                    bean.priceStr.set("120元/月");
                    break;
                case 1:
                    bean.name.set("VIP套餐");
                    bean.priceStr.set("500元/月");
                    break;
                case 2:
                    bean.name.set("免费随访");
                    bean.priceStr.set("免费");
                    break;
            }
            bean.iconUrl.set(Constant.DEFAULT_PORTRAIT);

            packageList.add(bean);
        }*/

        mDoctorDetailListener.showPackageList(packageList);
    }

    @Override
    public void onReceiveFailed(String message) {
        mDoctorDetailListener.hideLoading();
        mDoctorDetailListener.showError(message);
    }

    public interface DoctorDetailPresenterListener extends BasePresenterListener {
        void showBasicInfo(DoctorGroupBasicInfoBean basicInfoBean);

        void showPackageList(ArrayList<DoctorDetailPackageBean> packageList);

        void showGroupMemberList(ArrayList<DoctorDetailGroupMemberBean> groupMemberList);

        void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList);
    }
}
