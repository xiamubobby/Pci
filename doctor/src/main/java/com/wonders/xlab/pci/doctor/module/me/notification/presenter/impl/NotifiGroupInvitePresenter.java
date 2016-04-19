package com.wonders.xlab.pci.doctor.module.me.notification.presenter.impl;

import com.wonders.xlab.pci.doctor.data.entity.NotifiGroupInviteEntity;
import com.wonders.xlab.pci.doctor.data.model.IAgreeJoinDoctorGroupModel;
import com.wonders.xlab.pci.doctor.data.model.INotifiGroupInviteModel;
import com.wonders.xlab.pci.doctor.data.model.impl.AgreeJoinDoctorGroupModel;
import com.wonders.xlab.pci.doctor.data.model.impl.NotifiGroupInviteModel;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiGroupInviteBean;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.INotifiGroupInvitePresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hua on 16/4/14.
 */
public class NotifiGroupInvitePresenter extends BasePagePresenter implements INotifiGroupInvitePresenter, NotifiGroupInviteModel.NotifiGroupInviteModelListener, AgreeJoinDoctorGroupModel.AgreeJoinDoctorGroupModelListener {
    private NotifiGroupInvitePresenterListener mListener;
    private INotifiGroupInviteModel mGroupInviteModel;
    private IAgreeJoinDoctorGroupModel mJoinDoctorGroupModel;

    public NotifiGroupInvitePresenter(NotifiGroupInvitePresenterListener listener) {
        mListener = listener;
        mGroupInviteModel = new NotifiGroupInviteModel(this);
        mJoinDoctorGroupModel = new AgreeJoinDoctorGroupModel(this);
        addModel(mGroupInviteModel);
        addModel(mJoinDoctorGroupModel);
    }

    @Override
    public void getInviteNotifications(String doctorId) {
        mListener.showLoading("");
        mGroupInviteModel.getGroupInviteNotifications(doctorId, 0, DEFAULT_PAGE_SIZE);
    }

    @Override
    public void agreeJoinDoctorGroup(String doctorId, String doctorGroupId) {
        mJoinDoctorGroupModel.agreeJoinDoctorGroup(doctorId, doctorGroupId);
    }

    @Override
    public void onReceiveGroupInviteNotifiSuccess(List<NotifiGroupInviteEntity.RetValuesEntity> valuesEntityList) {
        mListener.hideLoading();
        Observable.from(valuesEntityList)
                .flatMap(new Func1<NotifiGroupInviteEntity.RetValuesEntity, Observable<NotifiGroupInviteBean>>() {
                    @Override
                    public Observable<NotifiGroupInviteBean> call(NotifiGroupInviteEntity.RetValuesEntity retValuesEntity) {
                        NotifiGroupInviteBean bean = new NotifiGroupInviteBean();
                        bean.setId(retValuesEntity.getGroupId());
                        bean.setOwnerName(retValuesEntity.getOwnerName());
                        bean.setOwnerJobTitle(retValuesEntity.getOwnerJobTitle());
                        bean.setOwnerDepartment(retValuesEntity.getOwnerDepartment());
                        bean.setGroupName(retValuesEntity.getGroupName());
                        bean.setOwnerHospital(retValuesEntity.getOwnerHos());
                        bean.setGroupDesc(retValuesEntity.getGroupDescription());
                        bean.setRecordTime(retValuesEntity.getGroupCreateTime());
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<NotifiGroupInviteBean>() {
                    List<NotifiGroupInviteBean> inviteBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        if (inviteBeanList.size() <= 0) {
                            mListener.showEmptyView("");
                        } else {
                            mListener.showInviteNotifications(inviteBeanList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.showErrorToast(e.getMessage());
                    }

                    @Override
                    public void onNext(NotifiGroupInviteBean notifiGroupInviteBean) {
                        inviteBeanList.add(notifiGroupInviteBean);
                    }
                });
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mListener, code, message);
    }

    @Override
    public void onAgreeSuccess(String groupId, String message) {
        mListener.onAgreeSuccess(groupId, message);
    }

    public interface NotifiGroupInvitePresenterListener extends BasePagePresenterListener {
        void showInviteNotifications(List<NotifiGroupInviteBean> inviteBeanList);

        void onAgreeSuccess(String groupId, String message);
    }
}
