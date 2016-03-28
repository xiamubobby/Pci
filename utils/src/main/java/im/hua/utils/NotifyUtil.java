package im.hua.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by hua on 15/12/24.
 */
public class NotifyUtil {
    public void showNotification(Context context, Class targetClass, Bundle data, int id, String title, String message, @DrawableRes int smallIconResId, boolean autoCancel, boolean vibrate, boolean hasParent, int color) {

        Notification notification = generateNotification(context, targetClass, data, title, message, smallIconResId, autoCancel, vibrate, hasParent, color);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }

    /**
     * 创建一个Notification
     * @param context
     * @param targetClass
     * @param data
     *@param title
     * @param message
     * @param smallIconResId
     * @param autoCancel
     * @param vibrate
     * @param hasParent
     * @param color        @return
     */
    public Notification generateNotification(Context context, Class targetClass, Bundle data, String title, String message, @DrawableRes int smallIconResId, boolean autoCancel, boolean vibrate, boolean hasParent, int color) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(smallIconResId)
                .setContentTitle(title)
                .setColor(color)
                .setContentText(message);
        if (vibrate) {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }

        Intent intent = new Intent(context, targetClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //使用context启动activity时使用
        if (null != data) {
            intent.putExtras(data);
        }

        PendingIntent pendingIntent;
        if (hasParent) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(targetClass);
            stackBuilder.addNextIntent(intent);

            pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        builder.setAutoCancel(autoCancel);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
//        notification.setLatestEventInfo(context,"title","tag",pendingIntent);
        if (!autoCancel) {
            notification.flags = Notification.FLAG_NO_CLEAR;
        }
        //notification.defaults |= Notification.FLAG_ONGOING_EVENT;
        return notification;
    }

    public void cancelAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public void cancel(Context context,int notifyId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notifyId);
    }
}
