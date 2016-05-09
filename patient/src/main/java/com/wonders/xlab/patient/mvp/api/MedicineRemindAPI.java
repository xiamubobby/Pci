package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.mvp.entity.MedicineRemindDetailEntity;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindListEntity;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import im.hua.library.base.mvp.entity.BaseEntity;
import retrofit2.Response;
import retrofit2.http.Body;
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

    @POST("v1/medicationReminders/createNewRemindersRecord/{userId}")
    Observable<Response<BaseEntity>> createMedicineRemind(@Path("userId") String userId, @Body MedicineRemindEditBody body);

    /**
     * 如果是修改，则body需要设置id
     * @param body
     * @return
     */
    @POST("v1/medicationReminders/editReminderRecord")
    Observable<Response<BaseEntity>> addOrModifyMedicineRemind(@Body MedicineRemindEditBody body);

    @POST("v1/medicationReminders/createNewRemindersRecord/{userId}")
    Observable<Response<BaseEntity>> deleteMedicineRemind(@Path("userId") String userId, @Body MedicineRemindEditBody body);

}
