package me.shkschneider.skeleton.helpers;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import me.shkschneider.skeleton.SkeletonApplication;

import org.jetbrains.annotations.NotNull;

public class AudioHelper {

    public static final int STREAM_ALARM = AudioManager.STREAM_ALARM;
    public static final int STREAM_DTMF = AudioManager.STREAM_DTMF;
    public static final int STREAM_MUSIC = AudioManager.STREAM_MUSIC;
    public static final int STREAM_NOTIFICATION = AudioManager.STREAM_NOTIFICATION;
    public static final int STREAM_RING = AudioManager.STREAM_RING;
    public static final int STREAM_SYSTEM = AudioManager.STREAM_SYSTEM;
    public static final int STREAM_VOICE_CALL = AudioManager.STREAM_VOICE_CALL;

    public static int volume(final int streamType) {
        final AudioManager audioManager = (AudioManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_AUDIO);
        return audioManager.getStreamVolume(streamType);
    }

    public static boolean play(@NotNull final String path) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            return true;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static boolean play(@NotNull final Uri uri) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(SkeletonApplication.CONTEXT, uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
            return true;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static boolean play(final int rawId) {
        final MediaPlayer mediaPlayer = MediaPlayer.create(SkeletonApplication.CONTEXT, rawId);
        if (mediaPlayer == null) {
            LogHelper.warning("MediaPlayer was NULL");
            return false;
        }

        try {
            mediaPlayer.start();
            return true;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

}
