package me.shkschneider.skeleton.helpers;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Patterns;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.java.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class AndroidHelper {

    public static final String PLATFORM = "Android";
    public static final String ACCOUNT_GOOGLE = "com.google";

    public static String platform() {
        return PLATFORM;
    }

    public static String id() {
        final String id = Settings.Secure.getString(SkeletonApplication.CONTEXT.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (StringHelper.nullOrEmpty(id)) {
            LogHelper.warning("Id was NULL");
            return null;
        }

        return (id != null ? id.toLowerCase() : null);
    }

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
    public static final int API_20 = Build.VERSION_CODES.KITKAT_WATCH;
    public static final int API_21 = Build.VERSION_CODES.LOLLIPOP;

    public static Integer versionCode() {
        try {
            return Integer.valueOf(versionName().replaceAll("[^0-9]", ""));
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String versionName() {
        return Build.VERSION.RELEASE;
    }

    public static int api() {
        return Build.VERSION.SDK_INT;
    }

    public static List<String> accounts() {
        final List<String> accounts = new ArrayList<String>();
        final AccountManager accountManager = AccountManager.get(SkeletonApplication.CONTEXT);
        if (accountManager != null) {
            for (Account account : accountManager.getAccountsByType(ACCOUNT_GOOGLE)) {
                if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                    accounts.add(account.name);
                }
            }
        }
        else {
            LogHelper.warning("AccountManager was NULL");
        }
        return accounts;
    }

    public static String account() {
        final List<String> accounts = accounts();
        if (accounts.size() == 0) {
            LogHelper.warning("No accounts");
            return null;
        }

        return accounts.get(0);
    }

}
