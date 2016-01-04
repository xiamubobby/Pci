package com.wonders.xlab.pci.module.login.mvn.view;

import com.wonders.xlab.pci.module.base.mvn.view.BaseView;
import com.wonders.xlab.pci.module.base.mvn.entity.login.LoginEntity;

/**
 * Created by hua on 15/12/17.
 */
public interface LoginView extends BaseView {
    void loginSuccess(LoginEntity.RetValuesEntity value);

    void loginFailed(String message);
}
