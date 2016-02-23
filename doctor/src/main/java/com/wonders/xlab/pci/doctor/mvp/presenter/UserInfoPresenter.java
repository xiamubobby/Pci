package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.userinfo.bean.UserInfoBean;
import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;
import com.wonders.xlab.pci.doctor.mvp.model.UserInfoModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IUserInfoModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IUserInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/2/23.
 */
public class UserInfoPresenter extends BasePresenter implements IUserInfoModel{
    private UserInfoModel mUserInfoModel;
    private IUserInfoPresenter mUserInfoPresenter;


    public UserInfoPresenter(IUserInfoPresenter userInfoPresenter) {
        mUserInfoPresenter = userInfoPresenter;
        mUserInfoModel = new UserInfoModel(this);
        addModel(mUserInfoModel);
    }

    public void getUserInfo() {
        onReceiveUserInfoSuccess(null);
//        mUserInfoModel.getUserInfo();
    }

    @Override
    public void onReceiveUserInfoSuccess(UserInfoEntity entity) {
        if (mUserInfoPresenter != null) {
            List<UserInfoBean> userInfoBeanList = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                UserInfoBean infoBean = new UserInfoBean();
                String title = "";
                switch (i) {
                    case 0:
                        title = "住院号";
                        break;
                    case 1:
                        title = "床位号";
                        break;
                    case 2:
                        title = "姓名";
                        break;
                    case 3:
                        title = "性别";
                        break;
                    case 4:
                        title = "出生日期";
                        break;
                    case 5:
                        title = "首诊时间";
                        break;
                    case 6:
                        title = "手机号码";
                        break;
                    case 7:
                        title = "备注";
                        break;
                }
                infoBean.setTitle(title);
                infoBean.setValue("value"+i);

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
