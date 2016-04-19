package com.wonders.xlab.pci.doctor.data.presenter.impl;

import android.text.TextUtils;

import com.wonders.xlab.pci.doctor.data.entity.UserInfoEntity;
import com.wonders.xlab.pci.doctor.data.model.impl.UserInfoModel;
import com.wonders.xlab.pci.doctor.module.chatroom.userinfo.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by hua on 16/2/23.
 */
public class UserInfoPresenter extends BasePresenter implements UserInfoModel.UserInfoModelListener {
    private UserInfoModel mUserInfoModel;
    private UserInfoPresenterListener mListener;


    public UserInfoPresenter(UserInfoPresenterListener listener) {
        mListener = listener;
        mUserInfoModel = new UserInfoModel(this);
        addModel(mUserInfoModel);
    }

    public void getUserInfo(String userId) {
        mListener.showLoading("");
        mUserInfoModel.getUserInfo(userId);
    }

    @Override
    public void onReceiveUserInfoSuccess(UserInfoEntity entity) {
        mListener.hideLoading();
        List<UserInfoEntity.RetValuesEntity> valuesEntity = entity.getRet_values();
        if (null == valuesEntity) {
            mListener.showEmptyView("无数据");
            return;
        }
        Observable.from(valuesEntity)
                .filter(new Func1<UserInfoEntity.RetValuesEntity, Boolean>() {
                    @Override
                    public Boolean call(UserInfoEntity.RetValuesEntity retValuesEntity) {
                        return null != retValuesEntity && !TextUtils.isEmpty(retValuesEntity.getValue());
                    }
                })
                .map(new Func1<UserInfoEntity.RetValuesEntity, UserInfoBean>() {
                    @Override
                    public UserInfoBean call(UserInfoEntity.RetValuesEntity retValuesEntity) {
                        UserInfoBean infoBean = new UserInfoBean();
                        infoBean.setTitle(retValuesEntity.getTitle());
                        infoBean.setValue(retValuesEntity.getValue());
                        return infoBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfoBean>() {
                    List<UserInfoBean> userInfoBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        if (userInfoBeanList.size() <= 0) {
                            mListener.showEmptyView("");
                        } else {
                            mListener.showUserInfo(userInfoBeanList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.showErrorToast(e.getMessage());
                    }

                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        userInfoBeanList.add(userInfoBean);
                    }
                });
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mListener, code, message);
    }

    public interface UserInfoPresenterListener extends BasePresenterListener {
        void showUserInfo(List<UserInfoBean> userInfoBeanList);
    }
}
