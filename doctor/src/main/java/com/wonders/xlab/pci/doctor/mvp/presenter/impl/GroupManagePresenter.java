package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.bean.GroupManageBean;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.bean.ServiceIconBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupManagePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/7.
 */
public class GroupManagePresenter extends BasePagePresenter implements IGroupManagePresenter {
    private GroupManagePresenterListener mListener;

    public GroupManagePresenter(GroupManagePresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getGroupList(boolean isRefresh, String doctorId) {
        List<GroupManageBean> groupManageBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GroupManageBean bean = new GroupManageBean();
            bean.setGroupName("刘二小组");
            bean.setOwnerName("刘二");
            bean.setCreateTimeInMill(Calendar.getInstance().getTimeInMillis());
            bean.setOwnerHospital("XX医院");
            bean.setOwnerTitle("主任医师");
            bean.setOwnerDepartment("心血管");
            bean.setServedPeopleCounts(3);
            bean.setServingPeopleCounts(5);
            if (2 == i) {
                bean.setBelongToCurrentDoctor(true);
            } else {
                bean.setGroupMemberCounts(4);
                bean.setBelongToCurrentDoctor(false);
            }
            List<String> avatarList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                avatarList.add(Constant.DEFAULT_PORTRAIT);
            }
            bean.setAvatarList(avatarList);
            List<ServiceIconBean> serviceIconList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                ServiceIconBean serviceIconBean = new ServiceIconBean();
                serviceIconBean.setIconUrl(Constant.DEFAULT_PORTRAIT);
                serviceIconList.add(serviceIconBean);
            }
            bean.setServiceIconList(serviceIconList);

            groupManageBeanList.add(bean);
        }

        mListener.hideLoading();
        mListener.showDoctorGroup(groupManageBeanList);
    }

    public interface GroupManagePresenterListener extends BasePresenterListener{
        void showDoctorGroup(List<GroupManageBean> groupManageBeanList);
    }
}
