package com.wonders.xlab.pci.module.message.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.home.ChatEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/14.
 */
public interface ChatAPI {
    @FormUrlEncoded
    @POST("homePage/listHomePage")
    Observable<ChatEntity> getChatList(@Field("userId") String userId, @Field("page") int page);
}
