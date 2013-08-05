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

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public abstract class AndroidHelper {

    public static final String PLATFORM = "Android";
    public static final int LENGTH_SHORT = 2000;
    public static final int LENGTH_LONG = 3500;

    // If SCREENLAYOUT_SIZE is XLARGE for API >= HONEYCOMB
    public static Boolean isTablet(final Context context) {
        if (context != null) {
            if (AndroidHelper.getApi() >= Build.VERSION_CODES.HONEYCOMB) {
                final android.content.res.Configuration configuration = context.getResources().getConfiguration();
                if (configuration != null) {
                    try {
                        return (Boolean) configuration.getClass().getMethod("isLayoutSizeAtLeast", int.class)
                                .invoke(configuration, android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE);
                    } catch (NoSuchMethodException e) {
                        LogHelper.e("NoSuchMethodException: " + e.getMessage());
                    } catch (IllegalAccessException e) {
                        LogHelper.e("IllegalAccessException: " + e.getMessage());
                    } catch (InvocationTargetException e) {
                        LogHelper.e("InvocationTargetException: " + e.getMessage());
                    }
                }
            }
        }
        return false;
    }

    public static void toastShort(final Context context, final String text) {
        if (context != null && ! TextUtils.isEmpty(text)) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void toastLong(final Context context, final String text) {
        if (context != null && ! TextUtils.isEmpty(text)) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    public static void croutonInfo(final Activity activity, final String text) {
        if (activity != null && ! TextUtils.isEmpty(text)) {
            final Crouton crouton = Crouton.makeText(activity, text, Style.INFO);
            crouton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    crouton.cancel();
                }

            });
            crouton.setConfiguration(new Configuration.Builder().setDuration(AndroidHelper.LENGTH_SHORT).build());
            crouton.show();
        }
    }

    public static void croutonConfirm(final Activity activity, final String text) {
        if (activity != null && ! TextUtils.isEmpty(text)) {
            final Crouton crouton = Crouton.makeText(activity, text, Style.CONFIRM);
            crouton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    crouton.cancel();
                }

            });
            crouton.setConfiguration(new Configuration.Builder().setDuration(AndroidHelper.LENGTH_SHORT).build());
            crouton.show();
        }
    }

    public static void croutonAlert(final Activity activity, final String text) {
        if (activity != null && ! TextUtils.isEmpty(text)) {
            final Crouton crouton = Crouton.makeText(activity, text, Style.ALERT);
            crouton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    crouton.cancel();
                }

            });
            crouton.setConfiguration(new Configuration.Builder().setDuration(AndroidHelper.LENGTH_LONG).build());
            crouton.show();
        }
    }

    // Get Android ID (length: 16)
    // The value may change if a factory reset is performed on the device.
    public static String getId(final Context context) {
        if (context != null) {
            final String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (! TextUtils.isEmpty(id)) {
                return id.toLowerCase();
            }
        }
        return null;
    }

    // Get Device ID (length: 32)
    // The value may change if a factory reset is performed on the device.
    public static String getDeviceId(final Context context) {
        if (context != null) {
            final String id = AndroidHelper.getId(context);
            if (! TextUtils.isEmpty(id)) {
                try {
                    final MessageDigest messageDigest = MessageDigest.getInstance(HashHelper.MD5);
                    if (messageDigest != null) {
                        return new BigInteger(1, messageDigest.digest(id.getBytes())).toString(16).toLowerCase();
                    }
                } catch (NoSuchAlgorithmException e) {
                    LogHelper.e("NoSuchAlgorithmException: " + e.getMessage());
                }
            }
        }
        return null;
    }

    // Get UUID from Device ID (RFC4122, length: 36)
    // The value may change if a factory reset is performed on the device.
    public static String getUUID(final Context context) {
        if (context != null) {
            final String deviceId = AndroidHelper.getDeviceId(context);
            if (! TextUtils.isEmpty(deviceId)) {
                return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
            }
        }
        return null;
    }

    // Get a random ID (RFC4122, length: 32)
    // Random
    public static String getRandomId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getDevice() {
        return Build.DEVICE;
    }

	public static String getRelease() {
		return Build.VERSION.RELEASE;
	}

	public static int getApi() {
		return Build.VERSION.SDK_INT;
	}

}
