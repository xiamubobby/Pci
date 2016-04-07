package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyBean;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyMemberBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupModifyPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/7.
 */
public class GroupModifyPresenter extends BasePresenter implements IGroupModifyPresenter {
    private GroupModifyPresenterListener mListener;

    public GroupModifyPresenter(GroupModifyPresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getGroupInfo(String groupId) {
        GroupModifyBean groupModifyBean = new GroupModifyBean();
        groupModifyBean.setGroupId(groupId);
        groupModifyBean.setGroupDesc("小组简介\n换行");
        groupModifyBean.setGroupName("刘二医师小组");

        List<String> serviceIconList = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            serviceIconList.add(Constant.DEFAULT_PORTRAIT);
        }
        groupModifyBean.setPublishedServiceIconList(serviceIconList);

        List<GroupModifyMemberBean> memberBeanList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            GroupModifyMemberBean bean = new GroupModifyMemberBean();
            bean.setAvatarUrl(Constant.DEFAULT_PORTRAIT);
            bean.setMemberId(String.valueOf(i));
            bean.setMemberName("刘" + i);
            memberBeanList.add(bean);
        }
        groupModifyBean.setMemberInfoList(memberBeanList);

        mListener.hideLoading();
        mListener.showGroupInfo(groupModifyBean);
    }

    public interface GroupModifyPresenterListener extends BasePresenterListener {
        void showGroupInfo(GroupModifyBean groupModifyBean);
    }
}
