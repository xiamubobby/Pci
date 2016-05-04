package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.BannerAPI;
import com.wonders.xlab.patient.mvp.entity.HomeBannerEntity;
import com.wonders.xlab.patient.mvp.model.IHomeBannerModel;

import java.util.List;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/5.
 */
public class HomeBannerModel extends PatientBaseModel<HomeBannerEntity> implements IHomeBannerModel{
    private HomeBannerModelListener mListener;
    private BannerAPI mBannerAPI;

    public HomeBannerModel(HomeBannerModelListener listener) {
        mListener = listener;
        mBannerAPI = mRetrofit.create(BannerAPI.class);
    }

    @Override
    protected void onSuccess(HomeBannerEntity response) {
        if (response.getRet_values() == null) {
            mListener.onReceiveFailed(-1, "");
        } else {
            mListener.onReceiveHomeBannerSuccess(response.getRet_values());
        }
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void getHomeBanner() {
        request(mBannerAPI.getHomeBannerList(),true);
    }

    public interface HomeBannerModelListener extends BaseModelListener {
        void onReceiveHomeBannerSuccess(List<HomeBannerEntity.RetValuesEntity> bannerList);
    }
}
