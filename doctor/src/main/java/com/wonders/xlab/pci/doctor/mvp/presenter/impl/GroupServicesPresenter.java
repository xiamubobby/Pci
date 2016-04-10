package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.servicemanage.adapter.bean.GroupServiceBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupServicesPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/10.
 */
public class GroupServicesPresenter extends BasePresenter implements IGroupServicesPresenter {
    private GroupServicesPresenterListener mListener;

    public GroupServicesPresenter(GroupServicesPresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getPackages(String groupId) {
        List<GroupServiceBean> groupServiceBeanList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            GroupServiceBean bean = new GroupServiceBean();
            bean.packageId.set(String.valueOf(i));
            bean.packageIconUrl.set(Constant.DEFAULT_PORTRAIT);
            switch (i) {
                case 0:
                    bean.packageName.set("包月套餐");
                    bean.packageDesc.set("未开通");
                    bean.packageDescColor.set("#fc6467");
                    break;
                case 1:
                    bean.packageName.set("免费随访");
                    bean.packageDesc.set("7天");
                    bean.packageDescColor.set("#bcbcbc");
                    break;
            }
            groupServiceBeanList.add(bean);
        }
        mListener.hideLoading();
        mListener.showPackages(groupServiceBeanList);
    }

    public interface GroupServicesPresenterListener extends BasePresenterListener {
        void showPackages(List<GroupServiceBean> groupServiceBeanList);
    }

}
