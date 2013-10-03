/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.helper;

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
public class NotificationHelper {

    public static Boolean toastShort(final Context context, final String text) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }
        if (TextUtils.isEmpty(text)) {
            LogHelper.w("String was NULL");
            return false;
        }

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        return true;
    }

    public static Boolean toastLong(final Context context, final String text) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }
        if (TextUtils.isEmpty(text)) {
            LogHelper.w("String was NULL");
            return false;
        }

        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        return true;
    }

    public static Boolean croutonInfo(final Activity activity, final String text) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }
        if (TextUtils.isEmpty(text)) {
            LogHelper.w("String was NULL");
            return false;
        }

        Crouton.makeText(activity, text, Style.INFO).show();
        return true;
    }

    public static Boolean croutonConfirm(final Activity activity, final String text) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }
        if (TextUtils.isEmpty(text)) {
            LogHelper.w("String was NULL");
            return false;
        }

        Crouton.makeText(activity, text, Style.CONFIRM).show();
        return true;
    }

    public static Boolean croutonAlert(final Activity activity, final String text) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }
        if (TextUtils.isEmpty(text)) {
            LogHelper.w("String was NULL");
            return false;
        }

        Crouton.makeText(activity, text, Style.ALERT).show();
        return true;
    }

    public static Boolean onDestroy(final Activity activity) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        Crouton.clearCroutonsForActivity(activity);
        return true;
    }

    public static NotificationManager notificationManager(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        return (NotificationManager) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_NOTIFICATION_SERVICE);
    }

    public static Notification notification(final Context context, final int smallIcon, final String title, final String message, final PendingIntent pendingIntent) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

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

    public static Notification notification(final Context context, final int smallIcon, final String title, final String message) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        return notification(context, smallIcon, title, message, null);
    }

    public static Boolean notify(final NotificationManager notificationManager, final Notification notification, final Integer id) {
        if (notificationManager == null) {
            LogHelper.w("NotificationManager was NULL");
            return null;
        }
        if (notification == null) {
            LogHelper.w("Notification was NULL");
            return null;
        }

        notificationManager.notify(id, notification);
        return true;
    }

    public static Boolean cancel(final NotificationManager notificationManager, final Integer id) {
        if (notificationManager == null) {
            LogHelper.w("NotificationManager was NULL");
            return false;
        }

        notificationManager.cancel(id);
        return true;
    }

}
