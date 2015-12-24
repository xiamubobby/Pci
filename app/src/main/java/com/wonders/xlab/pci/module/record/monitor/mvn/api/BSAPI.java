package com.wonders.xlab.pci.module.record.monitor.mvn.api;

import com.wonders.xlab.pci.module.record.monitor.mvn.entity.BSEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by sjy on 2015/12/23.
 */
public interface BSAPI {
    @FormUrlEncoded
    @POST("userBloodSugar/listUserBloodSugar")
    Observable<BSEntity> getBp(@Field("userId") String userId);

}
