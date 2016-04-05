package com.wonders.xlab.patient.util;

import android.support.annotation.NonNull;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.realm.UnreadMessageRealm;

import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by hua on 16/4/4.
 */
public class UnreadMessageUtil {
    public synchronized static void addNewUnread(@NonNull UnreadMessageRealm messageRealm) {
        try {
            XApplication.realm.beginTransaction();
            UnreadMessageRealm realm = XApplication.realm.copyToRealm(messageRealm);
            XApplication.realm.commitTransaction();
        } catch (RealmPrimaryKeyConstraintException exception) {
            exception.printStackTrace();
        }
    }

    public synchronized static void readMessage(String imGroupId) {
        try {
            XApplication.realm.beginTransaction();
            RealmResults<UnreadMessageRealm> realmResults = XApplication.realm.where(UnreadMessageRealm.class)
                    .equalTo("imGroupId", imGroupId)
                    .findAll();
            realmResults.clear();
            XApplication.realm.commitTransaction();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static int getUnreadMessageCounts(String imGroupId) throws UnsupportedOperationException {
        return (int) XApplication.realm.where(UnreadMessageRealm.class)
                .equalTo("imGroupId", imGroupId)
                .count();
    }

    public static int getAllUnreadMessageCounts() throws UnsupportedOperationException {
        return (int) XApplication.realm.where(UnreadMessageRealm.class)
                .count();
    }
}
