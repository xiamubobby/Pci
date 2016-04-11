package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/22.
 */
public interface ChatRoomAPI {
    @GET("v1/imContent/listImChatHistory/{groupId}")
    Observable<Response<ChatRoomEntity>> getChatHistory(@Path("groupId") String groupId, @Query("page") int page, @Query("size") int size);
}
