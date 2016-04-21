package com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.data.entity.GroupDoctorUpdateMemberEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupMembersEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateMemberBody;
import com.wonders.xlab.pci.doctor.data.model.IGroupDoctorRemoveModel;
import com.wonders.xlab.pci.doctor.data.model.IGroupMemberModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupDoctorRemoveModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupMemberModel;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.IGroupRemoveDoctorPresenter;

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
public class GroupDoctorRemovePresenter extends BasePagePresenter implements IGroupRemoveDoctorPresenter, GroupMemberModel.GroupMemberModelListener, GroupDoctorRemoveModel.GroupDoctorRemoveModelListener {
    private GroupRemoveDoctorPresenterListener mListener;
    private IGroupMemberModel mMemberModel;
    private IGroupDoctorRemoveModel mDoctorRemoveModel;

    public GroupDoctorRemovePresenter(GroupRemoveDoctorPresenterListener listener) {
        mListener = listener;
        mMemberModel = new GroupMemberModel(this);
        mDoctorRemoveModel = new GroupDoctorRemoveModel(this);
        addModel(mMemberModel);
    }

    @Override
    public void getCurrentMemberList(String doctorId, String ownerId) {
        mListener.showLoading("");
        mMemberModel.getMemberList(doctorId, ownerId);
    }

    @Override
    public void removeMembers(String doctorId, GroupUpdateMemberBody body) {
        mDoctorRemoveModel.removeDoctors(doctorId, body);
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
        showError(mListener, code, message);
    }

    @Override
    public void onRemoveDoctorsSuccess(GroupDoctorUpdateMemberEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();
        mListener.removeSuccess();
    }

    public interface GroupRemoveDoctorPresenterListener extends BasePagePresenterListener {
        void showMemberList(List<GroupDoctorBean> doctorBeanList);

        void removeSuccess();
    }
}
