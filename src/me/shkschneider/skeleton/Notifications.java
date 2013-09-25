package me.shkschneider.skeleton;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

@SuppressWarnings("unused")
public class Notifications {

    public static void toastShort(final Context context, final String text) {
        if (context != null) {
            if (! TextUtils.isEmpty(text)) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
            else {
                Log.w("String was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
    }

    public static void toastLong(final Context context, final String text) {
        if (context != null) {
            if (! TextUtils.isEmpty(text)) {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
            else {
                Log.w("String was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
    }

    public static void croutonInfo(final Activity activity, final String text) {
        if (activity != null) {
            if (! TextUtils.isEmpty(text)) {
                Crouton.makeText(activity, text, Style.INFO).show();
            }
            else {
                Log.w("String was NULL");
            }
        }
        else {
            Log.w("Activity was NULL");
        }
    }

    public static void croutonConfirm(final Activity activity, final String text) {
        if (activity != null) {
            if (! TextUtils.isEmpty(text)) {
                Crouton.makeText(activity, text, Style.CONFIRM).show();
            }
            else {
                Log.w("String was NULL");
            }
        }
        else {
            Log.w("Activity was NULL");
        }
    }

    public static void croutonAlert(final Activity activity, final String text) {
        if (activity != null) {
            if (! TextUtils.isEmpty(text)) {
                Crouton.makeText(activity, text, Style.ALERT).show();
            }
            else {
                Log.w("String was NULL");
            }
        }
        else {
            Log.w("Activity was NULL");
        }
    }

    public static void onDestroy(final Activity activity) {
        if (activity != null) {
            Crouton.clearCroutonsForActivity(activity);
        }
        else {
            Log.w("Activity was NULL");
        }
    }

    public static NotificationManager notificationManager(final Context context) {
        if (context != null) {
            return (NotificationManager) Systems.systemService(context, Systems.SYSTEM_SERVICE_NOTIFICATION_SERVICE);
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static Notification notification(final Context context, final int smallIcon, final String title, final String message, final PendingIntent pendingIntent) {
        if (context != null) {
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
            notificationBuilder.setSmallIcon(smallIcon);
            if (! TextUtils.isEmpty(title)) {
                notificationBuilder.setContentTitle(title);
            }
            if (! TextUtils.isEmpty(message)) {
                notificationBuilder.setContentText(message);
            }
            if (pendingIntent != null) {
                notificationBuilder.setContentIntent(pendingIntent);
            }
            final Notification notification = notificationBuilder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            return notification;
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static Notification notification(final Context context, final int smallIcon, final String title, final String message) {
        return notification(context, smallIcon, title, message, null);
    }

    public static void notify(final NotificationManager notificationManager, final Notification notification, final Integer id) {
        if (notificationManager != null) {
            if (notification != null) {
                notificationManager.notify(id, notification);
            }
            else {
                Log.w("Notification was NULL");
            }
        }
        else {
            Log.w("NotificationManager was NULL");
        }
    }

    public static void cancel(final NotificationManager notificationManager, final Integer id) {
        if (notificationManager != null) {
            notificationManager.cancel(id);
        }
        else {
            Log.w("NotificationManager was NULL");
        }
    }

}
