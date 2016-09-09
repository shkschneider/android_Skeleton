package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shkschneider.skeleton.data.FileHelper;
import me.shkschneider.skeleton.ui.BitmapHelper;
import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.ui.DrawableHelper;

public class ApplicationHelper {

    public static final int DEFAULT_ICON = android.R.drawable.sym_def_app_icon;

    protected ApplicationHelper() {
        // Empty
    }

    public static Context context() {
        if (SkeletonApplication.CONTEXT == null) {
            throw new RuntimeException("Skeleton not configured!");
        }
        return SkeletonApplication.CONTEXT;
    }

    public static boolean debug() {
        // <https://stackoverflow.com/a/25517680/603270>
        return SkeletonApplication.DEBUG;
    }

    public static Resources resources() {
        return context().getResources();
    }

    public static AssetManager assetManager() {
        return context().getAssets();
    }

    public static String[] files() {
        return context().fileList();
    }

    public static int wipe() {
        int i = 0;
        for (final String path : files()) {
            final File file = FileHelper.get(path);
            if (file.exists()) {
                if (file.delete()) {
                    i++;
                }
            }
        }
        return i;
    }

    public static String packageName() {
        return context().getPackageName();
    }

    public static PackageManager packageManager() {
        return context().getPackageManager();
    }

    @Nullable
    public static String name() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName(), 0);
            if (applicationInfo == null) {
                LogHelper.warning("ApplicationInfo was NULL");
                return null;
            }

            final CharSequence label = applicationInfo.loadLabel(packageManager);
            if (label == null) {
                LogHelper.warning("Label was NULL");
                return null;
            }

            return label.toString();
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    // <http://semver.org>
    @Size(3)
    @Nullable
    public static Integer[] semanticVersions() {
        final String versionName = versionName();
        if (versionName == null) {
            return null;
        }
        if (! versionName.matches("^\\d+\\.\\d+\\.\\d+$")) {
            LogHelper.warning("Not semantic versioning");
            return null;
        }
        final String[] versionNames = versionName.split("\\.");
        return new Integer[] {
                Integer.valueOf(versionNames[0]),
                Integer.valueOf(versionNames[1]),
                Integer.valueOf(versionNames[2])
        };
    }

    @Nullable
    public static String versionName() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionName;
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static int versionCode() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return -1;
            }

            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionCode;
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return -1;
        }
    }

    @Nullable
    public static Bitmap icon() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }
            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName(), 0);
            if (applicationInfo == null) {
                LogHelper.warning("ApplicationInfo was NULL");
                return null;
            }
            final Drawable drawable = applicationInfo.loadIcon(packageManager);
            return BitmapHelper.fromDrawable(drawable);
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static List<String> permissions() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName(), PackageManager.GET_PERMISSIONS);

            final List<String> list = new ArrayList<>();
            Collections.addAll(list, packageInfo.requestedPermissions);
            return list;
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @SuppressLint("PackageManagerGetSignatures")
    @Nullable
    public static String signature() {
        final PackageManager packageManager = packageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return null;
        }

        try {
            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null) {
                LogHelper.warning("PackageInfo was NULL");
                return null;
            }

            final Signature[] signatures = packageInfo.signatures;
            if (signatures == null) {
                LogHelper.debug("No signatures");
                return null;
            }

            return signatures[0].toCharsString();
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static boolean fromMarket() {
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            return fromMarket21();
        }
        else if (AndroidHelper.api() >= AndroidHelper.API_17) {
            return fromMarket17();
        }
        return fromMarket3();
    }

    @TargetApi(AndroidHelper.API_21)
    private static boolean fromMarket21() {
        // API-21+ Settings.Secure
        return (Settings.Secure.getInt(ApplicationHelper.context().getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

    @TargetApi(AndroidHelper.API_17)
    @SuppressWarnings("deprecation")
    private static boolean fromMarket17() {
        // API-17+ Settings.Global
        return (Settings.Global.getInt(ApplicationHelper.context().getContentResolver(), Settings.Global.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

    @TargetApi(AndroidHelper.API_3)
    private static boolean fromMarket3() {
        // API-3+ Settings.Secure
        return (Settings.Secure.getInt(ApplicationHelper.context().getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

}
