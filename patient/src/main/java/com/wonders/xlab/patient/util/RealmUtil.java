package com.wonders.xlab.patient.util;

import com.wonders.xlab.patient.application.XApplication;

import io.realm.RealmResults;

/**
 * Created by hua on 16/5/27.
 */
public class RealmUtil {
    public static void deleteRealm(Class clazz) {
        XApplication.realm.beginTransaction();
        RealmResults realmResults = XApplication.realm.where(clazz).findAll();
        realmResults.deleteAllFromRealm();
        XApplication.realm.commitTransaction();
    }
}
