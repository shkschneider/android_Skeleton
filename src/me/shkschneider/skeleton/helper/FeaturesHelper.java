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
import android.content.pm.PackageManager;
import android.text.TextUtils;

@SuppressWarnings("unused")
public class FeaturesHelper {

    // public static final String APP_WIDGETS = PackageManager.FEATURE_APP_WIDGETS;
    // public static final String AUDIO_LOW_LATENCY = PackageManager.FEATURE_AUDIO_LOW_LATENCY;
    public static final String BLUETOOTH = PackageManager.FEATURE_BLUETOOTH;
    // public static final String BLUETOOTH_LE = PackageManager.FEATURE_BLUETOOTH_LE;
    public static final String CAMERA = PackageManager.FEATURE_CAMERA;
    // public static final String CAMERA_ANY = PackageManager.FEATURE_CAMERA_ANY;
    public static final String CAMERA_AUTOFOCUS = PackageManager.FEATURE_CAMERA_AUTOFOCUS;
    public static final String CAMERA_FLASH = PackageManager.FEATURE_CAMERA_FLASH;
    // public static final String CAMERA_FRONT = PackageManager.FEATURE_CAMERA_FRONT;
    // public static final String FAKETOUCH = PackageManager.FEATURE_FAKETOUCH;
    // public static final String FAKETOUCH_MULTITOUCH_DISTINCT = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT;
    // public static final String FAKETOUCH_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND;
    // public static final String HOME_SCREEN = PackageManager.FEATURE_HOME_SCREEN;
    // public static final String INPUT_METHODS = PackageManager.FEATURE_INPUT_METHODS;
    public static final String LIVE_WALLPAPER = PackageManager.FEATURE_LIVE_WALLPAPER;
    public static final String LOCATION = PackageManager.FEATURE_LOCATION;
    public static final String LOCATION_GPS = PackageManager.FEATURE_LOCATION_GPS;
    public static final String LOCATION_NETWORK = PackageManager.FEATURE_LOCATION_NETWORK;
    public static final String MICROPHONE = PackageManager.FEATURE_MICROPHONE;
    // public static final String NFC = PackageManager.FEATURE_NFC;
    // public static final String SCREEN_LANDSCAPE = PackageManager.FEATURE_SCREEN_LANDSCAPE;
    // public static final String SCREEN_PORTRAIT = PackageManager.FEATURE_SCREEN_PORTRAIT;
    public static final String SENSOR_ACCELEROMETER = PackageManager.FEATURE_SENSOR_ACCELEROMETER;
    // public static final String SENSOR_BAROMETER = PackageManager.FEATURE_SENSOR_BAROMETER;
    public static final String SENSOR_COMPASS = PackageManager.FEATURE_SENSOR_COMPASS;
    // public static final String SENSOR_GYROSCOPE = PackageManager.FEATURE_SENSOR_GYROSCOPE;
    public static final String SENSOR_LIGHT = PackageManager.FEATURE_SENSOR_LIGHT;
    public static final String SENSOR_PROXIMITY = PackageManager.FEATURE_SENSOR_PROXIMITY;
    // public static final String SIP = PackageManager.FEATURE_SIP;
    // public static final String SIP_VOIP = PackageManager.FEATURE_SIP_VOIP;
    public static final String TELEPHONY = PackageManager.FEATURE_TELEPHONY;
    public static final String TELEPHONY_CDMA = PackageManager.FEATURE_TELEPHONY_CDMA;
    public static final String TELEPHONY_GSM = PackageManager.FEATURE_TELEPHONY_GSM;
    // public static final String TELEVISION = PackageManager.FEATURE_TELEVISION;
    public static final String TOUCHSCREEN = PackageManager.FEATURE_TOUCHSCREEN;
    public static final String TOUCHSCREEN_MULTITOUCH = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH;
    public static final String TOUCHSCREEN_MULTITOUCH_DISTINCT = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT;
    // public static final String TOUCHSCREEN_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND;
    // public static final String USB_ACCESSORY = PackageManager.FEATURE_USB_ACCESSORY;
    // public static final String USB_HOST = PackageManager.FEATURE_USB_HOST;
    public static final String WIFI = PackageManager.FEATURE_WIFI;
    // public static final String WIFI_DIRECT = PackageManager.FEATURE_WIFI_DIRECT;

    public static Boolean feature(final Context context, final String feature) {
        if (context != null) {
            if (! TextUtils.isEmpty(feature)) {
                final PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    return packageManager.hasSystemFeature(feature);
                }
                else {
                    LogHelper.w("PackageManager was NULL");
                }
            }
            else {
                LogHelper.w("Permission was NULL");
            }
        }
        else {
            LogHelper.w("Context was NULL");
        }
        return false;
    }

}
