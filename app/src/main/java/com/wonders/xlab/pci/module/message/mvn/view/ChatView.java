package com.wonders.xlab.pci.module.message.mvn.view;

import com.wonders.xlab.pci.module.message.bean.ChatBean;
import com.wonders.xlab.pci.module.base.mvn.view.BaseView;

import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public interface ChatView extends BaseView {
    void showHomeList(List<ChatBean> beanList);

    void appendHomeList(List<ChatBean> beanList);

    void showError(String message);
}
