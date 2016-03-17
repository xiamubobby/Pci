package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.userinfo.bean.UserInfoBean;
import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;
import com.wonders.xlab.pci.doctor.mvp.model.UserInfoModel;
import com.wonders.xlab.pci.doctor.mvp.model.listener.UserInfoModelListener;
import com.wonders.xlab.pci.doctor.mvp.presenter.listener.UserInfoPresenterListener;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/2/23.
 */
public class UserInfoPresenter extends BasePresenter implements UserInfoModelListener {
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
            mUserInfoPresenter.showError("获取数据失败，请重试1");
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
    public void onReceiveFailed(String message) {
        if (mUserInfoPresenter != null) {
            mUserInfoPresenter.showError(message);
        }
    }
}
