package com.wonders.xlab.pci.module.message.mvn.view;

import com.wonders.xlab.pci.module.message.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.base.mvn.view.BaseView;

import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public interface HomeView extends BaseView {
    void showHomeList(List<HomeTaskBean> beanList);

    void appendHomeList(List<HomeTaskBean> beanList);

    void showError(String message);
}
