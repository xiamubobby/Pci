package com.wonders.xlab.patient.data.api;

import com.wonders.xlab.patient.data.entity.DoctorAllEntity;
import com.wonders.xlab.patient.data.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.data.entity.DoctorGroupDetailEntity;
import com.wonders.xlab.patient.data.entity.DoctorMyEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/3/17.
 */
public interface DoctorAPI {
    /**
     * 我的医生
     * @param patientId
     * @param page
     * @param size
     * @return
     */
    @GET("v1/doctors/listDoctorGroups/{patientId}")
    Observable<Response<DoctorMyEntity>> getMyDoctors(@Path("patientId") String patientId, @Query("page") int page, @Query("size") int size);

    /**
     * 所有医生
     * @param patientId
     * @param page
     * @param size
     * @return
     */
    @GET("v1/doctors/listAllDoctors/{patientId}")
    Observable<Response<DoctorAllEntity>> getAllDoctors(@Path("patientId") String patientId, @Query("page") int page,@Query("size") int size);

    /**
     * 获取医生小组信息（包括个人和多人的）
     * @param ownerId
     * @return
     */
    @GET("v1/doctors/retrieveDoctorGroupInfo/{patientId}/{ownerId}")
    Observable<Response<DoctorGroupDetailEntity>> getDoctorGroupDetailInfo(@Path("patientId") String patientId,@Path("ownerId") String ownerId);

    /**
     * 获取医生个人信息
     * @param doctorId
     * @return
     */
    @GET("v1/doctors/retrieveDoctorInfo/{patientId}/{doctorId}")
    Observable<Response<DoctorDetailEntity>> getDoctorDetailInfo(@Path("patientId") String patientId,@Path("doctorId") String doctorId);
}
