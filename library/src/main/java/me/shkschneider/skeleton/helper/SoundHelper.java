package me.shkschneider.skeleton.helper;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class SoundHelper {

    public static Ringtone notification() {
        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        return RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
    }

    public static Ringtone ringtone() {
        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        return RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
    }

    public static Ringtone alarm() {
        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        return RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
    }

    @Deprecated // Not sure what that actually plays
    public static Ringtone all() {
        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
        return RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
    }

}
