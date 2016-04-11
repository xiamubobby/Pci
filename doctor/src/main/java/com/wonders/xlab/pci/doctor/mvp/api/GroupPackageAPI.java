package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.GroupPackageListEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/4/11.
 */
public interface GroupPackageAPI {

    @GET("v1/servicePackages/listServicePackages/{doctorGroupId}")
    Observable<Response<GroupPackageListEntity>> getPackageList(@Path("doctorGroupId") String doctorGroupId);
}
