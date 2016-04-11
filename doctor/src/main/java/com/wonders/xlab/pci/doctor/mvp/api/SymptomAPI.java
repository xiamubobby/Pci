package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.SymptomCommentEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/2/24.
 */
public interface SymptomAPI {

    @GET("v1/userSymptomRecords/listUserSymptomRecord/{userId}")
    Observable<Response<SymptomEntity>> getSymptomList(@Path("userId") String userId);

    @FormUrlEncoded
    @POST("v1/userSymptomRecords/commentUserSymptom")
    Observable<Response<SymptomCommentEntity>> saveComment(@Field("recordId") String symptomId,@Field("doctorId") String doctorId,@Field("comment") String comment,@Field("check") boolean check);
}
