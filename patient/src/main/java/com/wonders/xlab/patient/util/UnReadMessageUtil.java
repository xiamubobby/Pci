package com.wonders.xlab.patient.util;

import android.support.annotation.NonNull;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.data.realm.UnReadMessageRealm;
import com.wonders.xlab.patient.module.otto.MainBottomUnreadNotifyCountOtto;

import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by hua on 16/4/4.
 */
public class UnReadMessageUtil {
    private final static Object object = new Object();

    public static void addNewUnread(@NonNull UnReadMessageRealm messageRealm) {
        try {
            synchronized (object) {
                XApplication.realm.beginTransaction();
                UnReadMessageRealm realm = XApplication.realm.copyToRealm(messageRealm);
                XApplication.realm.commitTransaction();
                //发送Event事件
                OttoManager.post(new MainBottomUnreadNotifyCountOtto(messageRealm.getImGroupId()));
            }
        } catch (RealmPrimaryKeyConstraintException exception) {
            exception.printStackTrace();
        }
    }

    public static void readMessage(String imGroupId) {
        try {
            synchronized (object) {
                RealmUtil.deleteRealmResults(XApplication.realm.where(UnReadMessageRealm.class)
                        .equalTo("imGroupId", imGroupId)
                        .findAll());
                OttoManager.post(new MainBottomUnreadNotifyCountOtto(imGroupId));
            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static int getUnreadMessageCounts(String imGroupId) throws UnsupportedOperationException {
        return (int) XApplication.realm.where(UnReadMessageRealm.class)
                .equalTo("imGroupId", imGroupId)
                .count();
    }

    public static int getAllUnreadMessageCounts() throws UnsupportedOperationException {
        return (int) XApplication.realm.where(UnReadMessageRealm.class)
                .count();
    }
}
