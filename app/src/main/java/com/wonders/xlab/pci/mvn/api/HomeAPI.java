package com.wonders.xlab.pci.mvn.api;

import com.wonders.xlab.pci.mvn.entity.home.HomeEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 15/12/14.
 */
public interface HomeAPI {
    @GET("home")
    Observable<HomeEntity> getHomeList();
}
