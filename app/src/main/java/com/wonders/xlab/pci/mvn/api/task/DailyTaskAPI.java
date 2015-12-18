package com.wonders.xlab.pci.mvn.api.task;

import com.wonders.xlab.pci.mvn.entity.task.DailyTaskEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface DailyTaskAPI {

    @FormUrlEncoded
    @POST("dailyHealthTask/listDailyHealthTask")
    Observable<DailyTaskEntity> getDailyTask(@Field("userId") String userId,@Field("date") long date);
}
