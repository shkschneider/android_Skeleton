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

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.androidquery.AQuery;
import com.androidquery.auth.FacebookHandle;
import com.androidquery.callback.AbstractAjaxCallback;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.devspark.appmsg.AppMsg;
import com.testflightapp.lib.TestFlight;

import org.apache.http.HttpStatus;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.protocol.HTTP;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/*
 * Skeleton
 *  Log
 *  Android
 *  System
 *  File
 *  Keyboard
 *  Vibrator
 *  Network
 *  Notification
 *  Runtime
 *  String
 *  Time
 *  WebView
 *  Hash
 *  Facebook
 *  WebService
 *  ImageDownloader
 *  Location
 *  Screen
 *  Intent
 *  Activity
 *  Graphics
 */

@SuppressWarnings("unused")
public abstract class Skeleton {

    public static final java.lang.String PLATFORM = "Android";

    public static class Log {

        private static final int VERBOSE = 10;
        private static final int DEBUG = 20;
        private static final int INFO = 30;
        private static final int WARN = 40;
        private static final int ERROR = 50;

        private static void log(final int state, final java.lang.String msg) {
            final java.lang.String tag = SkeletonApplication.TAG;

            // Uses StackTrace to build the log tag
            final StackTraceElement[] elements = new Throwable().getStackTrace();
            java.lang.String callerClassName = "?";
            java.lang.String callerMethodName = "?";
            if (elements.length >= 3) {
                callerClassName = elements[2].getClassName();
                callerClassName = callerClassName.substring(callerClassName.lastIndexOf('.') + 1);
                if (callerClassName.indexOf("$") > 0) {
                    callerClassName = callerClassName.substring(0, callerClassName.indexOf("$"));
                }
                callerMethodName = elements[2].getMethodName();
                callerMethodName = callerMethodName.substring(callerMethodName.lastIndexOf('_') + 1);
            }

            final java.lang.String stack = callerClassName + " " + callerMethodName + "()";

            switch (state) {
                case VERBOSE:
                    android.util.Log.v(tag, "[" + stack + "] " + msg);
                    break ;

                case DEBUG:
                    android.util.Log.d(tag, "[" + stack + "] " + msg);
                    break ;

                case INFO:
                    android.util.Log.i(tag, "[" + stack + "] " + msg);
                    break ;

                case WARN:
                    android.util.Log.w(tag, "[" + stack + "] " + msg);
                    break ;

                case ERROR:
                    android.util.Log.e(tag, "[" + stack + "] " + msg);
                    break ;
            }

            if (TestFlight.isActive()) {
                TestFlight.log("[" + stack + "] " + msg);
            }
        }

        public static void v(final java.lang.String msg) {
            log(VERBOSE, msg);
        }

        public static void d(final java.lang.String msg) {
            log(DEBUG, msg);
        }

        public static void i(final java.lang.String msg) {
            log(INFO, msg);
        }

        public static void w(final java.lang.String msg) {
            log(WARN, msg);
        }

        public static void e(final java.lang.String msg) {
            log(ERROR, msg);
        }

        public static void checkpoint(final java.lang.String name) {
            if (TestFlight.isActive()) {
                TestFlight.passCheckpoint(name);
            }
            else {
                Skeleton.Log.d("TestFlight was inactive");
            }
        }

    }

    public static class Android {

        public static final int API_3 = Build.VERSION_CODES.CUPCAKE;
        public static final int API_4 = Build.VERSION_CODES.DONUT;
        public static final int API_5 = Build.VERSION_CODES.ECLAIR;
        public static final int API_6 = Build.VERSION_CODES.ECLAIR_0_1;
        public static final int API_7 = Build.VERSION_CODES.ECLAIR_MR1;
        public static final int API_8 = Build.VERSION_CODES.FROYO;
        public static final int API_9 = Build.VERSION_CODES.GINGERBREAD;
        public static final int API_10 = Build.VERSION_CODES.GINGERBREAD_MR1;
        public static final int API_11 = Build.VERSION_CODES.HONEYCOMB;
        public static final int API_12 = Build.VERSION_CODES.HONEYCOMB_MR1;
        public static final int API_13 = Build.VERSION_CODES.HONEYCOMB_MR2;
        public static final int API_14 = Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        public static final int API_15 = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
        public static final int API_16 = Build.VERSION_CODES.JELLY_BEAN;
        public static final int API_17 = Build.VERSION_CODES.JELLY_BEAN_MR1;
        public static final int API_18 = Build.VERSION_CODES.JELLY_BEAN_MR2;

        public static void testFlight(final Application application, final java.lang.String token) {
            if (application != null) {
                if (! TextUtils.isEmpty(token)) {
                    if (! TestFlight.isActive()) {
                        TestFlight.takeOff(application, token);
                    }
                    else {
                        Skeleton.Log.d("TestFlight was active");
                    }
                }
                else {
                    Skeleton.Log.w("ApiKey was NULL");
                }
            }
            else {
                Skeleton.Log.w("Application was NULL");
            }
        }

        // If SCREENLAYOUT_SIZE is XLARGE for API >= HONEYCOMB

        public static Boolean isTablet(final Context context) {
            if (context != null) {
                if (getApi() >= Build.VERSION_CODES.HONEYCOMB) {
                    final Configuration configuration = context.getResources().getConfiguration();
                    if (configuration != null) {
                        try {
                            return (Boolean) configuration.getClass().getMethod("isLayoutSizeAtLeast", int.class)
                                    .invoke(configuration, Configuration.SCREENLAYOUT_SIZE_XLARGE);
                        }
                        catch (NoSuchMethodException e) {
                            Log.e("NoSuchMethodException: " + e.getMessage());
                        }
                        catch (IllegalAccessException e) {
                            Log.e("IllegalAccessException: " + e.getMessage());
                        }
                        catch (InvocationTargetException e) {
                            Log.e("InvocationTargetException: " + e.getMessage());
                        }
                    }
                    else {
                        Log.w("Configuration was NULL");
                    }
                }
                else {
                    Log.d("Api was < HONEYCOMB");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return false;
        }

        // Get Android ID (length: 16)
        // The value may change if a factory reset is performed on the device.

        public static java.lang.String getId(final Context context) {
            if (context != null) {
                final java.lang.String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                if (! TextUtils.isEmpty(id)) {
                    return id.toLowerCase();
                }
                else {
                    Log.w("Id was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Get Device ID (length: 32)
        // The value may change if a factory reset is performed on the device.

        public static java.lang.String getDeviceId(final Context context) {
            if (context != null) {
                final java.lang.String id = getId(context);
                if (! TextUtils.isEmpty(id)) {
                    try {
                        final MessageDigest messageDigest = MessageDigest.getInstance(Hash.MD5);
                        if (messageDigest != null) {
                            return new BigInteger(1, messageDigest.digest(id.getBytes())).toString(16).toLowerCase();
                        }
                        else {
                            Log.w("MessageDigest was NULL");
                        }
                    }
                    catch (NoSuchAlgorithmException e) {
                        Log.e("NoSuchAlgorithmException: " + e.getMessage());
                    }
                }
                else {
                    Log.w("Id was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Get UUID from Device ID (RFC4122, length: 36)
        // The value may change if a factory reset is performed on the device.

        public static java.lang.String getUUID(final Context context) {
            if (context != null) {
                final java.lang.String deviceId = getDeviceId(context);
                if (! TextUtils.isEmpty(deviceId)) {
                    return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
                }
                else {
                    Log.w("DeviceId was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Get a random ID (RFC4122, length: 32)
        // Random

        public static java.lang.String getRandomId() {
            return UUID.randomUUID().toString().replace("-", "");
        }

        public static java.lang.String getDevice() {
            return Build.DEVICE;
        }

        public static java.lang.String getRelease() {
            return Build.VERSION.RELEASE;
        }

        public static int getApi() {
            return Build.VERSION.SDK_INT;
        }

        public static Boolean isDebug() {
            return BuildConfig.DEBUG;
        }

        public static java.lang.String getPackage(final Context context) {
            if (context != null) {
                return context.getPackageName();
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static java.lang.String getName(final Context context) {
            if (context != null) {
                return context.getResources().getString(R.string.app_name);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static java.lang.String getVersionName(final Context context) {
            if (context != null) {
                try {
                    final PackageManager packageManager = context.getPackageManager();
                    if (packageManager != null) {
                        return packageManager.getPackageInfo(getPackage(context), PackageManager.GET_META_DATA).versionName;
                    }
                    else {
                        Log.w("PackageManager was NULL");
                    }
                }
                catch (PackageManager.NameNotFoundException e) {
                    Log.e("NameNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return "0";
        }

        public static Integer getVersionCode(final Context context) {
            if (context != null) {
                try {
                    final PackageManager packageManager = context.getPackageManager();
                    if (packageManager != null) {
                        return packageManager.getPackageInfo(getPackage(context), PackageManager.GET_META_DATA).versionCode;
                    }
                    else {
                        Log.w("PackageManager was NULL");
                    }
                }
                catch (PackageManager.NameNotFoundException e) {
                    Log.e("NameNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return 0;
        }

        private static final java.lang.String ACCOUNT_GOOGLE = "com.google";

        public static java.lang.String getAccount(final Context context) {
            if (context != null) {
                final AccountManager accountManager = AccountManager.get(context);
                if (accountManager != null) {
                    for (Account account : accountManager.getAccounts()) {
                        if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                            return account.name;
                        }
                    }
                }
                else {
                    Log.w("AccountManager was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static java.lang.String getSignature(final Context context) {
            if (context != null) {
                final PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    try {
                        final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
                        if (packageInfo != null) {
                            final Signature[] signatures = packageInfo.signatures;
                            if (signatures != null) {
                                return signatures[0].toCharsString();
                            }
                            else {
                                Log.d("No signatures");
                            }
                        }
                        else {
                            Log.w("PackageInfo was NULL");
                        }
                    }
                    catch (PackageManager.NameNotFoundException e) {
                        Log.e("NameNotFoundException: " + e.getMessage());
                    }
                }
                else {
                    Log.w("PackageManager was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static TelephonyManager getSim(final Context context) {
            if (context != null) {
                return (TelephonyManager) System.getSystemService(context, System.SYSTEM_SERVICE_TELEPHONY);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static Boolean hasPermission(final Context context, final java.lang.String permission) {
            if (context != null) {
                if (! TextUtils.isEmpty(permission)) {
                    final PackageManager packageManager = context.getPackageManager();
                    if (packageManager != null) {
                        return (packageManager.checkPermission(permission, Android.getPackage(context)) == PackageManager.PERMISSION_GRANTED);
                    }
                    else {
                        Log.w("PackageManager was NULL");
                    }
                }
                else {
                    Log.w("Permission was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return false;
        }

        public static class Permissions {

            public static final java.lang.String ACCESS_CHECKIN_PROPERTIES = android.Manifest.permission.ACCESS_CHECKIN_PROPERTIES;
            public static final java.lang.String ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
            public static final java.lang.String ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
            public static final java.lang.String ACCESS_LOCATION_EXTRA_COMMANDS = android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
            public static final java.lang.String ACCESS_MOCK_LOCATION = android.Manifest.permission.ACCESS_MOCK_LOCATION;
            public static final java.lang.String ACCESS_NETWORK_STATE = android.Manifest.permission.ACCESS_NETWORK_STATE;
            public static final java.lang.String ACCESS_SURFACE_FLINGER = android.Manifest.permission.ACCESS_SURFACE_FLINGER;
            public static final java.lang.String ACCESS_WIFI_STATE = android.Manifest.permission.ACCESS_WIFI_STATE;
            public static final java.lang.String ACCOUNT_MANAGER = android.Manifest.permission.ACCOUNT_MANAGER;
            // public static final java.lang.String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
            public static final java.lang.String AUTHENTICATE_ACCOUNTS = android.Manifest.permission.AUTHENTICATE_ACCOUNTS;
            public static final java.lang.String BATTERY_STATS = android.Manifest.permission.BATTERY_STATS;
            // public static final java.lang.String BIND_ACCESSIBILITY_SERVICE = android.Manifest.permission.BIND_ACCESSIBILITY_SERVICE;
            public static final java.lang.String BIND_APPWIDGET = android.Manifest.permission.BIND_APPWIDGET;
            public static final java.lang.String BIND_DEVICE_ADMIN = android.Manifest.permission.BIND_DEVICE_ADMIN;
            public static final java.lang.String BIND_INPUT_METHOD = android.Manifest.permission.BIND_INPUT_METHOD;
            public static final java.lang.String BIND_WALLPAPER = android.Manifest.permission.BIND_WALLPAPER;
            public static final java.lang.String BLUETOOTH = android.Manifest.permission.BLUETOOTH;
            public static final java.lang.String BLUETOOTH_ADMIN = android.Manifest.permission.BLUETOOTH_ADMIN;
            public static final java.lang.String BRICK = android.Manifest.permission.BRICK;
            public static final java.lang.String BROADCAST_PACKAGE_REMOVED = android.Manifest.permission.BROADCAST_PACKAGE_REMOVED;
            public static final java.lang.String BROADCAST_SMS = android.Manifest.permission.BROADCAST_SMS;
            public static final java.lang.String BROADCAST_STICKY = android.Manifest.permission.BROADCAST_STICKY;
            public static final java.lang.String BROADCAST_WAP_PUSH = android.Manifest.permission.BROADCAST_WAP_PUSH;
            public static final java.lang.String CALL_PHONE = android.Manifest.permission.CALL_PHONE;
            public static final java.lang.String CALL_PRIVILEGED = android.Manifest.permission.CALL_PRIVILEGED;
            public static final java.lang.String CAMERA = android.Manifest.permission.CAMERA;
            public static final java.lang.String CHANGE_COMPONENT_ENABLED_STATE = android.Manifest.permission.CHANGE_COMPONENT_ENABLED_STATE;
            public static final java.lang.String CHANGE_CONFIGURATION = android.Manifest.permission.CHANGE_CONFIGURATION;
            public static final java.lang.String CHANGE_NETWORK_STATE = android.Manifest.permission.CHANGE_NETWORK_STATE;
            public static final java.lang.String CHANGE_WIFI_MULTICAST_STATE = android.Manifest.permission.CHANGE_WIFI_MULTICAST_STATE;
            public static final java.lang.String CHANGE_WIFI_STATE = android.Manifest.permission.CHANGE_WIFI_STATE;
            public static final java.lang.String CLEAR_APP_CACHE = android.Manifest.permission.CLEAR_APP_CACHE;
            public static final java.lang.String CLEAR_APP_USER_DATA = android.Manifest.permission.CLEAR_APP_USER_DATA;
            public static final java.lang.String CONTROL_LOCATION_UPDATES = android.Manifest.permission.CONTROL_LOCATION_UPDATES;
            public static final java.lang.String DELETE_CACHE_FILES = android.Manifest.permission.DELETE_CACHE_FILES;
            public static final java.lang.String DELETE_PACKAGES = android.Manifest.permission.DELETE_PACKAGES;
            public static final java.lang.String DEVICE_POWER = android.Manifest.permission.DEVICE_POWER;
            public static final java.lang.String DIAGNOSTIC = android.Manifest.permission.DIAGNOSTIC;
            public static final java.lang.String DISABLE_KEYGUARD = android.Manifest.permission.DISABLE_KEYGUARD;
            public static final java.lang.String DUMP = android.Manifest.permission.DUMP;
            public static final java.lang.String EXPAND_STATUS_BAR = android.Manifest.permission.EXPAND_STATUS_BAR;
            public static final java.lang.String FACTORY_TEST = android.Manifest.permission.FACTORY_TEST;
            public static final java.lang.String FLASHLIGHT = android.Manifest.permission.FLASHLIGHT;
            public static final java.lang.String FORCE_BACK = android.Manifest.permission.FORCE_BACK;
            public static final java.lang.String GET_ACCOUNTS = android.Manifest.permission.GET_ACCOUNTS;
            public static final java.lang.String GET_PACKAGE_SIZE = android.Manifest.permission.GET_PACKAGE_SIZE;
            public static final java.lang.String GET_TASKS = android.Manifest.permission.GET_TASKS;
            // public static final java.lang.String GET_TOP_ACTIVITY_INFO = android.Manifest.permission.GET_TOP_ACTIVITY_INFO;
            public static final java.lang.String GLOBAL_SEARCH = android.Manifest.permission.GLOBAL_SEARCH;
            public static final java.lang.String HARDWARE_TEST = android.Manifest.permission.HARDWARE_TEST;
            public static final java.lang.String INJECT_EVENTS = android.Manifest.permission.INJECT_EVENTS;
            public static final java.lang.String INSTALL_LOCATION_PROVIDER = android.Manifest.permission.INSTALL_LOCATION_PROVIDER;
            public static final java.lang.String INSTALL_PACKAGES = android.Manifest.permission.INSTALL_PACKAGES;
            public static final java.lang.String INTERNAL_SYSTEM_WINDOW = android.Manifest.permission.INTERNAL_SYSTEM_WINDOW;
            public static final java.lang.String INTERNET = android.Manifest.permission.INTERNET;
            public static final java.lang.String KILL_BACKGROUND_PROCESSES = android.Manifest.permission.KILL_BACKGROUND_PROCESSES;
            // public static final java.lang.String LOCATION_HARDWARE = android.Manifest.permission.LOCATION_HARDWARE;
            public static final java.lang.String MANAGE_ACCOUNTS = android.Manifest.permission.MANAGE_ACCOUNTS;
            public static final java.lang.String MANAGE_APP_TOKENS = android.Manifest.permission.MANAGE_APP_TOKENS;
            public static final java.lang.String MASTER_CLEAR = android.Manifest.permission.MASTER_CLEAR;
            public static final java.lang.String MODIFY_AUDIO_SETTINGS = android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
            public static final java.lang.String MODIFY_PHONE_STATE = android.Manifest.permission.MODIFY_PHONE_STATE;
            public static final java.lang.String MOUNT_FORMAT_FILESYSTEMS = android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS;
            public static final java.lang.String MOUNT_UNMOUNT_FILESYSTEMS = android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS;
            // public static final java.lang.String NFC = android.Manifest.permission.NFC;

        }

        public static Boolean hasFeature(final Context context, final java.lang.String feature) {
            if (context != null) {
                if (! TextUtils.isEmpty(feature)) {
                    final PackageManager packageManager = context.getPackageManager();
                    if (packageManager != null) {
                        return packageManager.hasSystemFeature(feature);
                    }
                    else {
                        Log.w("PackageManager was NULL");
                    }
                }
                else {
                    Log.w("Permission was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return false;
        }

        public static class Features {

            // public static final java.lang.String APP_WIDGETS = PackageManager.FEATURE_APP_WIDGETS;
            // public static final java.lang.String AUDIO_LOW_LATENCY = PackageManager.FEATURE_AUDIO_LOW_LATENCY;
            public static final java.lang.String BLUETOOTH = PackageManager.FEATURE_BLUETOOTH;
            // public static final java.lang.String BLUETOOTH_LE = PackageManager.FEATURE_BLUETOOTH_LE;
            public static final java.lang.String CAMERA = PackageManager.FEATURE_CAMERA;
            // public static final java.lang.String CAMERA_ANY = PackageManager.FEATURE_CAMERA_ANY;
            public static final java.lang.String CAMERA_AUTOFOCUS = PackageManager.FEATURE_CAMERA_AUTOFOCUS;
            public static final java.lang.String CAMERA_FLASH = PackageManager.FEATURE_CAMERA_FLASH;
            // public static final java.lang.String CAMERA_FRONT = PackageManager.FEATURE_CAMERA_FRONT;
            // public static final java.lang.String FAKETOUCH = PackageManager.FEATURE_FAKETOUCH;
            // public static final java.lang.String FAKETOUCH_MULTITOUCH_DISTINCT = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT;
            // public static final java.lang.String FAKETOUCH_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND;
            // public static final java.lang.String HOME_SCREEN = PackageManager.FEATURE_HOME_SCREEN;
            // public static final java.lang.String INPUT_METHODS = PackageManager.FEATURE_INPUT_METHODS;
            public static final java.lang.String LIVE_WALLPAPER = PackageManager.FEATURE_LIVE_WALLPAPER;
            public static final java.lang.String LOCATION = PackageManager.FEATURE_LOCATION;
            public static final java.lang.String LOCATION_GPS = PackageManager.FEATURE_LOCATION_GPS;
            public static final java.lang.String LOCATION_NETWORK = PackageManager.FEATURE_LOCATION_NETWORK;
            public static final java.lang.String MICROPHONE = PackageManager.FEATURE_MICROPHONE;
            // public static final java.lang.String NFC = PackageManager.FEATURE_NFC;
            // public static final java.lang.String SCREEN_LANDSCAPE = PackageManager.FEATURE_SCREEN_LANDSCAPE;
            // public static final java.lang.String SCREEN_PORTRAIT = PackageManager.FEATURE_SCREEN_PORTRAIT;
            public static final java.lang.String SENSOR_ACCELEROMETER = PackageManager.FEATURE_SENSOR_ACCELEROMETER;
            // public static final java.lang.String SENSOR_BAROMETER = PackageManager.FEATURE_SENSOR_BAROMETER;
            public static final java.lang.String SENSOR_COMPASS = PackageManager.FEATURE_SENSOR_COMPASS;
            // public static final java.lang.String SENSOR_GYROSCOPE = PackageManager.FEATURE_SENSOR_GYROSCOPE;
            public static final java.lang.String SENSOR_LIGHT = PackageManager.FEATURE_SENSOR_LIGHT;
            public static final java.lang.String SENSOR_PROXIMITY = PackageManager.FEATURE_SENSOR_PROXIMITY;
            // public static final java.lang.String SIP = PackageManager.FEATURE_SIP;
            // public static final java.lang.String SIP_VOIP = PackageManager.FEATURE_SIP_VOIP;
            public static final java.lang.String TELEPHONY = PackageManager.FEATURE_TELEPHONY;
            public static final java.lang.String TELEPHONY_CDMA = PackageManager.FEATURE_TELEPHONY_CDMA;
            public static final java.lang.String TELEPHONY_GSM = PackageManager.FEATURE_TELEPHONY_GSM;
            // public static final java.lang.String TELEVISION = PackageManager.FEATURE_TELEVISION;
            public static final java.lang.String TOUCHSCREEN = PackageManager.FEATURE_TOUCHSCREEN;
            public static final java.lang.String TOUCHSCREEN_MULTITOUCH = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH;
            public static final java.lang.String TOUCHSCREEN_MULTITOUCH_DISTINCT = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT;
            // public static final java.lang.String TOUCHSCREEN_MULTITOUCH_JAZZHAND = PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND;
            // public static final java.lang.String USB_ACCESSORY = PackageManager.FEATURE_USB_ACCESSORY;
            // public static final java.lang.String USB_HOST = PackageManager.FEATURE_USB_HOST;
            public static final java.lang.String WIFI = PackageManager.FEATURE_WIFI;
            // public static final java.lang.String WIFI_DIRECT = PackageManager.FEATURE_WIFI_DIRECT;

        }

    }

    public static class System {

        public static final java.lang.String SYSTEM_PROPERTY_JAVA_VM_NAME = "java.vm.name";
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_VM_VENDOR = "java.vm.vendor";
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_VM_VERSION = "java.vm.version";
        // java.version is not implemented on Android
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_HOME = "java.home";
        public static final java.lang.String SYSTEM_PROPERTY_USER_DIR = "user.dir";
        // user.home is not implemented on Android
        public static final java.lang.String SYSTEM_PROPERTY_USER_REGION = "user.region";
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_IO_TMPDIR = "java.io.tmpdir";
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_RUNTIME_NAME = "java.runtime.name";
        public static final java.lang.String SYSTEM_PROPERTY_HTTP_AGENT = "http.agent";
        public static final java.lang.String SYSTEM_PROPERTY_FILE_SEPARATOR = "file.separator";
        public static final java.lang.String SYSTEM_PROPERTY_FILE_ENCODING = "file.encoding";
        public static final java.lang.String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";
        public static final java.lang.String SYSTEM_PROPERTY_OS_ARCH = "os.arch";
        public static final java.lang.String SYSTEM_PROPERTY_OS_NAME = "os.name";
        public static final java.lang.String SYSTEM_PROPERTY_OS_VERSION = "os.version";
        public static final java.lang.String SYSTEM_PROPERTY_PATH_SEPARATOR = "path.separator";

        public static java.lang.String getSystemProperty(final java.lang.String property) {
            if (! TextUtils.isEmpty(property)) {
                final java.lang.String systemProperty = System.getSystemProperty(property);
                if (systemProperty != null) {
                    return systemProperty;
                }
                else {
                    Log.w("SystemProperty was NULL");
                }
            }
            return null;
        }

        public static java.lang.String uname() {
            return java.lang.String.format("%s %s %s",
                    getSystemProperty(SYSTEM_PROPERTY_OS_NAME),
                    getSystemProperty(SYSTEM_PROPERTY_OS_VERSION),
                    getSystemProperty(SYSTEM_PROPERTY_OS_ARCH));
        }

        public static final java.lang.String SYSTEM_SERVICE_WINDOW_SERVICE = Context.WINDOW_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE = Context.LAYOUT_INFLATER_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_ACTIVITY_SERVICE = Context.ACTIVITY_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_POWER_SERVICE = Context.POWER_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_ALARM_SERVICE = Context.ALARM_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_NOTIFICATION_SERVICE = Context.NOTIFICATION_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_KEYGUARD_SERVICE = Context.KEYGUARD_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_LOCATION_SERVICE = Context.LOCATION_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_SEARCH_SERVICE = Context.SEARCH_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_SENSOR = Context.SENSOR_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_STORAGE = Context.STORAGE_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_VIBRATOR = Context.VIBRATOR_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_CONNECTIVITY = Context.CONNECTIVITY_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_WIFI = Context.WIFI_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_AUDIO = Context.AUDIO_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_MEDIA_ROUTER = Context.MEDIA_ROUTER_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_TELEPHONY = Context.TELEPHONY_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_INPUT_METHOD = Context.INPUT_METHOD_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_UI_MODE = Context.UI_MODE_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_DOWNLOAD = Context.DOWNLOAD_SERVICE;

        public static Object getSystemService(final Context context, final java.lang.String service) {
            if (context != null) {
                return context.getSystemService(service);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

    }

    public static class File {

        public static final java.lang.String ASSETS_PREFIX = "file:///android_asset/";

        // Get

        public static java.io.File get(final java.lang.String path) {
            if (! TextUtils.isEmpty(path)) {
                return new java.io.File(path);
            }
            else {
                Log.w("Path was NULL");
            }
            return null;
        }

        // Open

        public static InputStream openFile(final java.io.File file) {
            if (file != null) {
                InputStream inputStream = null;

                try {
                    inputStream = new FileInputStream(file);
                    inputStream.close();
                }
                catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException: " + e.getMessage());
                }
                catch (IOException e) {
                    Log.e("IOException: " + e.getMessage());
                }

                return inputStream;
            }
            else {
                Log.w("File was NULL");
            }
            return null;
        }

        public static InputStream openRaw(final Context context, final int id) {
            if (context != null) {
                return context.getResources().openRawResource(id);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static InputStream openAsset(final Context context, final java.lang.String name) {
            if (context != null) {
                try {
                    return context.getAssets().open(name);
                }
                catch (IOException e) {
                    Log.e("IOException: " + e.getMessage());
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Read & Write

        public static java.lang.String readString(final InputStream inputStream) {
            if (inputStream != null) {
                java.lang.String string = "";
                final Scanner scanner = new Scanner(inputStream);
                while (scanner.hasNextLine()) {
                    string = string.concat(scanner.nextLine() + "\n");
                }
                return string;
            }
            else {
                Log.w("InputStream was NULL");
            }
            return null;
        }

        public static java.lang.String readString(final java.io.File file) {
            if (file != null) {
                try {
                    return readString(new FileInputStream(file));
                }
                catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("File was NULL");
            }
            return null;
        }

        public static Bitmap readBitmap(final java.io.File file) {
            if (file != null) {
                try {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    return BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                }
                catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("File was NULL");
            }
            return null;
        }

        public static Boolean writeString(final OutputStream outputStream, final java.lang.String content) {
            if (outputStream != null) {
                if (! TextUtils.isEmpty(content)) {
                    try {
                        outputStream.write(content.getBytes());
                        outputStream.close();
                        return true;
                    }
                    catch (FileNotFoundException e) {
                        Log.e("FileNotFoundException: " + e.getMessage());
                    }
                    catch (IOException e) {
                        Log.e("IOException: " + e.getMessage());
                    }
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("OutputStream was NULL");
            }
            return false;
        }

        public static Boolean writeString(final java.io.File file, final java.lang.String content) {
            try {
                return writeString(new FileOutputStream(file), content);
            }
            catch (FileNotFoundException e) {
                Log.e("FileNotFoundException: " + e.getMessage());
            }
            return null;
        }

        public static Boolean writeBitmap(final java.io.File file, final Bitmap bitmap) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                return true;
            }
            catch (FileNotFoundException e) {
                Log.e("FileNotFoundException: " + e.getMessage());
            }
            return false;
        }

        // Serialize & Unserialize

        public static Boolean serialize(final java.io.File file, final Object object) {
            try {
                final FileOutputStream fileOutputStream = new FileOutputStream(file);
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
                objectOutputStream.close();
                return true;
            }
            catch (IOException e) {
                Log.e("IOException: " + e.getMessage());
            }
            return false;
        }

        public static Object unserialize(final java.io.File file) {
            try {
                final FileInputStream fileInputStream = new FileInputStream(file);
                final ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream);
                return objectOutputStream.readObject();
            }
            catch (IOException e) {
                Log.e("IOException: " + e.getMessage());
            }
            catch (ClassNotFoundException e) {
                Log.e("ClassNotFoundException: " + e.getMessage());
            }
            return null;
        }

        // Remove

        public static Boolean remove(final java.io.File file) {
            if (file != null) {
                return file.delete();
            }
            else {
                Log.w("File was NULL");
            }
            return false;
        }

        // Internal

        public static java.lang.String getInternalDir(final Context context, final java.lang.String name) {
            if (context != null) {
                final java.io.File dir = context.getFilesDir();
                if (dir != null) {
                    return (new java.io.File(dir.getAbsolutePath(), name).getAbsolutePath());
                }
                else {
                    Log.w("Context was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // External

        public static java.lang.String getExternalDir(final Context context, final java.lang.String name) {
            if (context != null) {
                final java.io.File dir = context.getExternalFilesDir(name);
                if (dir != null) {
                    return dir.getAbsolutePath();
                }
                else {
                    Log.w("File was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Cache

        public static java.lang.String getInternalCacheDir(final Context context) {
            if (context != null) {
                final java.io.File dir = context.getCacheDir();
                if (dir != null) {
                    return dir.getAbsolutePath();
                }
                else {
                    Log.w("File was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static java.lang.String getExternalCacheDir(final Context context) {
            if (context != null) {
                final java.io.File dir = context.getExternalCacheDir();
                if (dir != null) {
                    return dir.getAbsolutePath();
                }
                else {
                    Log.w("File was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static java.lang.String getDownloadCache() {
            return Environment.getDownloadCacheDirectory().getAbsolutePath();
        }

        // SDCard

        public static Boolean hasSdCardAvailable() {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }

        public static java.lang.String getSdCard() {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

    }

    public static class Keyboard {

        // Behavior can vary

        public static void show(final android.app.Activity activity) {
            final InputMethodManager inputMethodManager = (InputMethodManager) System.getSystemService(activity, Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
            else {
                Log.d("InputMethodManager was NULL");
            }
        }

        // Behavior can vary

        public static void hide(final android.app.Activity activity) {
            final InputMethodManager inputMethodManager = (InputMethodManager) System.getSystemService(activity, Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                final View view = activity.getCurrentFocus();
                if (view != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                else {
                    Log.d("View was NULL");
                }
            }
            else {
                Log.d("InputMethodManager was NULL");
            }
        }

        public static void keyboardCallback(final EditText editText, final KeyboardCallback callback) {
            if (editText != null) {
                if (callback != null) {
                    editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                        @Override
                        public boolean onEditorAction(final TextView textView, final int i, final KeyEvent keyEvent) {
                            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                                    (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                                callback.keyboardCallback();
                            }
                            // Keep false
                            return false;
                        }

                    });
                }
                else {
                    Log.w("Callback was NULL");
                }
            }
            else {
                Log.w("EditText was NULL");
            }
        }

        public static interface KeyboardCallback {

            public void keyboardCallback();

        }

    }

    public static class Audio {

        public static Integer volume(final Context context, final int streamType) {
            if (context != null) {
                final AudioManager audioManager = (AudioManager) System.getSystemService(context, System.SYSTEM_SERVICE_AUDIO);
                audioManager.getStreamVolume(streamType);
            }
            else {
                Log.w("Context was NULL");
            }
            return 0;
        }

        public static Integer volume(final Context context) {
            return volume(context, AudioManager.STREAM_SYSTEM);
        }

        public static void play(final java.lang.String path) {
            try {
                final MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
            catch (IOException e) {
                Log.e("IOException: " + e.getMessage());
            }
        }

        public static void play(final Context context, final Uri uri) {
            if (context != null) {
                if (uri != null) {
                    try {
                        final MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(context, uri);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                    catch (IOException e) {
                        Log.e("IOException: " + e.getMessage());
                    }
                }
                else {
                    Log.w("Uri was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
        }

        public static void play(final Context context, final int rawId) {
            if (context != null) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(context, rawId);
                mediaPlayer.start();
            }
            else {
                Log.w("Context was NULL");
            }
        }

    }

    public static class Vibrator {

        @SuppressLint("NewApi")
        private static void vibrateNew(final android.os.Vibrator vibrator, final long duration) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(duration);
            }
            else {
                Log.d("Vibrator cannot vibrate");
            }
        }

        @SuppressLint("NewApi")
        private static void vibrateNew(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(durations, (repeat ? 0 : -1));
            }
            else {
                Log.d("Vibrator cannot vibrate");
            }
        }

        private static void vibrateOld(final android.os.Vibrator vibrator, final long duration) {
            vibrator.vibrate(duration);
        }

        private static void vibrateOld(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
            vibrator.vibrate(durations, (repeat ? 0 : -1));
        }

        public static void vibrate(final Context context, final long duration) {
            final android.os.Vibrator vibrator = (android.os.Vibrator) System.getSystemService(context, System.SYSTEM_SERVICE_VIBRATOR);
            if (vibrator != null) {
                if (Android.getApi() < Build.VERSION_CODES.HONEYCOMB) {
                    vibrateOld(vibrator, duration);
                }
                else {
                    vibrateNew(vibrator, duration);
                }
            }
            else {
                Log.w("Vibrator was NULL");
            }
        }

        public static void vibrate(final Context context, final long[] durations, final Boolean repeat) {
            final android.os.Vibrator vibrator = (android.os.Vibrator) System.getSystemService(context, System.SYSTEM_SERVICE_VIBRATOR);
            if (vibrator != null) {
                if (Android.getApi() < Build.VERSION_CODES.HONEYCOMB) {
                    vibrateOld(vibrator, durations, repeat);
                }
                else {
                    vibrateNew(vibrator, durations, repeat);
                }
            }
            else {
                Log.w("Vibrator was NULL");
            }
        }

    }

    public static class Network {

        public static java.lang.String getDefaultUserAgent() {
            final java.lang.String userAgent = System.getSystemProperty(System.SYSTEM_PROPERTY_HTTP_AGENT);
            return (userAgent != null ? userAgent : java.lang.String.format("%s-%d", PLATFORM, Android.getApi()));
        }

        public static java.lang.String makeUserAgent(final Context context) {
            if (context != null) {
                return PLATFORM + "-" +
                        Android.getApi() + "/" +
                        Android.getPackage(context) + "/" +
                        Android.getVersionCode(context);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static Boolean isConnectedToInternet(final Context context) {
            final NetworkInfo networkInfo = ((ConnectivityManager) System.getSystemService(context, System.SYSTEM_SERVICE_CONNECTIVITY)).getActiveNetworkInfo();
            if (networkInfo != null) {
                return (! networkInfo.isConnected());
            }
            else {
                Log.w("NetworkInfo was NULL");
            }
            return false;
        }

        public static java.lang.String getMacAddress(final Context context) {
            if (context != null) {
                final WifiManager wifiManager = (WifiManager) System.getSystemService(context, System.SYSTEM_SERVICE_WIFI);
                final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                final java.lang.String macAddress = wifiInfo.getMacAddress();
                if (! TextUtils.isEmpty(macAddress)) {
                    return macAddress;
                }
                else {
                    Log.w("MacAddress was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static Boolean isValidUrl(final java.lang.String url) {
            if (! TextUtils.isEmpty(url)) {
                return URLUtil.isValidUrl(url);
            }
            else {
                Log.w("Url was null");
            }
            return false;
        }

        public static List<java.lang.String> ipAddresses() {
            final List<java.lang.String> ipAddresses = new ArrayList<java.lang.String>();
            try {
                for (final Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces(); enumerationNetworkInterface.hasMoreElements();) {
                    final NetworkInterface networkInterface = enumerationNetworkInterface.nextElement();
                    for (Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses(); enumerationInetAddress.hasMoreElements();) {
                        final InetAddress inetAddress = enumerationInetAddress.nextElement();
                        final java.lang.String ipAddress = inetAddress.getHostAddress();
                        if (! inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipAddress)) {
                            ipAddresses.add(ipAddress);
                        }
                    }
                }
            }
            catch (SocketException e) {
                Log.e("SocketException: " + e.getMessage());
            }
            return ipAddresses;
        }

    }

    public static class Notification {

        public static void toastShort(final Context context, final java.lang.String text) {
            if (context != null) {
                if (! TextUtils.isEmpty(text)) {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
        }

        public static void toastLong(final Context context, final java.lang.String text) {
            if (context != null) {
                if (! TextUtils.isEmpty(text)) {
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
        }

        public static void croutonInfo(final android.app.Activity activity, final java.lang.String text) {
            if (activity != null) {
                if (! TextUtils.isEmpty(text)) {
                    AppMsg.makeText(activity, text, AppMsg.STYLE_INFO).show();
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("Activity was NULL");
            }
        }

        public static void croutonConfirm(final android.app.Activity activity, final java.lang.String text) {
            if (activity != null) {
                if (! TextUtils.isEmpty(text)) {
                    AppMsg.makeText(activity, text, AppMsg.STYLE_CONFIRM).show();
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("Activity was NULL");
            }
        }

        public static void croutonAlert(final android.app.Activity activity, final java.lang.String text) {
            if (activity != null) {
                if (! TextUtils.isEmpty(text)) {
                    AppMsg.makeText(activity, text, AppMsg.STYLE_ALERT).show();
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("Activity was NULL");
            }
        }

        public static NotificationManager notificationManager(final Context context) {
            if (context != null) {
                return (NotificationManager) System.getSystemService(context, System.SYSTEM_SERVICE_NOTIFICATION_SERVICE);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static android.app.Notification notification(final Context context, final int smallIcon, final java.lang.String title, final java.lang.String message, final PendingIntent pendingIntent) {
            if (context != null) {
                final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                notificationBuilder.setSmallIcon(smallIcon);
                if (! TextUtils.isEmpty(title)) {
                    notificationBuilder.setContentTitle(title);
                }
                if (! TextUtils.isEmpty(message)) {
                    notificationBuilder.setContentText(message);
                }
                if (pendingIntent != null) {
                    notificationBuilder.setContentIntent(pendingIntent);
                }
                final android.app.Notification notification = notificationBuilder.build();
                notification.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
                notification.defaults |= android.app.Notification.DEFAULT_SOUND;
                notification.defaults |= android.app.Notification.DEFAULT_VIBRATE;
                return notification;
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static android.app.Notification notification(final Context context, final int smallIcon, final java.lang.String title, final java.lang.String message) {
            return notification(context, smallIcon, title, message, null);
        }

        public static void notify(final NotificationManager notificationManager, final android.app.Notification notification, final Integer id) {
            if (notificationManager != null) {
                if (notification != null) {
                    notificationManager.notify(id, notification);
                }
                else {
                    Log.w("Notification was NULL");
                }
            }
            else {
                Log.w("NotificationManager was NULL");
            }
        }

        public static void cancel(final NotificationManager notificationManager, final Integer id) {
            if (notificationManager != null) {
                notificationManager.cancel(id);
            }
            else {
                Log.w("NotificationManager was NULL");
            }
        }

    }

    public static class Runtime {

        public static int getProcessors() {
            return java.lang.Runtime.getRuntime().availableProcessors();
        }

        public static long getFreeMemory() {
            return java.lang.Runtime.getRuntime().freeMemory();
        }

        public static long getMaxMemory() {
            return java.lang.Runtime.getRuntime().maxMemory();
        }

        public static long getTotalMemory() {
            return java.lang.Runtime.getRuntime().totalMemory();
        }

    }

    public static class String {

        public static java.lang.String capitalize(final java.lang.String string) {
            if (! TextUtils.isEmpty(string)) {
                return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
            }
            else {
                Log.w("String was NULL");
            }
            return string;
        }

        public static Boolean isNumeric(final java.lang.String string) {
            if (! TextUtils.isEmpty(string)) {
                return TextUtils.isDigitsOnly(string);
            }
            else {
                Log.w("String was NULL");
            }
            return false;
        }

        public static Boolean contains(final java.lang.String[] strings, final java.lang.String string) {
            if (strings != null) {
                for (final java.lang.String s : strings) {
                    if (s.equals(string)) {
                        return true;
                    }
                }
            }
            else {
                Log.w("Strings was NULL");
            }
            return false;
        }

    }

    public static class Time {

        // UNIX Timestamp (length: 1-11)

        public static Long timestamp() {
            return (java.lang.System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS);
        }

        // Relative elapsed time

        public static java.lang.String relative(final Long time) {
            return DateUtils.getRelativeTimeSpanString(time, new Date().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        }

        public static java.lang.String relative(final Long from, final Long to) {
            return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        }

    }

    public static class WebView {

        private static final java.lang.String CHARSET = HTTP.UTF_8;
        private static final java.lang.String MIME_TYPE = "text/html";

        public static android.webkit.WebView fromUrl(final Context context, final java.lang.String url) {
            if (context != null) {
                final android.webkit.WebView webView = new android.webkit.WebView(context);
                webView.loadUrl(url);
                return webView;
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static android.webkit.WebView fromAsset(final Context context, final java.lang.String asset) {
            if (context != null) {
                final android.webkit.WebView webView = new android.webkit.WebView(context);
                webView.loadDataWithBaseURL(File.ASSETS_PREFIX, asset, MIME_TYPE, CHARSET, "");
                return webView;
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static android.webkit.WebView fromHtml(final Context context, final java.lang.String source) {
            if (context != null) {
                final android.webkit.WebView webView = new android.webkit.WebView(context);
                webView.loadData(source, MIME_TYPE, CHARSET);
                return webView;
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

    }

    public static class Hash {

        public static final java.lang.String MD5 = "MD5";
        public static final java.lang.String SHA = "SHA";

        private static final Integer MD5_LENGTH = 32;
        private static final Integer SHA_LENGTH = 40;

        private static java.lang.String hash(final java.lang.String algorithm, final java.lang.String string, final Integer length) {
            try {
                final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
                messageDigest.reset();
                messageDigest.update(string.getBytes());

                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.setLength(0);

                final byte digest[] = messageDigest.digest();
                for (final byte d : digest) {
                    final int b = d & 255;
                    if (b < length) {
                        stringBuilder.append('0');
                    }
                    stringBuilder.append(Integer.toHexString(b));
                }
                return stringBuilder.toString();
            }
            catch (NoSuchAlgorithmException e) {
                Log.e("NoSuchAlgorithmException: " + e.getMessage());
            }
            return null;
        }

        public static java.lang.String md5(final java.lang.String string) {
            return hash(string, MD5, MD5_LENGTH);
        }

        public static java.lang.String sha(final java.lang.String string) {
            return hash(string, SHA, SHA_LENGTH);
        }

    }

    public static class Facebook {

        private static final java.lang.String GRAPH_ME_URL = "https://graph.facebook.com/me/feed";

        public static final java.lang.String PERMISSION_BASIC_INFO = "basic_info";
        public static final java.lang.String PERMISSION_READ_STREAM = "read_stream";
        public static final java.lang.String PERMISSION_READ_FRIENDLISTS = "read_friendlists";
        public static final java.lang.String PERMISSION_MANAGE_FRIENDLISTS = "manage_friendlists";
        public static final java.lang.String PERMISSION_MANAGE_NOTIFICATIONS = "manage_notifications";
        public static final java.lang.String PERMISSION_PUBLISH_STREAM = "publish_stream";
        public static final java.lang.String PERMISSION_PUBLISH_CHECKINS = "publish_checkins";
        public static final java.lang.String PERMISSION_OFFLINE_ACCESS = "offline_access";
        public static final java.lang.String PERMISSION_USER_PHOTOS = "user_photos";
        public static final java.lang.String PERMISSION_USER_LIKES = "user_likes";
        public static final java.lang.String PERMISSION_USER_GROUPS = "user_groups";
        public static final java.lang.String PERMISSION_FRIENDS_PHOTOS = "friends_photos";

        private static Facebook INSTANCE = null;

        private java.lang.String mAppId;
        private Integer mRequestCode;
        private AQuery mAQuery;
        private FacebookHandle mHandle;

        public static Facebook newInstance(final Context context, final java.lang.String appId, final Integer requestCode) {
            if (INSTANCE == null) {
                if (context != null) {
                    if (! TextUtils.isEmpty(appId)) {
                        INSTANCE = new Facebook(context, appId, requestCode);
                    }
                    else {
                        Skeleton.Log.w("AppId was NULL");
                    }
                }
                else {
                    Skeleton.Log.w("Context was NULL");
                }
            }
            return INSTANCE;
        }

        public static Facebook getInstance() {
            return INSTANCE;
        }

        private Facebook(final Context context, final java.lang.String appId, final Integer requestCode) {
            mAppId = appId;
            mRequestCode = requestCode;
            mAQuery = new AQuery(context);
        }

        public void auth(final android.app.Activity activity, final FacebookCallback callback, final java.lang.String permissions) {
            mHandle = new FacebookHandle(activity, mAppId, permissions) {

                @Override
                public boolean expired(final AbstractAjaxCallback<?, ?> callback, final AjaxStatus status) {
                    if (status.getCode() == HttpStatus.SC_UNAUTHORIZED) {
                        return true;
                    }
                    return super.expired(callback, status);
                }

            };
            mHandle.sso(mRequestCode);

            mAQuery.auth(mHandle)
                    .ajax(GRAPH_ME_URL, java.lang.String.class, new AjaxCallback<java.lang.String>() {

                        @Override
                        public void callback(final java.lang.String url, final java.lang.String object, final AjaxStatus status) {
                            super.callback(url, object, status);

                            if (TextUtils.isEmpty(status.getError()) && ! status.getMessage().equalsIgnoreCase("cancel")) {
                                final java.lang.String token = mHandle.getToken();
                                if (! TextUtils.isEmpty(token)) {
                                    Skeleton.Log.d("Token: " + token);
                                    callback.facebookCallback(token);
                                }
                                else {
                                    Skeleton.Log.w("Token is NULL");
                                    if (callback != null) {
                                        callback.facebookCallback(null);
                                    }
                                }
                            }
                            else {
                                Skeleton.Log.w("Message: " + status.getMessage());
                                Skeleton.Log.w("Error: " + status.getError());
                                if (callback != null) {
                                    callback.facebookCallback(null);
                                }
                            }
                        }

                    });
        }

        public void unauth() {
            if (mHandle != null) {
                if (! TextUtils.isEmpty(mHandle.getToken())) {
                    mHandle.unauth();
                }
                else {
                    Skeleton.Log.w("Token was NULL");
                }
            }
            else {
                Skeleton.Log.w("Handle was NULL");
            }
        }

        public java.lang.String getToken() {
            if (mHandle != null) {
                return mHandle.getToken();
            }
            else {
                Skeleton.Log.w("Handle was NULL");
            }
            return null;
        }

        public void onActivityResult(final int requestCode, final int resultCode, final android.content.Intent data) {
            if (requestCode == mRequestCode) {
                if (mHandle != null) {
                    mHandle.onActivityResult(requestCode, resultCode, data);
                }
                else {
                    Skeleton.Log.w("Handle was NULL");
                }
            }
        }

        public void onDestroy() {
            if (mAQuery != null) {
                mAQuery.dismiss();
            }
            else {
                Skeleton.Log.w("AQuery was NULL");
            }
        }

        public static interface FacebookCallback {

            public void facebookCallback(final java.lang.String token);

        }

    }

    public static class WebService {

        private Context mContext;
        private Integer mId;
        private java.lang.String mUrl;

        private Boolean check() {
            if (mContext == null) {
                Skeleton.Log.w("Context was NULL");
                return false;
            }
            if (mId < 0) {
                Skeleton.Log.w("Id was invalid");
                return false;
            }
            if (TextUtils.isEmpty(mUrl)) {
                Skeleton.Log.w("Url was NULL");
                return false;
            }
            if (! Skeleton.Network.isValidUrl(mUrl)) {
                Skeleton.Log.w("Url was invalid");
                return false;
            }
            return true;
        }

        public WebService(final Context context, final Integer id, final java.lang.String url) {
            mContext = context;
            mId = id;
            mUrl = url;
        }

        public void run(final WebServiceCallback callback) {
            if (! check()) {
                Skeleton.Log.d("check() failed");
                if (callback != null) {
                    callback.webServiceCallback(mId, new Response(null, null));
                }
            }
            else {
                final AjaxCallback<java.lang.String> ajaxCallback = new AjaxCallback<java.lang.String>() {

                    @Override
                    public void callback(final java.lang.String url, final java.lang.String content, final AjaxStatus ajaxStatus) {
                        if (callback != null) {
                            callback.webServiceCallback(mId, new Response(ajaxStatus, content));
                        }
                        else {
                            Skeleton.Log.w("Callback was NULL");
                        }
                    }

                }
                        .url(mUrl)
                        .type(java.lang.String.class)
                        .header("User-Agent", Skeleton.Network.getDefaultUserAgent());

                new AQuery(mContext).ajax(ajaxCallback);
            }
        }

        public static class Response {

            public Integer code;
            public Boolean success;
            public java.lang.String content;
            public Long duration;

            public Response(final AjaxStatus ajaxStatus, final java.lang.String content) {
                if (ajaxStatus != null) {
                    this.code = ajaxStatus.getCode();
                    this.success = (this.code == HttpStatus.SC_OK);
                    this.duration = ajaxStatus.getDuration();
                    this.content = (this.success ? content : Skeleton.String.capitalize(ajaxStatus.getMessage()));
                }
                else {
                    Skeleton.Log.w("AjaxStatus was NULL");
                    this.code = -1;
                    this.success = false;
                    this.duration = 0L;
                    this.content = content;
                }
            }

        }

        public static interface WebServiceCallback {

            public void webServiceCallback(final Integer id, final Response response);

        }

    }

    public static class ImageDownloader {

        private Context mContext;
        private ImageView mImageView;
        private java.lang.String mUrl;
        private Boolean[] mCache = { false, false };

        public ImageDownloader(final Context context, final ImageView imageView, final java.lang.String url) {
            mContext = context;
            mImageView = imageView;
            mUrl = url;
        }

        public ImageDownloader cache(final Boolean file, final Boolean memory) {
            mCache[0] = file;
            mCache[1] = memory;
            return this;
        }

        public void run(final ImageDownloaderCallback callback) {
            if (mContext != null) {
                if (! TextUtils.isEmpty(mUrl)) {
                    if (Skeleton.Network.isValidUrl(mUrl)) {
                        new AQuery(mContext)
                                .ajax(new AjaxCallback<Bitmap>() {

                                    @Override
                                    public void callback(final java.lang.String url, final Bitmap bitmap, final AjaxStatus status) {
                                        if (bitmap != null) {
                                            if (mImageView != null) {
                                                mImageView.setImageBitmap(bitmap);
                                                result(callback, mImageView, bitmap);
                                            }
                                            else {
                                                Skeleton.Log.w("ImageView was NULL");
                                                result(callback, null, null);
                                            }
                                        }
                                        else {
                                            Skeleton.Log.w("Bitmap was NULL");
                                            result(callback, null, null);
                                        }
                                    }

                                }
                                        .fileCache(mCache[0])
                                        .memCache(mCache[1])
                                        .url(mUrl)
                                        .type(Bitmap.class)
                                        .header("User-Agent", Skeleton.Network.getDefaultUserAgent()));
                    }
                    else {
                        Skeleton.Log.w("Url was invalid");
                        result(callback, null, null);
                    }
                }
                else {
                    Skeleton.Log.w("Url was NULL");
                    result(callback, null, null);
                }
            }
            else {
                Skeleton.Log.w("Context was NULL");
                result(callback, null, null);
            }
        }

        public void run() {
            run(null);
        }

        private void result(final ImageDownloaderCallback callback, final ImageView imageView, final Bitmap bitmap) {
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            else {
                Skeleton.Log.w("ImageView was NULL");
            }

            if (callback != null) {
                callback.imageDownloaderCallback(imageView, bitmap);
            }
            else {
                Skeleton.Log.w("Callback was NULL");
            }
        }

        public static interface ImageDownloaderCallback {

            public void imageDownloaderCallback(final ImageView imageView, final Bitmap bitmap);

        }

    }

    public static class Location implements LocationListener {

        private LocationManager mLocationManager;
        private LocationCallback mLocationCallback;
        private android.location.Location mLocation;

        public Location(final Context context, final LocationCallback locationCallback) {
            if (context != null) {
                mLocationManager = (LocationManager) System.getSystemService(context, System.SYSTEM_SERVICE_LOCATION_SERVICE);
                if (locationCallback != null) {
                    mLocationCallback = locationCallback;
                }
                else {
                    Log.d("LocationCallback was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
        }

        public android.location.Location start(final Boolean gps) {
            if (mLocationManager != null) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (gps) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                return mLocation;
            }
            else {
                Log.w("LocationManager was NULL");
            }
            return null;
        }

        public void stop() {
            if (mLocationManager != null) {
                mLocationManager.removeUpdates(this);
            }
            else {
                Log.w("LocationManager was NULL");
            }
        }

        public android.location.Location getLocation() {
            return mLocation;
        }

        @Override
        public void onLocationChanged(final android.location.Location location) {
            mLocation = location;

            if (mLocationCallback != null) {
                mLocationCallback.locationCallback(mLocation);
            }
        }

        @Override
        public void onStatusChanged(final java.lang.String s, final int i, final Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(final java.lang.String provider) {
            if (mLocationCallback != null) {
                mLocationCallback.providerCallback(provider, true);
            }
        }

        @Override
        public void onProviderDisabled(final java.lang.String provider) {
            if (mLocationCallback != null) {
                mLocationCallback.providerCallback(provider, false);
            }
        }

        public static interface LocationCallback {

            public void providerCallback(final java.lang.String provider, final Boolean enabled);
            public void locationCallback(final android.location.Location location);

        }

    }

    public static class Screen {

        // <http://developer.android.com/reference/android/util/DisplayMetrics.html>
        public static final int DENSITY_LDPI = 120;
        public static final int DENSITY_MDPI = 160;
        public static final int DENSITY_HDPI = 240;
        public static final int DENSITY_XHDPI = 320;
        public static final int DENSITY_XXHDPI = 480;
        public static final int DENSITY_XXXHDPI = 640;
        public static final int DENSITY_TV = 213;

        public static void wakeLock(final android.app.Activity activity) {
            if (activity != null) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            else {
                Log.w("Activity was NULL");
            }
        }

        public static Boolean isOn(final Context context) {
            if (context != null) {
                return ((PowerManager) System.getSystemService(context, System.SYSTEM_SERVICE_POWER_SERVICE)).isScreenOn();
            }
            else {
                Log.w("Context was NULL");
            }
            return false;
        }

        public static int density(final Context context) {
            if (context != null) {
                return context.getResources().getDisplayMetrics().densityDpi;
            }
            else {
                Log.w("Context was NULL");
            }
            return 0;
        }

        public static int height(final Context context) {
            if (context != null) {
                return context.getResources().getDisplayMetrics().heightPixels;
            }
            else {
                Log.w("Context was NULL");
            }
            return 0;
        }

        public static int width(final Context context) {
            if (context != null) {
                return context.getResources().getDisplayMetrics().widthPixels;
            }
            else {
                Log.w("Context was NULL");
            }
            return 0;
        }

        public static Integer orientation(final Context context) {
            return ((WindowManager) System.getSystemService(context, System.SYSTEM_SERVICE_WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        }

        public static int pixelsFromDp(final Context context, final Float dp) {
            if (context != null) {
                if (dp > 0) {
                    return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
                }
                else {
                    return 0;
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return 0;
        }

    }

    public static class Intent {

        public static final java.lang.String BROADCAST_TIME_TICK = android.content.Intent.ACTION_TIME_TICK;
        public static final java.lang.String BROADCAST_TIME_CHANGED = android.content.Intent.ACTION_TIME_CHANGED;
        public static final java.lang.String BROADCAST_TIMEZONE_CHANGED = android.content.Intent.ACTION_TIMEZONE_CHANGED;
        public static final java.lang.String BROADCAST_BOOT_COMPLETED = android.content.Intent.ACTION_BOOT_COMPLETED;
        public static final java.lang.String BROADCAST_PACKAGE_ADDED = android.content.Intent.ACTION_PACKAGE_ADDED;
        public static final java.lang.String BROADCAST_PACKAGE_CHANGED = android.content.Intent.ACTION_PACKAGE_CHANGED;
        public static final java.lang.String BROADCAST_PACKAGE_REMOVED = android.content.Intent.ACTION_PACKAGE_REMOVED;
        public static final java.lang.String BROADCAST_PACKAGE_RESTARTED = android.content.Intent.ACTION_PACKAGE_RESTARTED;
        public static final java.lang.String BROADCAST_PACKAGE_DATA_CLEARED = android.content.Intent.ACTION_PACKAGE_DATA_CLEARED;
        public static final java.lang.String BROADCAST_UID_REMOVED = android.content.Intent.ACTION_UID_REMOVED;
        public static final java.lang.String BROADCAST_BATTERY_CHANGED = android.content.Intent.ACTION_BATTERY_CHANGED;
        public static final java.lang.String BROADCAST_POWER_CONNECTED = android.content.Intent.ACTION_POWER_CONNECTED;
        public static final java.lang.String BROADCAST_POWER_DISCONNECTED = android.content.Intent.ACTION_POWER_DISCONNECTED;
        public static final java.lang.String BROADCAST_SHUTDOWN = android.content.Intent.ACTION_SHUTDOWN;

        public static Boolean canHandle(final Context context, final android.content.Intent intent) {
            if (context != null) {
                final PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    return (resolveInfos.size() > 0);
                }
                else {
                    Log.w("PackageManager was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return false;
        }

        public static void web(final android.app.Activity activity, final java.lang.String url) {
            if (! TextUtils.isEmpty(url)) {
                if (! Network.isValidUrl(url)) {
                    if (activity != null) {
                        activity.startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url)));
                    }
                    else {
                        Log.w("Activity was NULL");
                    }
                }
                else {
                    Log.w("Url was invalid");
                }
            }
            else {
                Log.w("Url was NULL");
            }
        }

        public static void market(final android.app.Activity activity, final java.lang.String pkg) {
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + pkg));
            if (activity != null) {
                activity.startActivity(intent);
            }
            else {
                Log.w("Activity was NULL");
            }
        }

        public static void market(final android.app.Activity activity) {
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + Android.getPackage(activity)));
            if (activity != null) {
                activity.startActivity(intent);
            }
            else {
                Log.w("Activity was NULL");
            }
        }

        public static void email(final android.app.Activity activity, final java.lang.String[] to, final java.lang.String subject, final java.lang.String text) {
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(android.content.Intent.EXTRA_EMAIL, to);
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
            if (activity != null) {
                activity.startActivity(android.content.Intent.createChooser(intent, "Send An Email"));
            }
            else {
                Log.w("Activity was NULL");
            }
        }

        public static void image(final android.app.Activity activity, final Uri uri) {
            final android.content.Intent intent = new android.content.Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            if (uri != null) {
                intent.setDataAndType(uri, "image/*");
                if (activity != null) {
                    activity.startActivity(intent);
                }
                else {
                    Log.w("Activity was NULL");
                }
            }
            else {
                Log.w("Uri was NULL");
            }
        }

        private static final int REQUEST_CODE_CAMERA = 111;

        public static void camera(final android.app.Activity activity) {
            if (activity != null) {
                if (Android.hasFeature(activity, Android.Features.CAMERA)) {
                    final android.content.Intent intent = new android.content.Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (canHandle(activity, intent)) {
                        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    }
                    else {
                        Log.w("Cannot handle Intent");
                    }
                }
                else {
                    Log.w("CAMERA was unavailable");
                }
            }
            else {
                Log.w("Activity was NULL");
            }
        }

        private static final int REQUEST_CODE_GALLERY = 222;

        public static void gallery(final android.app.Activity activity) {
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_PICK);
            intent.setType("image/*");
            if (activity != null) {
                activity.startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Log.w("Activity was NULL");
            }
        }

        public static Bitmap onActivityResult(final Context context, final int requestCode, final int resultCode, final android.content.Intent intent) {
            if (context != null) {
                if (intent != null) {
                    if (resultCode == android.app.Activity.RESULT_OK) {
                        switch (requestCode) {
                            case REQUEST_CODE_CAMERA:
                                final Bundle bundle = intent.getExtras();
                                if (bundle != null) {
                                    return (Bitmap) bundle.get("data");
                                }
                                else {
                                    Log.w("Bundle was NULL");
                                }
                                break ;
                            case REQUEST_CODE_GALLERY:
                                final Uri uri = intent.getData();
                                if (uri != null) {
                                    try {
                                        final InputStream inputStream = context.getContentResolver().openInputStream(uri);
                                        if (inputStream != null) {
                                            return Graphics.decodeUri(context, uri);
                                        }
                                        else {
                                            Log.w("InputStream was NULL");
                                        }
                                    }
                                    catch (FileNotFoundException e) {
                                        Log.e("FileNotFoundException: " + e.getMessage());
                                    }
                                }
                                else {
                                    Log.w("Uri was NULL");
                                }
                                break ;
                        }
                    }
                    else {
                        Log.d("ResultCode was KO");
                    }
                }
                else {
                    Log.w("Intent was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

    }

    public static class Activity {

        public static void indeterminate(final SherlockActivity sherlockActivity) {
            if (sherlockActivity != null) {
                sherlockActivity.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
                sherlockActivity.setSupportProgressBarIndeterminateVisibility(true);
            }
            else {
                Log.w("SherlockActivity was NULL");
            }
        }

        public static void indeterminate(final SherlockActivity sherlockActivity, final Boolean on) {
            if (sherlockActivity != null) {
                sherlockActivity.setSupportProgressBarIndeterminate(on);
            }
            else {
                Log.w("SherlockActivity was NULL");
            }
        }

        public static void error(final Context context, final java.lang.String message, final DialogInterface.OnClickListener onClickListener) {
            if (context != null) {
                if (! TextUtils.isEmpty(message)) {
                    new AlertDialog.Builder(context)
                            .setMessage(message)
                            .setNeutralButton(android.R.string.ok, onClickListener)
                            .setCancelable(false)
                            .create()
                            .show();
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
        }

        public static void error(final Context context, final java.lang.String message) {
            error(context, message, null);
        }

    }

    public static class Graphics {

        public static Bitmap decodeUri(final Context context, final Uri uri, final Integer downsample) {
            if (context != null) {
                final BitmapFactory.Options bitmapFactoryOptionsTmp = new BitmapFactory.Options();
                bitmapFactoryOptionsTmp.inJustDecodeBounds = true;
                try {
                    final InputStream inputStream = context.getContentResolver().openInputStream(uri);
                    if (inputStream != null) {
                        BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptionsTmp);

                        int width = bitmapFactoryOptionsTmp.outWidth;
                        int height = bitmapFactoryOptionsTmp.outHeight;
                        int scale = 1;
                        if (downsample > 0) {
                            while (true) {
                                if (width / 2 < downsample || height / 2 < downsample) {
                                    break ;
                                }
                                width /= 2;
                                height /= 2;
                                scale *= 2;
                            }
                            final BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
                            bitmapFactoryOptions.inJustDecodeBounds = false;
                            bitmapFactoryOptions.inSampleSize = scale;
                            bitmapFactoryOptions.inPurgeable = true;
                            return BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptions);
                        }
                        else {
                            Log.w("Downsample was negative");
                        }
                    }
                    else {
                        Log.w("InputStream was NULL");
                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        private static Bitmap decodeUri(final Context context, final Uri uri) {
            return decodeUri(context, uri, Screen.density(context));
        }

        public static Bitmap bitmapFromUri(final Context context, final Uri uri) {
            if (context != null) {
                try {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
                }
                catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static Bitmap bitmapFromDrawable(final Drawable drawable) {
            if (drawable != null) {
                if (drawable instanceof BitmapDrawable) {
                    return ((BitmapDrawable) drawable).getBitmap();
                }

                final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                return bitmap;
            }
            else {
                Log.w("Drawable was NULL");
            }
            return null;
        }

        public static Bitmap rotateBitmap(final Bitmap bitmap, final float degrees) {
            if (bitmap != null) {
                final Matrix matrix = new Matrix();
                matrix.postRotate(degrees);
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
            else {
                Log.w("Bitmap was NULL");
            }
            return null;
        }

        public static Drawable drawableFromBitmap(final Context context, final Bitmap bitmap) {
            return new BitmapDrawable(context.getResources(), bitmap);
        }

        public static Drawable indeterminateDrawable(final Context context) {
            if (context != null) {
                return new ProgressBar(context).getIndeterminateDrawable();
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

    }

}
