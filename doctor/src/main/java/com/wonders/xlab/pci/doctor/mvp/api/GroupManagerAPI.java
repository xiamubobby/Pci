package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.GroupDetailEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.GroupListEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/4/11.
 */
public interface GroupManagerAPI {

    @GET("v1/doctorGroup/queryAllGroupByDoctorId/{doctorId}")
    Observable<Response<GroupListEntity>> getGroupList(@Path("doctorId") String doctorId, @Query("page") int page,@Query("size") int size);

    @GET("v1/doctorGroup/toDoctorGroup/{doctorId}")
    Observable<Response<GroupDetailEntity>> getGroupDetail(@Path("doctorId") String doctorId,@Query("doctorGroupId") String doctorGroupId);
}
