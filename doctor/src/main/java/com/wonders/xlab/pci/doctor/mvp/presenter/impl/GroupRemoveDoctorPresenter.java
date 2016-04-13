package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupMembersEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.GroupMemberModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupRemoveDoctorPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hua on 16/4/10.
 */
public class GroupRemoveDoctorPresenter extends BasePagePresenter implements IGroupRemoveDoctorPresenter, GroupMemberModel.GroupMemberModelListener {
    private GroupRemoveDoctorPresenterListener mListener;
    private GroupMemberModel mMemberModel;

    public GroupRemoveDoctorPresenter(GroupRemoveDoctorPresenterListener listener) {
        mListener = listener;
        mMemberModel = new GroupMemberModel(this);
        addModel(mMemberModel);
    }

    @Override
    public void getCurrentMemberList(String doctorId, String groupId) {
        mMemberModel.getMemberList(doctorId, groupId);
    }

    @Override
    public void removeMembers(List<GroupDoctorBean> doctorBeanList) {
        mListener.removeSuccess();
    }

    @Override
    public void onReceiveGroupMemberSuccess(List<GroupMembersEntity.RetValuesEntity> valuesEntityList) {
        mListener.hideLoading();

        Observable.from(valuesEntityList)
                .flatMap(new Func1<GroupMembersEntity.RetValuesEntity, Observable<GroupDoctorBean>>() {
                    @Override
                    public Observable<GroupDoctorBean> call(GroupMembersEntity.RetValuesEntity retValuesEntity) {
                        GroupDoctorBean bean = new GroupDoctorBean();
                        bean.doctorId.set(retValuesEntity.getId());
                        bean.doctorName.set(retValuesEntity.getName());
                        bean.doctorAvatarUrl.set(retValuesEntity.getAvatarUrl());
                        bean.doctorHospital.set(retValuesEntity.getHospital().getName());
                        bean.doctorTitle.set(retValuesEntity.getJobTitle());
                        bean.doctorDepartment.set(retValuesEntity.getDepartment().getName());
                        bean.isSelected.set(false);
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<GroupDoctorBean>() {
                    List<GroupDoctorBean> doctorBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mListener.showMemberList(doctorBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GroupDoctorBean groupDoctorBean) {
                        doctorBeanList.add(groupDoctorBean);
                    }
                });

    }

    @Override
    public void onReceiveFailed(int code, String message) {

    }

    public interface GroupRemoveDoctorPresenterListener extends BasePagePresenterListener {
        void showMemberList(List<GroupDoctorBean> doctorBeanList);

        void removeSuccess();
    }
}
