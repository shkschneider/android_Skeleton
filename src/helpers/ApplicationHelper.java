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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import me.shkschneider.skeleton.BuildConfig;
import me.shkschneider.skeleton.R;

public abstract class ApplicationHelper {

    public static Boolean isDebug() {
        return BuildConfig.DEBUG;
    }

	public static String getPackage(final Context context) {
        if (context != null) {
		    return context.getPackageName();
        }
        return null;
	}

	public static String getName(final Context context) {
        if (context != null) {
		    return context.getResources().getString(R.string.app_name);
        }
        return null;
	}

    public static Integer getVersionCode(final Context context) {
        if (context != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(getPackage(context), PackageManager.GET_META_DATA);
                return packageInfo.versionCode;
            }
            catch (NameNotFoundException e) {
                LogHelper.e("NameNotFoundException: " + e.getMessage());
            }
        }
        return 0;
    }

    public static String getVersionName(final Context context) {
        if (context != null) {
            try {
                final PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    final PackageInfo packageInfo = packageManager.getPackageInfo(ApplicationHelper.getPackage(context), PackageManager.GET_META_DATA);
                    return packageInfo.versionName;
                }
            }
            catch (NameNotFoundException e) {
                LogHelper.e("NameNotFoundException: " + e.getMessage());
            }
        }
        return "0";
    }

}
