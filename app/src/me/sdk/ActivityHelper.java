package me.sdk;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;

import org.jetbrains.annotations.NotNull;

import me.app.MainApplication;

public class ActivityHelper {

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

}
