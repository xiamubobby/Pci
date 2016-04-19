package com.wonders.xlab.pci.doctor.data.api;

import com.wonders.xlab.pci.doctor.data.entity.AgreeJoinDoctorGroupEntity;
import com.wonders.xlab.pci.doctor.data.entity.NotifiGroupInviteEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/4/18.
 */
public interface NotificationAPI {
    @GET("v1/doctorGroup/queryInvitedDoctorRecord/{doctorInvitedId}")
    Observable<Response<NotifiGroupInviteEntity>> getGroupInviteNotifications(@Path("doctorInvitedId") String doctorInvitedId, @Query("page") int page, @Query("size") int size);

    @POST("v1/doctorGroup/agreeJoinDoctorGroup/{doctorId}/{doctorGroupId}")
    Observable<Response<AgreeJoinDoctorGroupEntity>> agreeJoinDoctorGroup(@Path("doctorId") String doctorId, @Path("doctorGroupId") String doctorGroupId);
}
