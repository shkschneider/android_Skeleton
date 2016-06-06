package me.shkschneider.skeleton.helper;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class SoundHelper {

    public static Ringtone notification() {
        try {
            final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            return RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static Ringtone ringtone() {
        try {
            final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            return RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static Ringtone alarm() {
        try {
            final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            return RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Deprecated // Not sure what that actually plays
    public static Ringtone all() {
        try {
            final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
            return RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
