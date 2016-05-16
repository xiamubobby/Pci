package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.home.bean.HomeBannerBean;
import com.wonders.xlab.patient.mvp.entity.HomeBannerEntity;
import com.wonders.xlab.patient.mvp.model.IHomeBannerModel;
import com.wonders.xlab.patient.mvp.model.impl.HomeBannerModel;
import com.wonders.xlab.patient.mvp.presenter.IHomeTopPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/4/5.
 */
public class HomeTopPresenter extends BasePresenter implements IHomeTopPresenter, HomeBannerModel.HomeBannerModelListener {
    private HomeTopPresenterListener mListener;
    private IHomeBannerModel mBannerModel;

    public HomeTopPresenter(HomeTopPresenterListener listener) {
        mListener = listener;
        mBannerModel = new HomeBannerModel(this);
        addModel(mBannerModel);
    }

    @Override
    public void getHomeBanner() {
        mBannerModel.getHomeBanner();
    }

    @Override
    public void onReceiveHomeBannerSuccess(List<HomeBannerEntity.RetValuesEntity> bannerList) {
        mListener.hideLoading();
        if (null == bannerList || bannerList.size() == 0) {
            return;
        }
        List<HomeBannerBean> beanList = new ArrayList<>();
        for (HomeBannerEntity.RetValuesEntity entity : bannerList) {
            beanList.add(new HomeBannerBean(entity.getTitle(), entity.getImageUrl(), entity.getLinkUrl()));
        }

        mListener.showHomeTopBanner(beanList);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mListener.hideLoading();
        mListener.showNetworkError(message);
    }

    public interface HomeTopPresenterListener extends BasePresenterListener {
        void showHomeTopBanner(List<HomeBannerBean> homeBannerBeanList);
    }
}
