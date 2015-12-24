package com.wonders.xlab.pci.module.home.mvn.api;

import com.wonders.xlab.pci.mvn.entity.home.HomeEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/14.
 */
public interface HomeAPI {
    @FormUrlEncoded
    @POST("homePage/listHomePage")
    Observable<HomeEntity> getHomeList(@Field("userId") String userId,@Field("page") int page);
}
