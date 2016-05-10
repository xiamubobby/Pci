package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.mvp.entity.MedicineRemindDetailEntity;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindListEntity;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import im.hua.library.base.mvp.entity.EmptyValueEntity;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface MedicineRemindAPI {
    @GET("v1/medicationReminders/queryAllRemindersRecordByUserId/{userId}")
    Observable<Response<MedicineRemindListEntity>> getMedicineRemindList(@Path("userId") String userId, @Query("page") int page, @Query("size") int size);

    @GET("v1/medicationReminders/queryDetailByRemindersRecordId/{remindersRecordId}")
    Observable<Response<MedicineRemindDetailEntity>> getMedicineRemindDetail(@Path("remindersRecordId") String remindersRecordId);

    /**
     * body不需要设置id
     * @param userId
     * @param body
     * @return
     */
    @POST("v1/medicationReminders/createNewRemindersRecord/{userId}")
    Observable<Response<EmptyValueEntity>> createMedicineRemind(@Path("userId") String userId, @Body MedicineRemindEditBody body);

    /**
     * body需要设置id
     * @param body
     * @return
     */
    @POST("v1/medicationReminders/editReminderRecord")
    Observable<Response<EmptyValueEntity>> modifyMedicineRemind(@Body MedicineRemindEditBody body);

    @POST("v1/medicationReminders/createNewRemindersRecord/{userId}")
    Observable<Response<EmptyValueEntity>> deleteMedicineRemind(@Path("userId") String userId, @Body MedicineRemindEditBody body);

    @FormUrlEncoded
    @POST("v1/medicationReminders/changeManualCloseReminder/{remindersRecordId}")
    Observable<Response<EmptyValueEntity>> changeRemindState(@Path("remindersRecordId") String remindersRecordId, @Field("manualCloseReminder") boolean manualCloseReminder);
}
