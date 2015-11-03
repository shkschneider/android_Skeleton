package me.shkschneider.skeleton.helper;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class SoundHelper {

    public static void notifications() {
        try {
            final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            final Ringtone ringtone = RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
            ringtone.play();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
    }

    public static void ringtone() {
        try {
            final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            final Ringtone ringtone = RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
            ringtone.play();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
    }

    public static void alarm() {
        try {
            final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            final Ringtone ringtone = RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
            ringtone.play();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
    }

    @Deprecated
    public static void all() {
        try {
            final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
            final Ringtone ringtone = RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
            ringtone.play();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
    }

}
