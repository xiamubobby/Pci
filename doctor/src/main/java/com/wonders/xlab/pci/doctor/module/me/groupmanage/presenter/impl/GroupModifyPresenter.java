package com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.impl;

import com.wonders.xlab.pci.doctor.data.entity.GroupDetailEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateBasicInfoBody;
import com.wonders.xlab.pci.doctor.data.model.IGroupCreateModel;
import com.wonders.xlab.pci.doctor.data.model.IGroupDetailModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupCreateModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupDetailModel;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyBean;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyMemberBean;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.IGroupModifyPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by hua on 16/4/7.
 */
public class GroupModifyPresenter extends BasePresenter implements IGroupModifyPresenter, GroupDetailModel.GroupDetailModelListener, GroupCreateModel.GroupCreateModelListener {
    private GroupModifyPresenterListener mListener;
    private IGroupDetailModel mDetailModel;
    private IGroupCreateModel mCreateModel;

    public GroupModifyPresenter(GroupModifyPresenterListener listener) {
        mListener = listener;
        mDetailModel = new GroupDetailModel(this);
        mCreateModel = new GroupCreateModel(this);
        addModel(mDetailModel);
    }

    @Override
    public void getGroupInfo(String doctorId, String ownerId) {
        mListener.showLoading("");
        mDetailModel.getGroupDetail(doctorId, ownerId);
        mListener.showGroupInfo(new GroupModifyBean());
    }

    @Override
    public void createGroup(String doctorId, GroupUpdateBasicInfoBody body) {
        mCreateModel.createGroup(doctorId, body);
    }

    @Override
    public void onReceiveGroupDetailSuccess(GroupDetailEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();

        final GroupModifyBean groupModifyBean = new GroupModifyBean();
        groupModifyBean.setGroupDesc(valuesEntity.getGroupDescription());
        groupModifyBean.setGroupName(valuesEntity.getGroupName());
        groupModifyBean.setCanGrant(valuesEntity.isCanGrant());
        groupModifyBean.setPublishedServiceIconList(valuesEntity.getServiceUrls());

        if ("member".equals(valuesEntity.getManagerType().toLowerCase())) {
            mListener.hideAdminOperationView();
        } else {
            mListener.showAdminOperationView();
        }

        Observable.from(valuesEntity.getMembers())
                .flatMap(new Func1<GroupDetailEntity.RetValuesEntity.MembersEntity, Observable<GroupDetailEntity.RetValuesEntity.MembersEntity>>() {
                    @Override
                    public Observable<GroupDetailEntity.RetValuesEntity.MembersEntity> call(GroupDetailEntity.RetValuesEntity.MembersEntity membersEntity) {
                        return Observable.just(membersEntity);
                    }
                })
                .map(new Func1<GroupDetailEntity.RetValuesEntity.MembersEntity, GroupModifyMemberBean>() {
                    @Override
                    public GroupModifyMemberBean call(GroupDetailEntity.RetValuesEntity.MembersEntity membersEntity) {
                        GroupModifyMemberBean bean = new GroupModifyMemberBean();
                        bean.doctorAvatarUrl.set(membersEntity.getDoctorAvatarUrl());
                        bean.doctorId.set(membersEntity.getDoctorId());
                        bean.doctorImId.set(membersEntity.getDoctorImId());
                        bean.doctorName.set(membersEntity.getDoctorName());
                        bean.hasAgreed.set("join".equals(membersEntity.getInviteStatus().toLowerCase()));
                        return bean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupModifyMemberBean>() {
                    List<GroupModifyMemberBean> memberBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        groupModifyBean.setMemberInfoList(memberBeanList);
                        mListener.showGroupInfo(groupModifyBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GroupModifyMemberBean bean) {
                        memberBeanList.add(bean);
                    }
                });


    }

    @Override
    public void cannotCreateGroup(String message) {
        mListener.hideLoading();
        mListener.cannotCreateGroup(message);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mListener, code, message);
    }

    @Override
    public void onGroupCreateSuccess(String newGroupId, String message) {
        mListener.hideLoading();
        mListener.onGroupCreateSuccess(newGroupId, message);
    }

    public interface GroupModifyPresenterListener extends BasePresenterListener {
        void showAdminOperationView();

        void hideAdminOperationView();

        void showGroupInfo(GroupModifyBean groupModifyBean);

        void onGroupCreateSuccess(String newGroupId, String message);

        void cannotCreateGroup(String message);
    }
}