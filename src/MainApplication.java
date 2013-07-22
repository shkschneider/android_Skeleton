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

import com.androidquery.util.AQUtility;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.DirHelper;
import me.shkschneider.skeleton.helper.FileHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.PreferencesHelper;
import me.shkschneider.skeleton.helper.RuntimeHelper;

public class MainApplication extends Application {

	public static Boolean DEBUG = false;
    public static String TAG = null;

	@Override
	public void onCreate() {
		super.onCreate();

        DEBUG = ApplicationHelper.isDebug();
        TAG = ApplicationHelper.getName(getApplicationContext());

        LogHelper.i(toString());

		PreferencesHelper.setContext(getApplicationContext());
        AQUtility.setDebug(SkeletonApplication.DEBUG);
        AQUtility.setCacheDir(FileHelper.get(DirHelper.getInternalCache(getApplicationContext())));

        AQUtility.setExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(final Thread thread, final Throwable throwable) {
                LogHelper.e("UNCAUGHTEXCEPTION: " + throwable.toString());
                // ...
            }
        });

    }

	@Override
	public void onLowMemory() {
		LogHelper.w("LowMemory: " + RuntimeHelper.getFreeMemory() + "/" + RuntimeHelper.getMaxMemory() + " B");

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
