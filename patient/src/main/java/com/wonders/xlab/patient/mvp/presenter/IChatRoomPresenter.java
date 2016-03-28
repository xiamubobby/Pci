package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 */
public interface IChatRoomPresenter extends IBasePresenter {
    void getChatList(String groupId, boolean isRefresh);

    void sendMessage(String message, String patientTel, String imGroupId, String groupId, String groupName, long time);
}
