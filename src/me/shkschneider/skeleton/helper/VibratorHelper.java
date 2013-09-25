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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

@SuppressWarnings("unused")
public class VibratorHelper {

    @SuppressLint("NewApi")
    private static void vibrateNew(final android.os.Vibrator vibrator, final long duration) {
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(duration);
        }
        else {
            LogHelper.d("VibratorHelper cannot vibrate");
        }
    }

    @SuppressLint("NewApi")
    private static void vibrateNew(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(durations, (repeat ? 0 : -1));
        }
        else {
            LogHelper.d("VibratorHelper cannot vibrate");
        }
    }

    private static void vibrateOld(final android.os.Vibrator vibrator, final long duration) {
        vibrator.vibrate(duration);
    }

    private static void vibrateOld(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
        vibrator.vibrate(durations, (repeat ? 0 : -1));
    }

    public static void vibrate(final Context context, final long duration) {
        final android.os.Vibrator vibrator = (android.os.Vibrator) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_VIBRATOR);
        if (vibrator != null) {
            if (AndroidHelper.api() < Build.VERSION_CODES.HONEYCOMB) {
                vibrateOld(vibrator, duration);
            }
            else {
                vibrateNew(vibrator, duration);
            }
        }
        else {
            LogHelper.w("VibratorHelper was NULL");
        }
    }

    public static void vibrate(final Context context, final long[] durations, final Boolean repeat) {
        final android.os.Vibrator vibrator = (android.os.Vibrator) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_VIBRATOR);
        if (vibrator != null) {
            if (AndroidHelper.api() < Build.VERSION_CODES.HONEYCOMB) {
                vibrateOld(vibrator, durations, repeat);
            }
            else {
                vibrateNew(vibrator, durations, repeat);
            }
        }
        else {
            LogHelper.w("VibratorHelper was NULL");
        }
    }

}
