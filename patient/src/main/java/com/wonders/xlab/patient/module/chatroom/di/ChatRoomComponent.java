package com.wonders.xlab.patient.module.chatroom.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.chatroom.ChatRoomPresenter;

import dagger.Component;

/**
 * Created by WZH on 16/6/17.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = ChatRoomModule.class)
public interface ChatRoomComponent {
    ChatRoomPresenter getChatRoomPresenter();
}
