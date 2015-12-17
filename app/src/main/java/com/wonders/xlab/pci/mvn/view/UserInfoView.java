package com.wonders.xlab.pci.mvn.view;

import com.wonders.xlab.pci.mvn.BaseView;
import com.wonders.xlab.pci.mvn.entity.report.UserInfoEntity;

/**
 * Created by hua on 15/12/17.
 */
public interface UserInfoView extends BaseView {
    void onSuccess(UserInfoEntity.RetValuesEntity entity);

    void onFailed(String message);
}
