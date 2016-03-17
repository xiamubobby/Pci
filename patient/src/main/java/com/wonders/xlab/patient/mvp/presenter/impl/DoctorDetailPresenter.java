package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailPackageBean;
import com.wonders.xlab.patient.mvp.model.IDoctorDetailModel;
import com.wonders.xlab.patient.mvp.model.impl.DoctorDetailModel;
import com.wonders.xlab.patient.mvp.presenter.IDoctorDetailPresenter;

import java.util.ArrayList;

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
    public void fetchDoctorDetailInfo(String groupId) {
        mDoctorDetailModel.getDoctorDetailInfo(groupId);
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

    private void setupPackageList() {
        ArrayList<DoctorDetailPackageBean> packageList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
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
        }

        mDoctorDetailListener.showPackageList(packageList);
    }

    @Override
    public void onReceiveDoctorDetailSuccess() {
        mDoctorDetailListener.hideLoading();

        setupPackageList();

//        setupMemberList();

        setupGroupOfDoctorList();
    }

    @Override
    public void onReceiveFailed(String message) {
        mDoctorDetailListener.hideLoading();
        mDoctorDetailListener.showError(message);
    }

    public interface DoctorDetailPresenterListener extends BasePresenterListener {
        void showBasicInfo();

        void showPackageList(ArrayList<DoctorDetailPackageBean> packageList);

        void showGroupMemberList(ArrayList<DoctorDetailGroupMemberBean> groupMemberList);

        void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList);
    }
}
