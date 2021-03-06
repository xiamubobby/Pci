package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.HomeBannerEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/4/5.
 */
public interface BannerAPI {

    @GET("banners/listBanners")
    Observable<Response<HomeBannerEntity>> getHomeBannerList();
}
