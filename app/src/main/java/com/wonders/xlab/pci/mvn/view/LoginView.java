package com.wonders.xlab.pci.mvn.view;

import com.wonders.xlab.pci.mvn.BaseView;
import com.wonders.xlab.pci.mvn.entity.LoginEntity;

/**
 * Created by hua on 15/12/17.
 */
public interface LoginView extends BaseView {
    void loginSuccess(LoginEntity.RetValuesEntity value);

    void loginFailed(String message);
}
