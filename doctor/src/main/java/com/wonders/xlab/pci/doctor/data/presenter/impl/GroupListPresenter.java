package com.wonders.xlab.pci.doctor.data.presenter.impl;

import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupListBean;
import com.wonders.xlab.pci.doctor.data.entity.GroupListEntity;
import com.wonders.xlab.pci.doctor.data.model.IGroupListModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupListModel;
import com.wonders.xlab.pci.doctor.data.presenter.IGroupListPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/4/7.
 */
public class GroupListPresenter extends BasePagePresenter implements IGroupListPresenter, GroupListModel.GroupListModelListener {
    private GroupManagePresenterListener mListener;
    private IGroupListModel mGroupListModel;

    public GroupListPresenter(GroupManagePresenterListener listener) {
        mListener = listener;
        mGroupListModel = new GroupListModel(this);
        addModel(mGroupListModel);
    }

    @Override
    public void getGroupList(boolean isRefresh, String doctorId) {
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mListener.hideLoading();
            mListener.showReachTheLastPageNotice("没有更多数据了");
            return;
        }
        mListener.showLoading("");
        mGroupListModel.getGroupList(doctorId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onReceiveGroupListSuccess(GroupListEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();

        updatePageInfo(valuesEntity.getNumber(), valuesEntity.isFirst(), valuesEntity.isLast());

        List<GroupListBean> groupListBeanList = new ArrayList<>();
        for (GroupListEntity.RetValuesEntity.ContentEntity entity : valuesEntity.getContent()) {
            GroupListBean bean = new GroupListBean();
            bean.setCreateTimeInMill(entity.getCreateTime());
            bean.setAvatarList(entity.getAvatarUrls());
            bean.setGroupId(entity.getId());
            bean.setGroupName(entity.getName());
            bean.setGroupMemberCounts(entity.getSize());
            bean.setOwnerName(entity.getOwnerName());
            bean.setOwnerDepartment(entity.getOwnerDepartment());
            bean.setOwnerTitle(entity.getOwnerJobTitle());
            bean.setOwnerHospital(entity.getOwnerHospital());
            bean.setServingPeopleCounts(entity.getServingPeople());
            bean.setServedPeopleCounts(entity.getServedPeopleCount());

            boolean isEqual = entity.getOwnerId().equals(AIManager.getInstance().getDoctorId());
            bean.setBelongToCurrentDoctor(isEqual);
            if (isEqual) {
                bean.setGroupName("我的个人诊所");
            }
            bean.setServiceIconList(entity.getServiceUrls());

            groupListBeanList.add(bean);
        }

        if (shouldAppend()) {
            mListener.appendDoctorGroup(groupListBeanList);
        } else {
            mListener.showDoctorGroup(groupListBeanList);
        }
    }

    @Override
    public void canCreateMore(boolean canCreate) {
        mListener.cannotCreateMore(canCreate);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mListener.hideLoading();
        mListener.showNetworkError(message);
    }

    public interface GroupManagePresenterListener extends BasePagePresenterListener {
        void showDoctorGroup(List<GroupListBean> groupListBeanList);

        void appendDoctorGroup(List<GroupListBean> groupListBeanList);

        void cannotCreateMore(boolean canCreate);
    }
}
