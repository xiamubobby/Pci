package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupListBean;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupListEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupListModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.GroupListModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupListPresenter;

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
        mGroupListModel.getGroupList(doctorId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onReceiveGroupListSuccess(GroupListEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();

        updatePageInfo(valuesEntity.getNumber(), valuesEntity.isFirst(), valuesEntity.isLast());

        List<GroupListBean> groupListBeanList = new ArrayList<>();
        for (GroupListEntity.RetValuesEntity.ContentEntity entity : valuesEntity.getContent()) {
            GroupListBean bean = new GroupListBean();
            bean.setAvatarList(entity.getAvatarUrls());
            bean.setGroupId(entity.getId());
            bean.setGroupName(entity.getName());
            bean.setOwnerName(entity.getOwner().getName());
            bean.setOwnerDepartment(entity.getOwner().getDepartment().getName());
            bean.setOwnerTitle(entity.getOwner().getJobTitle());
            bean.setOwnerHospital(entity.getOwner().getHospital().getName());
            bean.setServingPeopleCounts(entity.getServingPeople());
            bean.setServedPeopleCounts(entity.getServedPeopleCount());

            boolean isEqual = entity.getOwner().getId().equals(AIManager.getInstance().getDoctorId());
            bean.setBelongToCurrentDoctor(isEqual);
            if (isEqual) {
                bean.setGroupName("我的个人诊所");
            }
            //TODO 后台接口正在完善
//            bean.setServiceIconList(entity.get);

            groupListBeanList.add(bean);
        }

        if (shouldAppend()) {
            mListener.appendDoctorGroup(groupListBeanList);
        } else {
            mListener.showDoctorGroup(groupListBeanList);
        }
    }

    @Override
    public void onReceiveFailed(String message) {
        mListener.hideLoading();
        mListener.showError(message);
    }

    public interface GroupManagePresenterListener extends BasePagePresenterListener {
        void showDoctorGroup(List<GroupListBean> groupListBeanList);

        void appendDoctorGroup(List<GroupListBean> groupListBeanList);
    }
}
