package com.wonders.xlab.pci.doctor.util;

import android.support.annotation.NonNull;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.doctor.application.XApplication;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiOthersBean;
import com.wonders.xlab.pci.doctor.module.me.notification.otto.NotifiGroupInviteOtto;
import com.wonders.xlab.pci.doctor.module.me.notification.otto.NotifiOthersOtto;
import com.wonders.xlab.pci.doctor.module.otto.MainBottomUnreadNotifyCountOtto;
import com.wonders.xlab.pci.doctor.realm.NotifiGroupInviteRealm;
import com.wonders.xlab.pci.doctor.realm.NotifiOthersRealm;
import com.wonders.xlab.pci.doctor.realm.UnReadMessageRealm;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by hua on 16/4/4.
 */
public class RealmUtil {
    private final static Object objectChatMessage = new Object();
    private final static Object objectGroupInviteNotifi = new Object();
    private final static Object objectNotifiOthersNotifi = new Object();

    public static void addNewUnread(@NonNull UnReadMessageRealm messageRealm) {
        try {
            synchronized (objectChatMessage) {
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
            synchronized (objectChatMessage) {
                XApplication.realm.beginTransaction();
                RealmResults<UnReadMessageRealm> realmResults = XApplication.realm.where(UnReadMessageRealm.class)
                        .equalTo("imGroupId", imGroupId)
                        .findAll();
                realmResults.clear();
                XApplication.realm.commitTransaction();
                //发送Event事件
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

    public static void saveGroupInviteNotifi(NotifiGroupInviteRealm inviteRealm) {
        try {
            synchronized (objectGroupInviteNotifi) {
                XApplication.realm.beginTransaction();
                XApplication.realm.copyToRealm(inviteRealm);
                XApplication.realm.commitTransaction();
                //发送Event事件
                OttoManager.post(new NotifiGroupInviteOtto());
            }
        } catch (RealmPrimaryKeyConstraintException exception) {
            exception.printStackTrace();
        }
    }

    public static List<NotifiGroupInviteRealm> getGroupInviteNotifis(int page, int size) {
        List<NotifiGroupInviteRealm> realmList = new ArrayList<>();

        RealmResults<NotifiGroupInviteRealm> tmp = XApplication.realm.where(NotifiGroupInviteRealm.class)
                .findAllSorted("recordTimeInMill", Sort.DESCENDING);

        if (page * size >= tmp.size()) {
            return realmList;
        }
        for (int i = page * size; i < Math.min(tmp.size(), (page + 1) * size); i++) {
            realmList.add(tmp.get(i));
        }
        return realmList;
    }

    /**
     * 保存其他通知
     *
     * @param realm
     */
    public static void saveOthersNotifi(NotifiOthersRealm realm) {
        try {
            XApplication.realm.beginTransaction();
            XApplication.realm.copyToRealm(realm);
            XApplication.realm.commitTransaction();
            //发送Event事件
            OttoManager.post(new NotifiOthersOtto());
        } catch (RealmPrimaryKeyConstraintException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 分页获取其他通知列表
     *
     * @param page 页码
     * @param size 每页数量
     * @return
     */
    public static List<NotifiOthersRealm> getOthersNotifis(int page, int size) {
        List<NotifiOthersRealm> realmList = new ArrayList<>();

        RealmResults<NotifiOthersRealm> tmp = XApplication.realm.where(NotifiOthersRealm.class)
                .findAllSorted("receiveTimeInMill", Sort.DESCENDING);

        if (page * size >= tmp.size()) {
            return realmList;
        }

        for (int i = page * size; i < Math.min(tmp.size(), (page + 1) * size); i++) {
            realmList.add(tmp.get(i));
        }
        return realmList;
    }

    public static void deleteSelectedOthersNotification(@NonNull List<NotifiOthersBean> notifiOthersBeanList) {
        synchronized (objectNotifiOthersNotifi) {
            XApplication.realm.beginTransaction();
            for (NotifiOthersBean bean : notifiOthersBeanList) {
                RealmResults<NotifiOthersRealm> realmResults = XApplication.realm.where(NotifiOthersRealm.class)
                        .equalTo("receiveTimeInMill", bean.recordTimeInMill.get())
                        .findAll();
                realmResults.clear();
            }
            XApplication.realm.clear(NotifiOthersRealm.class);
            XApplication.realm.commitTransaction();
        }
    }
}
