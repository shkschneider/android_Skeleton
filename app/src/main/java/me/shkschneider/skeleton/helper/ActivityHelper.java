package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import me.shkschneider.app.MainApplication;

public class ActivityHelper {

    public static View contentView(@NotNull final Activity activity) {
        return activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    public static void toast(@NotNull final String msg) {
        Toast.makeText(MainApplication.CONTEXT, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean portrait() {
        return (MainApplication.CONTEXT.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    public static boolean landscape() {
        return (MainApplication.CONTEXT.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    public static String title(final Activity activity) {
        final ActivityInfo activityInfo = activityInfo(activity);
        if (activityInfo == null) {
            LogHelper.warning("ActivityInfo was NULL");
            return null;
        }

        return activity.getResources().getString(activityInfo.labelRes);
    }

    public static ActivityInfo activityInfo(final Activity activity) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return null;
        }

        final PackageManager packageManager = activity.getPackageManager();
        final ComponentName componentName = activity.getComponentName();
        try {
            return packageManager.getActivityInfo(componentName, 0);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
