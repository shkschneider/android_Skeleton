package me.shkschneider.skeleton.helpers;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Patterns;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class AndroidHelper {

    public static final String PLATFORM = "Android";
    public static final String ACCOUNT_GOOGLE = "com.google";

    public static final int API_1 = Build.VERSION_CODES.BASE;
    public static final int API_2 = Build.VERSION_CODES.BASE_1_1;
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
    public static final int API_19 = Build.VERSION_CODES.KITKAT;

    public static Boolean tablet() {
        if (api() >= Build.VERSION_CODES.HONEYCOMB) {
            final Configuration configuration = SkeletonApplication.CONTEXT.getResources().getConfiguration();
            if (configuration == null) {
                LogHelper.warning("Configuration was NULL");
                return null;
            }

            try {
                final Method method = configuration.getClass().getMethod("isLayoutSizeAtLeast", int.class);
                return (Boolean) method.invoke(configuration, Configuration.SCREENLAYOUT_SIZE_XLARGE);
            }
            catch (final NoSuchMethodException e) {
                LogHelper.error("NoSuchMethodException: " + e.getMessage());
                return null;
            }
            catch (final IllegalAccessException e) {
                LogHelper.error("IllegalAccessException: " + e.getMessage());
                return null;
            }
            catch (final InvocationTargetException e) {
                LogHelper.error("InvocationTargetException: " + e.getMessage());
                return null;
            }
        }
        else {
            LogHelper.debug("Api was < HONEYCOMB");
            return false;
        }
    }

    public static String id() {
        final String androidId = Settings.Secure.getString(SkeletonApplication.CONTEXT.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (StringHelper.nullOrEmpty(androidId)) {
            LogHelper.warning("AndroidId was NULL");
            return null;
        }

        return androidId.toLowerCase();
    }

    public static String uuid() {
        final String androidId = AndroidHelper.id();
        if (StringHelper.nullOrEmpty(androidId)) {
            LogHelper.warning("AndroidId was NULL");
            return null;
        }

        return UUID.nameUUIDFromBytes(androidId.getBytes()).toString().replace("-", "");
    }

    public static String randomUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String release() {
        return Build.VERSION.RELEASE;
    }

    public static int api() {
        return Build.VERSION.SDK_INT;
    }

    public static String account() {
        final AccountManager accountManager = AccountManager.get(SkeletonApplication.CONTEXT);
        if (accountManager != null) {
            for (Account account : accountManager.getAccountsByType(ACCOUNT_GOOGLE)) {
                if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                    return account.name;
                }
            }
        }
        else {
            LogHelper.warning("AccountManager was NULL");
        }
        return null;
    }

    public static long sinceBoot() {
        return SystemClock.elapsedRealtime();
    }

    public static long sinceCurrentThreadBirth() {
        return SystemClock.currentThreadTimeMillis();
    }

    public static void safeSleep(final long milliseconds) {
        SystemClock.sleep(milliseconds);
    }

}
