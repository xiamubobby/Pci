package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.BannerAPI;
import com.wonders.xlab.patient.mvp.entity.HomeBannerEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/4/5.
 */
public class HomeBannerModel extends PatientBaseModel implements HomeBannerModelContract.Actions {
    private BannerAPI mBannerAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public HomeBannerModel(BannerAPI bannerAPI) {
        mBannerAPI = bannerAPI;
    }

    @Override
    public void getHomeBanner(final HomeBannerModelContract.Callback callback) {
        request(mBannerAPI.getHomeBannerList(), new Callback<HomeBannerEntity>() {
            @Override
            public void onSuccess(HomeBannerEntity response) {
                if (response.getRet_values() == null) {
                    callback.onReceiveFailed(-1, "");
                } else {
                    callback.onReceiveHomeBannerSuccess(response.getRet_values());
                }
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
