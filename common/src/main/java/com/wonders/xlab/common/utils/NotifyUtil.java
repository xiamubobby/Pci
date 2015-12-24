package com.wonders.xlab.common.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;

/**
 * Created by hua on 15/12/24.
 */
public class NotifyUtil {
    public void showNotification(Context context,int id,String title, String message, Class targetClass, @DrawableRes int smallIconResId,boolean autoCancel) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(smallIconResId)
                .setContentTitle(title)
                .setContentText(message);
        Intent intent = new Intent(context, targetClass);
        Bundle data = new Bundle();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //使用context启动activity时使用
        intent.putExtras(data);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setAutoCancel(autoCancel);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
//        notification.setLatestEventInfo(context,"title","tag",pendingIntent);
        if (!autoCancel) {
            notification.flags = Notification.FLAG_NO_CLEAR;
        }
        //notification.defaults |= Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(id, notification);
    }

    public void cancelAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
