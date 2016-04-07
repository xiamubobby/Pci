package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupListBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupListPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/7.
 */
public class GroupListPresenter extends BasePagePresenter implements IGroupListPresenter {
    private GroupManagePresenterListener mListener;

    public GroupListPresenter(GroupManagePresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getGroupList(boolean isRefresh, String doctorId) {
        List<GroupListBean> groupListBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GroupListBean bean = new GroupListBean();
            bean.setGroupId(String.valueOf(i));
            bean.setOwnerName("刘二");
            bean.setCreateTimeInMill(Calendar.getInstance().getTimeInMillis());
            bean.setOwnerHospital("XX医院");
            bean.setOwnerTitle("主任医师");
            bean.setOwnerDepartment("心血管");
            bean.setServedPeopleCounts(3);
            bean.setServingPeopleCounts(5);
            if (2 == i) {
                bean.setGroupName("我的个人诊所");
                bean.setBelongToCurrentDoctor(true);
            } else {
                bean.setGroupName("刘二小组");
                bean.setGroupMemberCounts(4);
                bean.setBelongToCurrentDoctor(false);
            }
            List<String> avatarList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                avatarList.add(Constant.DEFAULT_PORTRAIT);
            }
            bean.setAvatarList(avatarList);
            List<String> serviceIconList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                serviceIconList.add(Constant.DEFAULT_PORTRAIT);
            }
            bean.setServiceIconList(serviceIconList);

            groupListBeanList.add(bean);
        }

        mListener.hideLoading();
        mListener.showDoctorGroup(groupListBeanList);
    }

    public interface GroupManagePresenterListener extends BasePresenterListener{
        void showDoctorGroup(List<GroupListBean> groupListBeanList);
    }
}
