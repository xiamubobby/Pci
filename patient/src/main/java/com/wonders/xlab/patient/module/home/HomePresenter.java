package com.wonders.xlab.patient.module.home;

import com.wonders.xlab.patient.module.home.bean.HomeBannerBean;
import com.wonders.xlab.patient.mvp.entity.HomeBannerEntity;
import com.wonders.xlab.patient.mvp.model.HomeBannerModelContract;
import com.wonders.xlab.patient.mvp.model.HomeBannerModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/4/5.
 */
public class HomePresenter extends BasePresenter implements HomePresenterContract.Actions, HomeBannerModelContract.Callback {
    private HomePresenterContract.ViewListener mListener;
    private HomeBannerModelContract.Actions mBannerModel;

    @Inject
    public HomePresenter(HomePresenterContract.ViewListener listener,HomeBannerModel homeBannerModel) {
        mListener = listener;
        mBannerModel = homeBannerModel;
        addModel(mBannerModel);
    }

    @Override
    public void getHomeBanner() {
        mBannerModel.getHomeBanner(this);
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
}
