package com.wonders.xlab.pci.doctor.data.api;

import com.wonders.xlab.pci.doctor.data.entity.GroupAuthMembersEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupAuthorizeEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupCreateEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupDetailEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupDoctorSaveEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupDoctorUpdateMemberEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupListEntity;
import com.wonders.xlab.pci.doctor.data.entity.GroupMembersEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupAuthorizeBody;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateBasicInfoBody;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateMemberBody;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/4/11.
 */
public interface GroupManagerAPI {

    @GET("v1/doctorGroup/queryAllGroupByDoctorId/{doctorId}")
    Observable<Response<GroupListEntity>> getGroupList(@Path("doctorId") String doctorId, @Query("page") int page, @Query("size") int size);

    @GET("v1/doctorGroup/toDoctorGroup/{doctorId}")
    Observable<Response<GroupDetailEntity>> getGroupDetail(@Path("doctorId") String doctorId, @Query("ownerId") String ownerId);

    @POST("v1/doctorGroup/saveDoctorGroup/{doctorId}")
    Observable<Response<GroupCreateEntity>> updateDoctorGroup(@Path("doctorId") String doctorId, @Body GroupUpdateBasicInfoBody body);

    @POST("v1/doctorGroup/inviteDoctorJoinGroup/{doctorId}")
    Observable<Response<GroupDoctorUpdateMemberEntity>> inviteDoctorToGroup(@Path("doctorId") String doctorId, @Body GroupUpdateMemberBody body);

    @POST("v1/doctorGroup/removeDoctorFromGroup/{doctorId}")
    Observable<Response<GroupDoctorUpdateMemberEntity>> removeDoctorFromGroup(@Path("doctorId") String doctorId, @Body GroupUpdateMemberBody body);

    @GET("v1/doctorGroup/queryDoctorByTelOrName/{doctorId}")
    Observable<Response<GroupDoctorSaveEntity>> searchDoctorByTelOrName(@Path("doctorId") String doctorId, @Query("doctorGroupId") String doctorGroupId, @Query("tel") String tel, @Query("name") String name);

    /**
     * 可授权医生列表
     *
     * @param doctorGroupId
     * @return
     */
    @GET("v1/doctorGroup/queryGrantDoctor/{doctorId}/{doctorGroupId}")
    Observable<Response<GroupAuthMembersEntity>> getAuthMemberList(@Path("doctorId") String doctorId, @Path("doctorGroupId") String doctorGroupId);

    @POST("v1/doctorGroup/grantToDoctor/{doctorGroupId}/{grantDoctorId}")
    Observable<Response<GroupAuthorizeEntity>> doGroupMemberAuthorize(@Path("doctorGroupId") String doctorGroupId, @Path("grantDoctorId") String doctorId, @Body GroupAuthorizeBody body);

    /**
     * 小组成员列表，除了自己和创建者
     *
     * @param doctorId
     * @param doctorGroupId
     * @return
     */
    @GET("v1/doctorGroup/getGroupMember/{doctorId}")
    Observable<Response<GroupMembersEntity>> getMemberList(@Path("doctorId") String doctorId, @Query("doctorGroupId") String doctorGroupId);

}
