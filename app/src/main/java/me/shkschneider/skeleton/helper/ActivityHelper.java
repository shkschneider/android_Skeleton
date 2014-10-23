package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;

import org.jetbrains.annotations.NotNull;

import me.shkschneider.app.MainApplication;

public class ActivityHelper {

    public static View contentView(@NotNull final Activity activity) {
        return activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    public static void toast(@NotNull final String msg) {
        Toast.makeText(MainApplication.CONTEXT, msg, Toast.LENGTH_SHORT).show();
    }

    private static void crouton(@NotNull final Activity activity, @NotNull final String msg, final AppMsg.Style style) {
        final AppMsg appMsg = AppMsg.makeText(activity, msg, style);
        appMsg.setLayoutGravity(Gravity.BOTTOM);
        appMsg.show();
    }

    public static void croutonGreen(@NotNull final Activity activity, @NotNull final String msg) {
        crouton(activity, msg, AppMsg.STYLE_INFO);
    }

    public static void croutonOrange(@NotNull final Activity activity, @NotNull final String msg) {
        crouton(activity, msg, AppMsg.STYLE_CONFIRM);
    }

    public static void croutonRed(@NotNull final Activity activity, @NotNull final String msg) {
        crouton(activity, msg, AppMsg.STYLE_ALERT);
    }

    public static boolean portrait() {
        return (MainApplication.CONTEXT.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    public static boolean landscape() {
        return (MainApplication.CONTEXT.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

}
