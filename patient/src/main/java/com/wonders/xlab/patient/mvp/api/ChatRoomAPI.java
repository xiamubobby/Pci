package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.ChatRoomEntity;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/3/17.
 */
public interface ChatRoomAPI {
    @GET("")
    Observable<ChatRoomEntity> getChatRecords(String groupId);

    Observable<SimpleEntity> sendMessage();
}
