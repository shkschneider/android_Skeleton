package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

/**
 * +------+-----------------------------------------+
 * |      | contentTitle                  timestamp |
 * | ICON | contentText                             |
 * |      | subText                     contentInfo |
 * +------+-----------------------------------------+
 * API-21+
 * +----------+-------------------------------------+
 * |          | contentTitle              timestamp |
 * |   ICON   | contentText                         |
 * |          | subText                 contentInfo |
 * +----------+-------------------------------------+
 * API-24+
 * +------------------------------------------------+
 * | ICON AppName - subText contentInfo - timestamp |
 * | contentTitle                                   |
 * | contentText                                    |
 * +------------------------------------------------+
 */
public class NotificationHelper {

    public static NotificationCompat.Builder Builder(@ColorInt final int color, @DrawableRes final int smallIcon, final Bitmap largeIcon,
                                                     final String ticker,
                                                     @NonNull final String contentTitle, @NonNull final String contentText, final String subText, final String contentInfo,
                                                     @NonNull final PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(ApplicationHelper.context())
                .setSmallIcon(smallIcon)
                .setLargeIcon(largeIcon)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSubText(subText)
                .setContentInfo(contentInfo)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                // .setShowWhen(true|false)
                .setColor(color)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOngoing(false)
                .setAutoCancel(true);
    }

    public static NotificationCompat.Builder Builder(@ColorInt final int color, @DrawableRes final int smallIcon, final Bitmap largeIcon,
                                                     final String ticker,
                                                     @NonNull final String contentTitle, @NonNull final String contentText, final String subText, final String contentInfo,
                                                     @NonNull final Activity activity, Intent intent) {
        if (intent == null) {
            intent = new Intent(activity, activity.getClass());
        }
        final PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return Builder(color, smallIcon, largeIcon,
                ticker,
                contentTitle, contentText, subText, contentInfo,
                pendingIntent);
    }

    @Deprecated // Avoid
    public static void notify(@IntRange(from=0) final int id, @NonNull final Notification notification) {
        SystemServices.notificationManager().notify(id, notification);
    }

    public static void notify(@IntRange(from=0) final int id, @NonNull final NotificationCompat.Builder builder) {
        builder.setWhen(System.currentTimeMillis());
        SystemServices.notificationManager().notify(id, builder.build());
    }

}
