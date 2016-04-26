package com.wonders.xlab.patient.data.api;


import com.wonders.xlab.patient.data.entity.SymptomEntity;
import com.wonders.xlab.patient.data.entity.SymptomRetrieveEntity;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomAPI {
    /**
     * 获取不适症状字典表
     *
     * @return
     */
    @GET("symptom/retrieveSymptom")
    Observable<Response<SymptomEntity>> getSymptomDicList();

    /**
     * 获取不适症状（每日记录界面）
     *
     * @param userId
     * @param startTimeInMill
     * @param endTimeInMill
     * @param page
     *@param size @return
     */
    @GET("v1/symptoms/listUserSymptoms")
    Observable<Response<SymptomRetrieveEntity>> getSymptomList(@Query("userId") String userId, @Query("start") long startTimeInMill, @Query("end") long endTimeInMill,@Query("page") int page,@Query("size") int size);

    @FormUrlEncoded
    @POST("v1/symptoms/saveUserSymptom")
    Observable<Response<SimpleEntity>> saveSymptom(@Field("userId") String userId, @Field("symptomIdsStr") String[] symptomIdsStr);

}
