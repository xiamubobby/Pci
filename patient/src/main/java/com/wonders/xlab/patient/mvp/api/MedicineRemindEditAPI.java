package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import im.hua.library.base.mvp.entity.BaseEntity;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by WZH on 16/5/5.
 */
public interface MedicineRemindEditAPI {

    @POST("v1/medicationReminders/createNewRemindersRecord/{userId}")
    Observable<Response<BaseEntity>> createMedicineRemind(@Path("userId") String userId, @Body MedicineRemindEditBody body);

    @POST("v1/medicationReminders/editReminderRecord")
    Observable<Response<BaseEntity>> editMedicineRemind(@Body MedicineRemindEditBody body);
}
