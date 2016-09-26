package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;

public class SoundHelper {

    public static Ringtone notification(@NonNull final Context context) {
        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        return RingtoneManager.getRingtone(context, uri);
    }

    public static Ringtone ringtone(@NonNull final Context context) {
        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        return RingtoneManager.getRingtone(context, uri);
    }

    public static Ringtone alarm(@NonNull final Context context) {
        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        return RingtoneManager.getRingtone(context, uri);
    }

    @Deprecated // Avoid
    public static Ringtone all(@NonNull final Context context) {
        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
        return RingtoneManager.getRingtone(context, uri);
    }

}
