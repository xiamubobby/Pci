package com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.presenter.impl;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.adapter.bean.GroupServiceBean;
import com.wonders.xlab.pci.doctor.data.entity.GroupPackageListEntity;
import com.wonders.xlab.pci.doctor.data.model.IGroupPackageListModel;
import com.wonders.xlab.pci.doctor.data.model.impl.GroupPackageListModel;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.presenter.IGroupServicesPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hua on 16/4/10.
 */
public class GroupServicesPresenter extends BasePresenter implements IGroupServicesPresenter, GroupPackageListModel.GroupPackageListModelListener {
    private GroupServicesPresenterListener mListener;
    private IGroupPackageListModel mGroupPackageListModel;

    public GroupServicesPresenter(GroupServicesPresenterListener listener) {
        mListener = listener;
        mGroupPackageListModel = new GroupPackageListModel(this);
        addModel(mGroupPackageListModel);
    }

    @Override
    public void getPackages(String groupId) {
        mListener.showLoading("");
        mGroupPackageListModel.getPackageList(groupId);
    }

    @Override
    public void onReceivePackageListSuccess(List<GroupPackageListEntity.RetValuesEntity> valuesEntityList) {
        mListener.hideLoading();

        if (null == valuesEntityList || valuesEntityList.size() <= 0) {
            mListener.showEmptyView("");
            return;
        }

        Observable.from(valuesEntityList)
                .flatMap(new Func1<GroupPackageListEntity.RetValuesEntity, Observable<GroupPackageListEntity.RetValuesEntity>>() {
                    @Override
                    public Observable<GroupPackageListEntity.RetValuesEntity> call(GroupPackageListEntity.RetValuesEntity valuesEntity) {
                        return Observable.just(valuesEntity);
                    }
                })
                .map(new Func1<GroupPackageListEntity.RetValuesEntity, GroupServiceBean>() {
                    @Override
                    public GroupServiceBean call(GroupPackageListEntity.RetValuesEntity valuesEntity) {
                        GroupServiceBean bean = new GroupServiceBean();
                        bean.packageId.set(valuesEntity.getServicePackageId());
                        bean.packageIconUrl.set(valuesEntity.getServicePackageIconUrl());
                        bean.packageName.set(valuesEntity.getServicePackageName());
                        bean.packageDesc.set(valuesEntity.getValue());
                        bean.packageDescColor.set(valuesEntity.getColorString());
                        bean.published.set(valuesEntity.isPublished());
                        return bean;
                    }
                })
                .subscribe(new Subscriber<GroupServiceBean>() {
                    List<GroupServiceBean> groupServiceBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mListener.showPackages(groupServiceBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.showNetworkError(e.getMessage());
                    }

                    @Override
                    public void onNext(GroupServiceBean groupServiceBean) {
                        groupServiceBeanList.add(groupServiceBean);
                    }
                });

    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mListener, code, message);
    }

    public interface GroupServicesPresenterListener extends BasePresenterListener {
        void showPackages(List<GroupServiceBean> groupServiceBeanList);
    }

}
