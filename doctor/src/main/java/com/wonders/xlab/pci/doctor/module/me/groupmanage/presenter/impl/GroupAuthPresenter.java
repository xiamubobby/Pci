package com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.data.entity.GroupAuthMembersEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupAuthorizeBody;
import com.wonders.xlab.pci.doctor.data.model.IGroupAuthMemberListModel;
import com.wonders.xlab.pci.doctor.data.model.IGroupAuthorizeModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupAuthMemberListListModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupAuthorizeModel;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.IGroupAuthPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


/**
 * Created by hua on 16/4/10.
 */
public class GroupAuthPresenter extends BasePresenter implements IGroupAuthPresenter, GroupAuthMemberListListModel.GroupAuthMemberModelListener, GroupAuthorizeModel.GroupAuthorizeModelListener {
    private GroupAuthPresenterListener mListener;
    private IGroupAuthMemberListModel mAuthMemberModel;
    private IGroupAuthorizeModel mAuthorizeModel;

    public GroupAuthPresenter(GroupAuthPresenterListener listener) {
        mListener = listener;
        mAuthMemberModel = new GroupAuthMemberListListModel(this);
        mAuthorizeModel = new GroupAuthorizeModel(this);
        addModel(mAuthMemberModel);
    }

    @Override
    public void getGroupMemberList(String doctorId,String ownerId) {
        mListener.showLoading("");
        mAuthMemberModel.getAuthMemberList(doctorId, ownerId);
    }

    @Override
    public void authorize(String doctorId, String ownerId, GroupAuthorizeBody body) {
        mListener.startAuthorizing("正在授权，请稍候...");
        mAuthorizeModel.authorize(doctorId, ownerId,body);
    }

    @Override
    public void onReceiveMemberListSuccess(List<GroupAuthMembersEntity.RetValuesEntity> valuesEntityList) {
        mListener.hideLoading();

        Observable.from(valuesEntityList)
                .flatMap(new Func1<GroupAuthMembersEntity.RetValuesEntity, Observable<GroupDoctorBean>>() {
                    @Override
                    public Observable<GroupDoctorBean> call(GroupAuthMembersEntity.RetValuesEntity retValuesEntity) {
                        GroupDoctorBean bean = new GroupDoctorBean();
                        bean.doctorId.set(retValuesEntity.getId());
                        bean.doctorName.set(retValuesEntity.getName());
                        bean.doctorAvatarUrl.set(retValuesEntity.getAvatarUrl());
                        bean.doctorHospital.set(retValuesEntity.getHospital().getName());
                        bean.doctorTitle.set(retValuesEntity.getJobTitle());
                        bean.doctorDepartment.set(retValuesEntity.getDepartment().getName());
                        bean.isSelected.set(!"member".equals(retValuesEntity.getManagerType().toLowerCase()));
                        return Observable.just(bean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupDoctorBean>() {
                    List<GroupDoctorBean> doctorBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mListener.showMemberList(doctorBeanList);
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
    public void authorizeSuccess(String message) {
        mListener.authorizeSuccess(message);
    }

    public interface GroupAuthPresenterListener extends BasePresenterListener {
        void startAuthorizing(String message);

        void authorizeSuccess(String message);

        void showMemberList(List<GroupDoctorBean> doctorBeanList);
    }
}
