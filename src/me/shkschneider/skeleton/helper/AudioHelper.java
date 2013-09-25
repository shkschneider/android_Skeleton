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

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

@SuppressWarnings("unused")
public class AudioHelper {

    public static final int STREAM_ALARM = AudioManager.STREAM_ALARM;
    public static final int STREAM_DTMF = AudioManager.STREAM_DTMF;
    public static final int STREAM_MUSIC = AudioManager.STREAM_MUSIC;
    public static final int STREAM_NOTIFICATION = AudioManager.STREAM_NOTIFICATION;
    public static final int STREAM_RING = AudioManager.STREAM_RING;
    public static final int STREAM_SYSTEM = AudioManager.STREAM_SYSTEM;
    public static final int STREAM_VOICE_CALL = AudioManager.STREAM_VOICE_CALL;
    public static final int STREAM_DEFAULT = AudioManager.USE_DEFAULT_STREAM_TYPE;

    public static Integer volume(final Context context, final int streamType) {
        if (context != null) {
            final AudioManager audioManager = (AudioManager) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_AUDIO);
            audioManager.getStreamVolume(streamType);
        }
        else {
            LogHelper.w("Context was NULL");
        }
        return 0;
    }

    public static Integer volume(final Context context) {
        return volume(context, AudioManager.USE_DEFAULT_STREAM_TYPE);
    }

    public static void play(final String path) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e) {
            LogHelper.e("IOException: " + e.getMessage());
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
                    LogHelper.e("IOException: " + e.getMessage());
                }
            }
            else {
                LogHelper.w("Uri was NULL");
            }
        }
        else {
            LogHelper.w("Context was NULL");
        }
    }

    public static void play(final Context context, final int rawId) {
        if (context != null) {
            final MediaPlayer mediaPlayer = MediaPlayer.create(context, rawId);
            mediaPlayer.start();
        }
        else {
            LogHelper.w("Context was NULL");
        }
    }

}
