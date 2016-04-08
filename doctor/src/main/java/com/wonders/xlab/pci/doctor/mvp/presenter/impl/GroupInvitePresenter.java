package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupInviteDoctorBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupInvitePresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/4/8.
 */
public class GroupInvitePresenter extends BasePagePresenter implements IGroupInvitePresenter {
    private GroupInvitePresenterListener mListener;

    public GroupInvitePresenter(GroupInvitePresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void searchByNameOrTel(String searchKey) {
        List<GroupInviteDoctorBean> doctorBeanList = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            GroupInviteDoctorBean bean = new GroupInviteDoctorBean();
            bean.doctorId.set(String.valueOf(i));
            bean.doctorName.set("刘" + i);
            bean.doctorAvatarUrl.set(Constant.DEFAULT_PORTRAIT);
            bean.doctorHospital.set("上海医院");
            bean.doctorTitle.set("主任医师");
            bean.doctorDepartment.set("心内科");
            bean.isSelected.set(false);
            doctorBeanList.add(bean);
        }

        mListener.hideLoading();
        mListener.showDoctorList(doctorBeanList);
    }

    public interface GroupInvitePresenterListener extends BasePagePresenterListener {
        void showDoctorList(List<GroupInviteDoctorBean> doctorBeanList);

        void appendDoctorList(List<GroupInviteDoctorBean> doctorBeanList);
    }
}
