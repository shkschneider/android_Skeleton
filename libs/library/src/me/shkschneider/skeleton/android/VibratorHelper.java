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
package me.shkschneider.skeleton.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

@SuppressWarnings("unused")
public class VibratorHelper {

    @SuppressLint("NewApi")
    private static Boolean vibrateNew(final android.os.Vibrator vibrator, final long duration) {
        if (! vibrator.hasVibrator()) {
            LogHelper.d("Vibrator cannot vibrate");
            return false;
        }

        vibrator.vibrate(duration);
        return true;
    }

    @SuppressLint("NewApi")
    private static Boolean vibrateNew(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
        if (! vibrator.hasVibrator()) {
            LogHelper.d("Vibrator cannot vibrate");
            return false;
        }

        if (! vibrator.hasVibrator()) {
            LogHelper.d("Vibrator cannot vibrate");
            return false;
        }

        vibrator.vibrate(durations, (repeat ? 0 : -1));
        return true;
    }

    private static Boolean vibrateOld(final android.os.Vibrator vibrator, final long duration) {
        if (vibrator == null) {
            LogHelper.d("Vibrator was NULL");
            return false;
        }

        vibrator.vibrate(duration);
        return true;
    }

    private static Boolean vibrateOld(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
        if (vibrator == null) {
            LogHelper.d("Vibrator was NULL");
            return false;
        }

        vibrator.vibrate(durations, (repeat ? 0 : -1));
        return true;
    }

    public static Boolean vibrate(final Context context, final long duration) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        final android.os.Vibrator vibrator = (android.os.Vibrator) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_VIBRATOR);
        if (AndroidHelper.api() < Build.VERSION_CODES.HONEYCOMB) {
            return vibrateOld(vibrator, duration);
        }
        else {
            return vibrateNew(vibrator, duration);
        }
    }

    public static Boolean vibrate(final Context context, final long[] durations, final Boolean repeat) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        final android.os.Vibrator vibrator = (android.os.Vibrator) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_VIBRATOR);
        if (AndroidHelper.api() < Build.VERSION_CODES.HONEYCOMB) {
            return vibrateOld(vibrator, durations, repeat);
        }
        else {
            return vibrateNew(vibrator, durations, repeat);
        }
    }

}
