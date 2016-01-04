package com.wonders.xlab.pci.module.record.userinfo.mvn.view;

import com.wonders.xlab.pci.module.base.mvn.view.BaseView;
import com.wonders.xlab.pci.module.base.mvn.entity.record.userinfo.UserInfoEntity;

/**
 * Created by hua on 15/12/17.
 */
public interface UserInfoView extends BaseView {
    void onSuccess(UserInfoEntity.RetValuesEntity entity);

    void onFailed(String message);
}
