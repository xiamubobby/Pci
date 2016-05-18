package com.wonders.xlab.patient.module.home;

import com.wonders.xlab.patient.module.home.bean.HomeBannerBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/5.
 */
public interface HomePresenterContract {
    interface ViewListener extends BasePresenterListener{
        void showHomeTopBanner(List<HomeBannerBean> homeBannerBeanList);
    }
    interface Actions extends IBasePresenter{
        void getHomeBanner();
    }
}
