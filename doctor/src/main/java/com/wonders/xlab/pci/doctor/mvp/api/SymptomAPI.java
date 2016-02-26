package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.SymptomCommentEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/2/24.
 */
public interface SymptomAPI {

    @GET("userSymptomRecords/listUserSymptomRecord/{userId}")
    Observable<SymptomEntity> getSymptomList(@Path("userId") String userId);

    @FormUrlEncoded
    @POST("userSymptomRecords/commentUserSymptom")
    Observable<SymptomCommentEntity> saveComment(@Field("recordId") String symptomId,@Field("doctorId") String doctorId,@Field("comment") String comment,@Field("check") boolean check);
}
