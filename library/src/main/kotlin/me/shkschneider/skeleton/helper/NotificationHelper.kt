package me.shkschneider.skeleton.helper

import android.app.*
import android.content.Intent
import android.os.Build
import android.support.annotation.IntRange
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat

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
object NotificationHelper {

    fun pendingIntent(activity: Activity, intent: Intent): PendingIntent {
        if (intent.flags == 0) {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        return PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun pendingIntent(activity: Activity): PendingIntent {
        return pendingIntent(activity, Intent(activity, activity::class.java))
    }

    class Builder : NotificationCompat.Builder {

        constructor(channel: Channel) : super(ContextHelper.applicationContext(), channel.id) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (Channel.get(channel.id) != null) {
                    return
                }
                with(NotificationChannel(channel.id, channel.name, NotificationManager.IMPORTANCE_DEFAULT)) {
                    lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                    setShowBadge(channel.badge)
                    enableLights(channel.lights)
                    enableVibration(channel.vibration)
                    SystemServices.notificationManager()?.createNotificationChannel(this)
                }
            }
        }

        @Deprecated("Use a NotificationChannel.")
        constructor(id: String) : super(ContextHelper.applicationContext(), id) {
            setChannelId(id)
            // setShowWhen(AndroidHelper.api() < AndroidHelper.API_24);
            // setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            // setPriority(NotificationCompat.PRIORITY_DEFAULT);
            // setDefaults(NotificationCompat.DEFAULT_ALL);
            // setOngoing(false);
            // setAutoCancel(true);
        }

        override fun build(): Notification {
            return super.build()
        }

    }

    class Channel {

        val id: String
        val name: String
        val badge: Boolean
        val lights: Boolean
        val vibration: Boolean

        constructor(id: String, name: String, badge: Boolean = true, lights: Boolean = true, vibration: Boolean = true) {
            this.id = id
            this.name = name
            this.badge = badge
            this.lights = lights
            this.vibration = vibration
        }

        @RequiresApi(AndroidHelper.API_26)
        fun get(): NotificationChannel {
            var notificationChannel = get(id)
            if (notificationChannel != null) {
                return notificationChannel
            }
            notificationChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
            with (notificationChannel) {
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                setShowBadge(badge)
                enableLights(lights)
                enableVibration(vibration)
                return this
            }
        }

        companion object {

            @RequiresApi(AndroidHelper.API_26)
            fun get(id: String): NotificationChannel? {
                return SystemServices.notificationManager()?.getNotificationChannel(id)
            }

            @RequiresApi(AndroidHelper.API_26)
            fun create(notificationChannel: NotificationChannel) {
                SystemServices.notificationManager()?.createNotificationChannel(notificationChannel)
            }

            @RequiresApi(AndroidHelper.API_26)
            fun delete(id: String) {
                SystemServices.notificationManager()?.deleteNotificationChannel(id)
            }

        }

    }

    fun notify(@IntRange(from = 0) id: Int, notification: Notification) {
        // final String channelId = NotificationCompat.getChannelId(notification);
        notification.`when` = DateTimeHelper.timestamp() // FIXME when keyword
        SystemServices.notificationManager()?.notify(id, notification)
    }

}
