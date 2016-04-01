package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.patient.mvp.entity.SendMessageEntity;
import com.wonders.xlab.patient.mvp.entity.request.SendMessageBody;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/3/17.
 */
public interface ChatRoomAPI {
    @GET("v1/imContents/listImChatHistory/{imGroupId}")
    Observable<Response<ChatRoomEntity>> getChatRecords(@Path("imGroupId") String imGroupId, @Query("page") int page, @Query("size") int size);

    @POST("v1/ims/sendContentToDoctor/{time}")
    Observable<Response<SendMessageEntity>> sendMessage(@Body SendMessageBody body, @Path("time") long time);
}
