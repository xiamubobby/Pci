package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/2/22.
 */
public interface ChatRoomAPI {
    @GET("getChatHistory")
    Observable<ChatRoomEntity> getChatHistory();
}
