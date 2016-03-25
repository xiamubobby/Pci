package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.patient.mvp.entity.SendMessageEntity;
import com.wonders.xlab.patient.mvp.entity.request.SendMessageBody;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/3/17.
 */
public interface ChatRoomAPI {
    @GET("v1/imContents/listImChatHistory/{imGroupId}")
    Observable<ChatRoomEntity> getChatRecords(@Path("imGroupId") String imGroupId, @Query("page") int page,@Query("size") int size);

    @POST("v1/ims/sendContentToDoctor/{time}")
    Observable<SendMessageEntity> sendMessage(@Body SendMessageBody body, @Path("time") long time);
}
