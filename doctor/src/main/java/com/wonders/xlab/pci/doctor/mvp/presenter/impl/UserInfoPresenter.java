package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.chatroom.userinfo.bean.UserInfoBean;
import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.UserInfoModel;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/23.
 */
public class UserInfoPresenter extends BasePresenter implements UserInfoModel.UserInfoModelListener {
    private UserInfoModel mUserInfoModel;
    private UserInfoPresenterListener mUserInfoPresenter;


    public UserInfoPresenter(UserInfoPresenterListener userInfoPresenter) {
        mUserInfoPresenter = userInfoPresenter;
        mUserInfoModel = new UserInfoModel(this);
        addModel(mUserInfoModel);
    }

    public void getUserInfo(String userId) {
        mUserInfoModel.getUserInfo(userId);
    }

    @Override
    public void onReceiveUserInfoSuccess(UserInfoEntity entity) {
        List<UserInfoEntity.RetValuesEntity> valuesEntity = entity.getRet_values();
        if (null == valuesEntity) {
            mUserInfoPresenter.showNetworkError("获取数据失败，请重试1");
            return;
        }
        if (mUserInfoPresenter != null) {
            List<UserInfoBean> userInfoBeanList = new ArrayList<>();
            for (int i = 0; i < valuesEntity.size(); i++) {
                UserInfoBean infoBean = new UserInfoBean();
                infoBean.setTitle(valuesEntity.get(i).getTitle());
                infoBean.setValue(valuesEntity.get(i).getValue());

                userInfoBeanList.add(infoBean);
            }
            mUserInfoPresenter.showUserInfo(userInfoBeanList);
        }
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        if (mUserInfoPresenter != null) {
            mUserInfoPresenter.showNetworkError(message);
        }
    }

    public interface UserInfoPresenterListener extends BasePresenterListener {
        void showUserInfo(List<UserInfoBean> userInfoBeanList);
    }
}
