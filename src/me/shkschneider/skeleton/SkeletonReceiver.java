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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

@SuppressWarnings("unused")
public class SkeletonReceiver extends BroadcastReceiver {

    /*
     * Intent.ACTION_TIME_TICK
     * Intent.ACTION_TIME_CHANGED
     * Intent.ACTION_TIMEZONE_CHANGED
     * Intent.ACTION_BOOT_COMPLETED
     * Intent.ACTION_PACKAGE_ADDED
     * Intent.ACTION_PACKAGE_CHANGED
     * Intent.ACTION_PACKAGE_REMOVED
     * Intent.ACTION_PACKAGE_RESTARTED
     * Intent.ACTION_PACKAGE_DATA_CLEARED
     * Intent.ACTION_UID_REMOVED
     * Intent.ACTION_BATTERY_CHANGED
     * Intent.ACTION_POWER_CONNECTED
     * Intent.ACTION_POWER_DISCONNECTED
     * Intent.ACTION_SHUTDOWN
     */

	@Override
	public void onReceive(final Context context, final Intent intent) {
        Skeleton.Log.d(intent.getAction());
	}

}
