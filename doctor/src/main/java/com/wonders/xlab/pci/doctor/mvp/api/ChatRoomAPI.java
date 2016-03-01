package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/22.
 */
public interface ChatRoomAPI {
    @GET("imContent/listImChatHistory/{groupId}")
    Observable<ChatRoomEntity> getChatHistory(@Path("groupId") String groupId, @Query("page") int page,@Query("size") int size);

   /* {
        "target_type" : "chatgroups",
            "target" : ["166710012339552740"],


        "msg" : {
        "type" : "txt",
                "msg" : "测试看看"
    },
        "from" : "13621673988"
    }*/
    @POST("im/sendChatContent")
    Observable sendMessage();
}
