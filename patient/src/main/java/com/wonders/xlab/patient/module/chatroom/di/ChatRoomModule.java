package com.wonders.xlab.patient.module.chatroom.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.chatroom.ChatRoomContract;
import com.wonders.xlab.patient.mvp.api.ChatRoomAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by WZH on 16/6/16.
 */
@Module
public class ChatRoomModule {
    private ChatRoomContract.ViewListener mViewListener;

    public ChatRoomModule(ChatRoomContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    ChatRoomContract.ViewListener provideChatRoomViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    ChatRoomAPI provideChatRoomAPI(Retrofit retrofit) {
        return retrofit.create(ChatRoomAPI.class);
    }
}
