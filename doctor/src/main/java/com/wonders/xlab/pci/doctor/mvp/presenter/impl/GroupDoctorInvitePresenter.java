package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupDoctorSaveEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupDoctorUpdateMemberEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupUpdateMemberBody;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupDoctorInviteModel;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupDoctorSearchModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.GroupDoctorInviteModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.GroupDoctorSearchModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupInviteDoctorPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


/**
 * Created by hua on 16/4/8.
 */
public class GroupDoctorInvitePresenter extends BasePagePresenter implements IGroupInviteDoctorPresenter, GroupDoctorSearchModel.GroupDoctorSearchModelListener, GroupDoctorInviteModel.GroupInviteDoctorModelListener {
    private GroupInvitePresenterListener mListener;
    private IGroupDoctorSearchModel mSearchModel;
    private IGroupDoctorInviteModel mDoctorInviteModel;

    public GroupDoctorInvitePresenter(GroupInvitePresenterListener listener) {
        mListener = listener;
        mSearchModel = new GroupDoctorSearchModel(this);
        mDoctorInviteModel = new GroupDoctorInviteModel(this);
        addModel(mSearchModel);
        addModel(mDoctorInviteModel);
    }

    @Override
    public void searchByNameOrTel(String doctorId, String doctorGroupId, String searchKey) {
        mSearchModel.searchDoctorByTelOrName(doctorId, doctorGroupId, searchKey, searchKey);
    }

    @Override
    public void inviteDoctors(String doctorId, GroupUpdateMemberBody body) {
        mDoctorInviteModel.inviteDoctors(doctorId, body);
    }

    @Override
    public void onSearchDoctorSuccess(List<GroupDoctorSaveEntity.RetValuesEntity> valuesEntityList) {
        mListener.hideLoading();

        Observable.from(valuesEntityList)
                .flatMap(new Func1<GroupDoctorSaveEntity.RetValuesEntity, Observable<GroupDoctorBean>>() {
                    @Override
                    public Observable<GroupDoctorBean> call(GroupDoctorSaveEntity.RetValuesEntity valuesEntity) {
                        GroupDoctorBean bean = new GroupDoctorBean();
                        bean.doctorId.set(valuesEntity.getId());
                        bean.doctorImId.set(valuesEntity.getImId());
                        bean.doctorName.set(valuesEntity.getName());
                        bean.doctorAvatarUrl.set(valuesEntity.getAvatarUrl());
                        bean.doctorHospital.set(valuesEntity.getHospital().getName());
                        bean.doctorTitle.set(valuesEntity.getJobTitle());
                        bean.doctorDepartment.set(valuesEntity.getDepartment().getName());
                        bean.isSelected.set(false);
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<GroupDoctorBean>() {
                    List<GroupDoctorBean> doctorBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mListener.showDoctorList(doctorBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.showNetworkError(e.getMessage());
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
    public void onInviteDoctorSuccess(GroupDoctorUpdateMemberEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();
        mListener.inviteDoctorSuccess(valuesEntity.getDoctorGroupId());
    }

    public interface GroupInvitePresenterListener extends BasePagePresenterListener {
        void showDoctorList(List<GroupDoctorBean> doctorBeanList);

        void inviteDoctorSuccess(String newDoctorGroupId);
    }
}
