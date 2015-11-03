package me.shkschneider.skeleton.helper;

import android.app.Notification;
import android.app.PendingIntent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper {

    public static NotificationCompat.Builder Builder(final int color, final int icon, final String ticker, final String title, final String message, final PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(ApplicationHelper.context())
                .setSmallIcon(icon) // white-only for API-21+
                // .setLargeIcon(ApplicationHelper.icon())
                .setTicker(ticker) // ApplicationHelper.name()
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setColor(color) // API-21+
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                .setOngoing(false)
                .setAutoCancel(true);
    }

    public static void notify(@IntRange(from=0) final int id, @NonNull final NotificationCompat.Builder builder) {
        builder.setWhen(System.currentTimeMillis());
        SystemServices.notificationManager().notify(id, builder.build());
    }

    public static Uri defaultRingtone() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

}
