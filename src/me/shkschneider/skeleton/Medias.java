package me.shkschneider.skeleton;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

@SuppressWarnings("unused")
public class Medias {

    public static Integer volume(final Context context, final int streamType) {
        if (context != null) {
            final AudioManager audioManager = (AudioManager) Skeleton.System.systemService(context, Skeleton.System.SYSTEM_SERVICE_AUDIO);
            audioManager.getStreamVolume(streamType);
        }
        else {
            Log.w("Context was NULL");
        }
        return 0;
    }

    public static Integer volume(final Context context) {
        return volume(context, AudioManager.STREAM_SYSTEM);
    }

    public static void play(final String path) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e) {
            Log.e("IOException: " + e.getMessage());
        }
    }

    public static void play(final Context context, final Uri uri) {
        if (context != null) {
            if (uri != null) {
                try {
                    final MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(context, uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
                catch (IOException e) {
                    Log.e("IOException: " + e.getMessage());
                }
            }
            else {
                Log.w("Uri was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
    }

    public static void play(final Context context, final int rawId) {
        if (context != null) {
            final MediaPlayer mediaPlayer = MediaPlayer.create(context, rawId);
            mediaPlayer.start();
        }
        else {
            Log.w("Context was NULL");
        }
    }

}
