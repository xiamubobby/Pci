package com.wonders.xlab.pci.doctor.data.api;

import com.wonders.xlab.pci.doctor.data.entity.GroupPackageCreateEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupPackageDetailEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupPackageListEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupPackagePublishBody;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/4/11.
 */
public interface GroupPackageAPI {

    @GET("v1/servicePackages/listServicePackages/{ownerId}")
    Observable<Response<GroupPackageListEntity>> getPackageList(@Path("ownerId") String ownerId);

    @GET("v1/servicePackages/retrieveServicePackage/{ownerId}/{servicePackageId}")
    Observable<Response<GroupPackageDetailEntity>> getPackageDetail(@Path("ownerId") String ownerId, @Path("servicePackageId") String servicePackageId);

    @POST("v1/packages/publishDoctorPackage")
    Observable<Response<GroupPackageCreateEntity>> publishDoctorPackage(@Body GroupPackagePublishBody body);
}