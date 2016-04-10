package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupAuthPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/10.
 */
public class GroupAuthPresenter extends BasePresenter implements IGroupAuthPresenter {
    private GroupAuthPresenterListener mListener;

    public GroupAuthPresenter(GroupAuthPresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getGroupMemberList(String groupId) {
        List<GroupDoctorBean> doctorBeanList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            GroupDoctorBean bean = new GroupDoctorBean();
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
        mListener.showMemberList(doctorBeanList);
    }

    @Override
    public void authorize(List<GroupDoctorBean> doctorBeanList) {
        mListener.authorizeSuccess("授权成功");
    }

    public interface GroupAuthPresenterListener extends BasePresenterListener {
        void authorizeSuccess(String message);

        void showMemberList(List<GroupDoctorBean> doctorBeanList);
    }
}
