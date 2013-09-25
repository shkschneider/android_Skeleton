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
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Patterns;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@SuppressWarnings("unused")
public abstract class Skeleton {

    public static final String PLATFORM = "Android";

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

        // If SCREENLAYOUT_SIZE is XLARGE for API >= HONEYCOMB

        public static Boolean tablet(final Context context) {
            if (context != null) {
                if (api() >= Build.VERSION_CODES.HONEYCOMB) {
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

        public static String id(final Context context) {
            if (context != null) {
                final String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
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

        public static String deviceId(final Context context) {
            if (context != null) {
                final String id = id(context);
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

        public static String uuid(final Context context) {
            if (context != null) {
                final String deviceId = deviceId(context);
                if (! TextUtils.isEmpty(deviceId)) {
                    return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString().replace("-", "");
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

        public static String randomId() {
            return UUID.randomUUID().toString().replace("-", "");
        }

        public static String codename() {
            return Build.DEVICE;
        }

        public static String manufacturer() {
            return Build.MANUFACTURER;
        }

        public static String device() {
            return Build.MODEL;
        }

        public static String release() {
            return Build.VERSION.RELEASE;
        }

        public static Integer api() {
            return Build.VERSION.SDK_INT;
        }

        public static Boolean debug() {
            return BuildConfig.DEBUG;
        }

        public static String packageName(final Context context) {
            if (context != null) {
                return context.getPackageName();
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static String name(final Context context) {
            if (context != null) {
                return context.getResources().getString(R.string.app_name);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static String versionName(final Context context) {
            if (context != null) {
                try {
                    final PackageManager packageManager = context.getPackageManager();
                    if (packageManager != null) {
                        return packageManager.getPackageInfo(packageName(context), PackageManager.GET_META_DATA).versionName;
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

        public static Integer versionCode(final Context context) {
            if (context != null) {
                try {
                    final PackageManager packageManager = context.getPackageManager();
                    if (packageManager != null) {
                        return packageManager.getPackageInfo(packageName(context), PackageManager.GET_META_DATA).versionCode;
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

        public static final String ACCOUNT_GOOGLE = "com.google";

        public static String account(final Context context) {
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

        public static String signature(final Context context) {
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

        public static TelephonyManager sim(final Context context) {
            if (context != null) {
                return (TelephonyManager) Systems.systemService(context, Systems.SYSTEM_SERVICE_TELEPHONY);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

    }

}
