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
 * +------+-------------------------------------+
 * |      | contentTitle              timestamp |
 * | ICON | contentText                         |
 * |      | subText                 contentInfo |
 * +------+-------------------------------------+
 * API-21+
 * +----------+---------------------------------+
 * |          | contentTitle          timestamp |
 * |   ICON   | contentText                     |
 * |     icon | subText             contentInfo |
 * +----------+---------------------------------+
 * API-24+
 * +--------------------------------------------+
 * | icon app - subText contentInfo - timestamp |
 * | contentTitle                               |
 * | contentText                                |
 * +--------------------------------------------+
 */
public class NotificationHelper {

    public static PendingIntent pendingIntent(@NonNull final Activity activity, Intent intent) {
        if (intent == null) {
            intent = new Intent(activity, activity.getClass());
        }
        if (intent.getFlags() == 0) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        return PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static NotificationCompat.Builder Builder(@ColorInt final int color, @DrawableRes final int smallIcon, final Bitmap largeIcon,
                                                     final String ticker,
                                                     @NonNull final String contentTitle, @NonNull final String contentText, final String subText, final String contentInfo,
                                                     @NonNull final PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(ContextHelper.applicationContext())
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
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setOngoing(false)
                .setAutoCancel(true);
    }

    @SuppressWarnings("deprecation")
    public static void notify(@IntRange(from=0) final int id, @NonNull final NotificationCompat.Builder builder) {
        builder.setWhen(System.currentTimeMillis());
        notify(id, builder.build());
    }

    @Deprecated // Avoid
    public static void notify(@IntRange(from=0) final int id, @NonNull final Notification notification) {
        SystemServices.notificationManager().notify(id, notification);
    }

}
