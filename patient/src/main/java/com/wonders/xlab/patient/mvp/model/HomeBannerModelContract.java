package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.HomeBannerEntity;

import java.util.List;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/5.
 */
public interface HomeBannerModelContract {
    interface Callback extends BaseModelListener {
        void onReceiveHomeBannerSuccess(List<HomeBannerEntity.RetValuesEntity> bannerList);
    }

    interface Actions extends IBaseModel {
        void getHomeBanner(Callback callback);
    }
}