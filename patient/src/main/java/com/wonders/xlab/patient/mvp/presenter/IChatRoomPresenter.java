package com.wonders.xlab.patient.mvp.presenter;

import java.util.Map;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 */
public interface IChatRoomPresenter extends IBasePresenter {
    void getChatList(String groupId, boolean isRefresh);

    void sendMessage(Map<String,Object> ext, long time);
}
