package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;
import com.wonders.xlab.patient.mvp.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.mvp.entity.DoctorGroupDetailEntity;
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

    /**
     * 获取医生小组信息（包括个人和多人的）
     * @param doctorGroupId
     * @return
     */
    @GET("v1/doctors/retrieveDoctorGroupInfo/{groupId}")
    Observable<DoctorGroupDetailEntity> getDoctorGroupDetailInfo(@Path("groupId") String doctorGroupId);

    /**
     * 获取医生个人信息
     * @param doctorId
     * @return
     */
    @GET("v1/doctors/retrieveDoctorInfo/{doctorId}")
    Observable<DoctorDetailEntity> getDoctorDetailInfo(@Path("doctorId") String doctorId);
}
