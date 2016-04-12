package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupDoctorInviteEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IGroupDoctorSearchModel;
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
public class GroupInviteDoctorPresenter extends BasePagePresenter implements IGroupInviteDoctorPresenter, GroupDoctorSearchModel.GroupDoctorSearchModelListener {
    private GroupInvitePresenterListener mListener;
    private IGroupDoctorSearchModel mSearchModel;

    public GroupInviteDoctorPresenter(GroupInvitePresenterListener listener) {
        mListener = listener;
        mSearchModel = new GroupDoctorSearchModel(this);
    }

    @Override
    public void searchByNameOrTel(String doctorGroupId, String searchKey) {
        mSearchModel.searchDoctorByTelOrName(doctorGroupId, searchKey, searchKey);

    }

    @Override
    public void onSearchDoctorSuccess(List<GroupDoctorInviteEntity.RetValuesEntity> valuesEntityList) {
        mListener.hideLoading();

        Observable.from(valuesEntityList)
                .flatMap(new Func1<GroupDoctorInviteEntity.RetValuesEntity, Observable<GroupDoctorBean>>() {
                    @Override
                    public Observable<GroupDoctorBean> call(GroupDoctorInviteEntity.RetValuesEntity valuesEntity) {
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
                        mListener.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(GroupDoctorBean groupDoctorBean) {
                        doctorBeanList.add(groupDoctorBean);
                    }
                });
    }

    @Override
    public void onReceiveFailed(String message) {
        mListener.hideLoading();
        mListener.showError(message);
    }

    public interface GroupInvitePresenterListener extends BasePagePresenterListener {
        void showDoctorList(List<GroupDoctorBean> doctorBeanList);

        void appendDoctorList(List<GroupDoctorBean> doctorBeanList);
    }
}
