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

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;
import android.util.TypedValue;
import android.view.WindowManager;

@SuppressWarnings("unused")
public class ScreenHelper {

    // <http://developer.android.com/reference/android/util/DisplayMetrics.html>
    public static final int DENSITY_LDPI = 120;
    public static final int DENSITY_MDPI = 160;
    public static final int DENSITY_HDPI = 240;
    public static final int DENSITY_XHDPI = 320;
    public static final int DENSITY_XXHDPI = 480;
    public static final int DENSITY_XXXHDPI = 640;
    public static final int DENSITY_TV = 213;

    public static Boolean wakeLock(final Activity activity) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    public static Boolean on(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        return ((PowerManager) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_POWER_SERVICE)).isScreenOn();
    }

    public static Integer density(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return 0;
        }

        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static Integer height(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return 0;
        }

        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static Integer width(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return 0;
        }

        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static Integer orientation(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return 0;
        }

        final WindowManager windowManager = (WindowManager) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getRotation();
    }

    public static Integer pixelsFromDp(final Context context, final Float dp) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return 0;
        }

        if (dp <= 0) {
            return 0;
        }

        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }

}
