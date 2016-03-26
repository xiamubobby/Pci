package com.wonders.xlab.pci.module.task.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.SimpleEntity;
import com.wonders.xlab.pci.module.base.mvn.entity.task.DailyTaskEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface DailyTaskAPI {

    @FormUrlEncoded
    @POST("dailyHealthTask/listDailyHealthTask")
    Observable<DailyTaskEntity> getDailyTask(@Field("userId") String userId,@Field("date") Long date);

    @FormUrlEncoded
    @POST("userMedicine/takeMedicine")
    Observable<SimpleEntity> takeMedicine(@Field("medicineId") String medicineId);
}
