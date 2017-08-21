package me.shkschneider.skeleton.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

/**
 * +------+------------------------------+
 * |      | contentTitle       timestamp |
 * | ICON | contentText                  |
 * |      | subText          contentInfo |
 * +------+------------------------------+
 * API-21+
 * +----------+--------------------------+
 * |          | contentTitle   timestamp |
 * |   ICON   | contentText              |
 * |     icon | subText      contentInfo |
 * +----------+--------------------------+
 * API-24+
 * +-------------------------------------+
 * | icon app - subText - timestamp      |
 * | contentTitle                        |
 * | contentText                         |
 * +-------------------------------------+
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

    public static class Builder extends NotificationCompat.Builder {

        public Builder(@NonNull final Channel channel) {
            super(ContextHelper.applicationContext(), channel.id);
            if (AndroidHelper.api() >= AndroidHelper.API_26) {
                notificationChannel26(channel);
            }
        }

        @Deprecated
        public Builder(@NonNull final String id) {
            super(ContextHelper.applicationContext(), id);
            // setShowWhen(AndroidHelper.api() < AndroidHelper.API_24);
            // setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            // setPriority(NotificationCompat.PRIORITY_DEFAULT);
            // setDefaults(NotificationCompat.DEFAULT_ALL);
            // setOngoing(false);
            // setAutoCancel(true);
        }

        @TargetApi(AndroidHelper.API_26)
        private void notificationChannel26(@NonNull final Channel channel) {
            if (Channel.get(channel.id) != null) {
                return;
            }
            final NotificationChannel notificationChannel = new NotificationChannel(channel.id, channel.name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationChannel.setShowBadge(channel.badge);
            notificationChannel.enableLights(channel.lights);
            notificationChannel.enableVibration(channel.vibration);
            SystemServices.notificationManager().createNotificationChannel(notificationChannel);
        }

        public Notification build() {
            return super.build();
        }

    }

    public static class Channel {

        public String id;
        public String name;
        public boolean badge;
        public boolean lights;
        public boolean vibration;

        public Channel(@NonNull final String id, @NonNull final String name) {
            this(id, name, true, true, true);
        }

        public Channel(@NonNull final String id, @NonNull final String name,
                       final boolean badge, final boolean lights, final boolean vibration) {
            this.id = id;
            this.name = name;
            this.badge = badge;
            this.lights = lights;
            this.vibration = vibration;
        }

        @TargetApi(AndroidHelper.API_26)
        public NotificationChannel get() {
            NotificationChannel notificationChannel = get(this.id);
            if (notificationChannel != null) {
                return notificationChannel;
            }
            notificationChannel = new NotificationChannel(this.id, this.name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationChannel.setShowBadge(this.badge);
            notificationChannel.enableLights(this.lights);
            notificationChannel.enableVibration(this.vibration);
            return notificationChannel;
        }

        @TargetApi(AndroidHelper.API_26)
        public static NotificationChannel get(@NonNull final String id) {
            return SystemServices.notificationManager().getNotificationChannel(id);
        }

        @TargetApi(AndroidHelper.API_26)
        public static void create(@NonNull final NotificationChannel notificationChannel) {
            SystemServices.notificationManager().createNotificationChannel(notificationChannel);
        }

        @TargetApi(AndroidHelper.API_26)
        public static void delete(@NonNull final String id) {
            SystemServices.notificationManager().deleteNotificationChannel(id);
        }

    }

    public static void notify(@IntRange(from=0) final int id, @NonNull final Notification notification) {
        // final String channelId = NotificationCompat.getChannelId(notification);
        notification.when = System.currentTimeMillis(); // ?
        SystemServices.notificationManager().notify(id, notification);
    }

}
