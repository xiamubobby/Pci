package com.wonders.xlab.pci.module.message.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.home.ChatEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/14.
 */
public interface ChatAPI {
    @FormUrlEncoded
    @POST("homePage/listHomePage")
    Observable<ChatEntity> getChatList(@Field("userId") String userId, @Field("page") int page);
}
