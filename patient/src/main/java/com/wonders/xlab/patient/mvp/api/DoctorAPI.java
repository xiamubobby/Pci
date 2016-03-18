package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;
import com.wonders.xlab.patient.mvp.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/3/17.
 */
public interface DoctorAPI {
    @GET("v1/doctors/listDoctorGroups/{patientId}")
    Observable<DoctorMyEntity> getMyDoctors(@Path("patientId") String patientId);

    @GET("v1/doctors/listAllDoctors/{patientId}")
    Observable<DoctorAllEntity> getAllDoctors(@Path("patientId") String patientId);

    @GET("v1/doctors/retrieveDoctorGroupInfo/{groupId}")
    Observable<DoctorDetailEntity> getDoctorGroupDetailInfo(@Path("groupId") String doctorGroupId);
}
