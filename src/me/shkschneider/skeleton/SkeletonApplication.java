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
import android.content.Context;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;

import java.util.Locale;

@SuppressWarnings("unused")
public class SkeletonApplication extends Application {

    public static Context CONTEXT = null;
	public static Boolean DEBUG = false;
    public static String TAG = null;
    public static String LOCALE = null;

	@Override
	public void onCreate() {
		super.onCreate();

        CONTEXT = getApplicationContext();
        DEBUG = Skeleton.Android.debug();
        TAG = Skeleton.Android.packageName(getApplicationContext());
        LOCALE = Locale.getDefault().toString();

        Skeleton.Log.i(toString());

        AQUtility.setDebug(SkeletonApplication.DEBUG);
        AQUtility.setCacheDir(Skeleton.File.get(Skeleton.File.internalCacheDir(getApplicationContext())));

        AjaxCallback.setNetworkLimit(4);
        BitmapAjaxCallback.setIconCacheLimit(20);
        BitmapAjaxCallback.setCacheLimit(20);
        BitmapAjaxCallback.setPixelLimit(400 * 400);
        BitmapAjaxCallback.setMaxPixelLimit(4000000);
    }

	@Override
	public void onLowMemory() {
        Skeleton.Log.d("LowMemory: " + Skeleton.Runtime.freeMemory() + "/" + Skeleton.Runtime.maxMemory() + " B");

        BitmapAjaxCallback.clearCache();

		super.onLowMemory();
	}

	@Override
	public String toString() {
		return Skeleton.Android.name(getApplicationContext()) +
                (Skeleton.Android.debug() ? " [DEBUG]" : "") +
                " v" + Skeleton.Android.versionName(getApplicationContext()) +
                " (" + Skeleton.Android.packageName(getApplicationContext()) + ")" +
                " Android " + Skeleton.Android.release() +
                " (API-" + Skeleton.Android.api() + ")";
	}

}
