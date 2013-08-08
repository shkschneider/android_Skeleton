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
package me.shkschneider.skeleton;

import android.app.Application;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;

import java.util.Locale;

import me.shkschneider.skeleton.helpers.AndroidHelper;
import me.shkschneider.skeleton.helpers.ApplicationHelper;
import me.shkschneider.skeleton.helpers.DirHelper;
import me.shkschneider.skeleton.helpers.FileHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.helpers.PreferencesHelper;
import me.shkschneider.skeleton.helpers.RuntimeHelper;

public class SkeletonApplication extends Application {

	public static Boolean DEBUG = false;
    public static String TAG = null;
    public static String LOCALE = null;

	@Override
	public void onCreate() {
		super.onCreate();

        DEBUG = ApplicationHelper.isDebug();
        TAG = ApplicationHelper.getName(getApplicationContext());
        LOCALE = Locale.getDefault().toString();

        LogHelper.i(toString());

		PreferencesHelper.setContext(getApplicationContext());

        AQUtility.setDebug(SkeletonApplication.DEBUG);
        AQUtility.setCacheDir(FileHelper.get(DirHelper.getInternalCache(getApplicationContext())));

        AjaxCallback.setNetworkLimit(4);
        BitmapAjaxCallback.setIconCacheLimit(20);
        BitmapAjaxCallback.setCacheLimit(20);
        BitmapAjaxCallback.setPixelLimit(400 * 400);
        BitmapAjaxCallback.setMaxPixelLimit(4000000);
    }

	@Override
	public void onLowMemory() {
		LogHelper.w("LowMemory: " + RuntimeHelper.getFreeMemory() + "/" + RuntimeHelper.getMaxMemory() + " B");

        BitmapAjaxCallback.clearCache();

		super.onLowMemory();
	}

	@Override
	public String toString() {
		return ApplicationHelper.getName(getApplicationContext()) +
                (ApplicationHelper.isDebug() ? " [DEBUG]" : "") +
                " v" + ApplicationHelper.getVersionName(getApplicationContext()) +
                " (" + ApplicationHelper.getPackage(getApplicationContext()) + ")" +
                " Android " + AndroidHelper.getRelease() +
                " (API-" + AndroidHelper.getApi() + ")";
	}

}
