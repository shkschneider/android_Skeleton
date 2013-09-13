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
package me.shkschneider.skeleton.helpers;

import android.content.Context;
import android.content.pm.PackageManager;

import me.shkschneider.skeleton.BuildConfig;
import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.SkeletonLog;

public abstract class SkeletonApplicationHelper {

    public static Boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static String getPackage(final Context context) {
        if (context != null) {
            return context.getPackageName();
        }
        else {
            SkeletonLog.d("Context was NULL");
        }
        return null;
    }

    public static String getName(final Context context) {
        if (context != null) {
            return context.getResources().getString(R.string.app_name);
        }
        else {
            SkeletonLog.d("Context was NULL");
        }
        return null;
    }

    public static Integer getVersionCode(final Context context) {
        if (context != null) {
            try {
                final PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    return packageManager.getPackageInfo(SkeletonApplicationHelper.getPackage(context), PackageManager.GET_META_DATA).versionCode;
                }
                else {
                    SkeletonLog.d("PackageManager was NULL");
                }
            }
            catch (PackageManager.NameNotFoundException e) {
                SkeletonLog.e("NameNotFoundException: " + e.getMessage());
            }
        }
        else {
            SkeletonLog.d("Context was NULL");
        }
        return 0;
    }

    public static String getVersionName(final Context context) {
        if (context != null) {
            try {
                final PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    return packageManager.getPackageInfo(SkeletonApplicationHelper.getPackage(context), PackageManager.GET_META_DATA).versionName;
                }
                else {
                    SkeletonLog.d("PackageManager was NULL");
                }
            }
            catch (PackageManager.NameNotFoundException e) {
                SkeletonLog.e("NameNotFoundException: " + e.getMessage());
            }
        }
        else {
            SkeletonLog.d("Context was NULL");
        }
        return "0";
    }

}
