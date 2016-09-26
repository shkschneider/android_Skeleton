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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shkschneider.skeleton.ui.BitmapHelper;
import me.shkschneider.skeleton.SkeletonApplication;

public class ApplicationHelper {

    public static final int DEFAULT_ICON = android.R.drawable.sym_def_app_icon;

    protected ApplicationHelper() {
        // Empty
    }

    // FIXME
    public static boolean debug() {
        // <https://stackoverflow.com/a/25517680/603270>
        return SkeletonApplication.DEBUG;
    }

    // FIXME
    public static Resources resources(@NonNull final Context context) {
        return context.getResources();
    }

    // FIXME AssetsHelper.assetManager()
    public static AssetManager assetManager(@NonNull final Context context) {
        return context.getAssets();
    }

    @Deprecated // Avoid
    public static String[] files(@NonNull final Context context) {
        return context.fileList();
    }

    public static String packageName(@NonNull final Context context) {
        return context.getPackageName();
    }

    public static PackageManager packageManager(@NonNull final Context context) {
        return context.getPackageManager();
    }

    @Nullable
    public static String name(@NonNull final Context context) {
        try {
            final PackageManager packageManager = packageManager(context);
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName(context), 0);
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
    public static Integer[] semanticVersions(@NonNull final Context context) {
        final String versionName = versionName(context);
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
    public static String versionName(@NonNull final Context context) {
        try {
            final PackageManager packageManager = packageManager(context);
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            return packageManager.getPackageInfo(packageName(context), PackageManager.GET_META_DATA).versionName;
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static int versionCode(@NonNull final Context context) {
        try {
            final PackageManager packageManager = packageManager(context);
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return -1;
            }

            return packageManager.getPackageInfo(packageName(context), PackageManager.GET_META_DATA).versionCode;
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return -1;
        }
    }

    @Nullable
    public static Bitmap icon(@NonNull final Context context) {
        try {
            final PackageManager packageManager = packageManager(context);
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }
            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName(context), 0);
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
    public static List<String> permissions(@NonNull final Context context) {
        try {
            final PackageManager packageManager = packageManager(context);
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName(context), PackageManager.GET_PERMISSIONS);

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
    public static String signature(@NonNull final Context context) {
        final PackageManager packageManager = packageManager(context);
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return null;
        }

        try {
            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName(context), PackageManager.GET_SIGNATURES);
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

    public static boolean fromMarket(@NonNull final Context context) {
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            return fromMarket21(context);
        }
        else if (AndroidHelper.api() >= AndroidHelper.API_17) {
            return fromMarket17(context);
        }
        return fromMarket3(context);
    }

    @TargetApi(AndroidHelper.API_21)
    private static boolean fromMarket21(@NonNull final Context context) {
        // API-21+ Settings.Secure
        return (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

    @TargetApi(AndroidHelper.API_17)
    @SuppressWarnings("deprecation")
    private static boolean fromMarket17(@NonNull final Context context) {
        // API-17+ Settings.Global
        return (Settings.Global.getInt(context.getContentResolver(), Settings.Global.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

    @TargetApi(AndroidHelper.API_3)
    private static boolean fromMarket3(@NonNull final Context context) {
        // API-3+ Settings.Secure
        return (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

}
