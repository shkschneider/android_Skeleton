package me.shkschneider.skeleton.helper

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import me.shkschneider.skeleton.extensions.android.Intent
import me.shkschneider.skeleton.helperx.SystemServices
import me.shkschneider.skeleton.kotlinx.DateTime
import java.util.concurrent.TimeUnit

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
        return pendingIntent(activity, Intent(activity, activity::class))
    }

    class Builder : NotificationCompat.Builder {

        constructor(channel: Channel) : super(ContextHelper.applicationContext(), channel.id) {
            if (Build.VERSION.SDK_INT >= 26) {
                Channel.get(channel.id) ?: return
                NotificationChannel(channel.id, channel.name, NotificationManager.IMPORTANCE_DEFAULT).run {
                    lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                    setShowBadge(channel.badge)
                    enableLights(channel.lights)
                    enableVibration(channel.vibration)
                    SystemServices.notificationManager()?.createNotificationChannel(this)
                }
            }
            // For apps targeting N and above, this time is not shown anymore by default.
            if (Build.VERSION.SDK_INT < 24) {
                setShowWhen(false)
            }
            // OnClick
            setAutoCancel(true)
        }

        override fun setOngoing(ongoing: Boolean): NotificationCompat.Builder {
            if (ongoing) {
                setShowWhen(false)
            }
            return super.setOngoing(ongoing)
        }

        fun setWhenMilliseconds(ms: Long): NotificationCompat.Builder {
            return super.setWhen(ms)
        }

        fun setWhenSeconds(s: Long): NotificationCompat.Builder {
            return super.setWhen(TimeUnit.SECONDS.toMillis(s))
        }

        override fun setUsesChronometer(b: Boolean): NotificationCompat.Builder {
            return super.setUsesChronometer(b)
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

    class Channel(
            val id: String,
            val name: String,
            val badge: Boolean = true,
            val lights: Boolean = true,
            val vibration: Boolean = true
    ) {

        @RequiresApi(AndroidHelper.API_26)
        fun get(): NotificationChannel {
            get(id)?.let { notificationChannel ->
                return notificationChannel
            }
            with(NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)) {
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

    fun notify(@IntRange(from = 0) id: Int, notification: Notification, ms: Long? = null) {
        notification.`when` = ms ?: DateTime.now.millis
        SystemServices.notificationManager()?.notify(id, notification)
    }

}
