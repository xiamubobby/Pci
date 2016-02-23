package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/2/22.
 */
public interface BSAPI {

    @GET("getBSList")
    Observable<BPEntity> getBSList();
}
