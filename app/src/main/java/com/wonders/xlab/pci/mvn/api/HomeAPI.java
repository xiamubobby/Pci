package com.wonders.xlab.pci.mvn.api;

import com.wonders.xlab.pci.mvn.entity.home.HomeEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 15/12/14.
 */
public interface HomeAPI {
    @GET("homePage/listHomePage/1")
    Observable<HomeEntity> getHomeList();
}
